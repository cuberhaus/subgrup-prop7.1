# Observability — Coroot eBPF PoC for `subgrup-prop7.1`

This branch (`obs-experiment-coroot`) replaces the prior Sentry
instrumentation with **Coroot Community Edition**, an eBPF-based
zero-instrumentation observability stack. It is the fourth PoC in the
demo-repo observability series, paired against the LGTM
(`Practica_de_Planificacion`), Honeycomb (`pracpro2`), and Elastic
(`tenda_online`) experiments.

## Why Coroot specifically

Coroot's pitch is the categorical opposite of every other stack in the
portfolio: **no SDK, no agent inside the JVM, no code changes**. A
privileged eBPF program attached to kernel tracepoints reconstructs HTTP
/ DB / gRPC / Redis traces from packet flow. The application binary
doesn't know it's being observed.

This PoC deliberately picks the corner of the design space everyone
else avoids:

|                  | Sentry / LGTM / Honeycomb / Elastic | Coroot (this PoC)               |
|------------------|-------------------------------------|---------------------------------|
| Instrumentation  | SDK / agent inside the app process  | eBPF probes on the host kernel  |
| Code changes     | yes (per language)                  | none                            |
| Stack traces     | yes                                 | no (HTTP-status inference only) |
| Custom spans     | yes                                 | no                              |
| Service map      | from instrumentation                | from packet decode (auto)       |
| RED metrics      | derived from spans / aggregations   | derived from packet decode      |
| JVM internals    | via SDK or Micrometer               | via JMX exporter (Phase 4)      |
| Lock-in          | SDK shape                           | none (just stop the daemonset)  |

The eventual `obs-experiment-notes.md` (Phase 6) compares it side-by-side
with the other PoCs on the same two scripted scenarios used by the
Honeycomb experiment (latency regression, error spike).

## Architecture

```text
                            X-Session-Id propagated from
                              parent portfolio (kept as
                              a logback MDC key on this
                                       branch)
                                          |
+-----------------------+      ┌─────────────────────────┐
| Spring Boot 3.2.5 JVM |      │ JMX exporter            │
|   ./mvnw spring-boot  | ───▶ │ -javaagent:.jar=12345   │
|   port 8081 (host)    |      │ exposes /metrics         │
+-----------------------+      └────────────┬────────────┘
          ▲                                 │ Prometheus scrape
          │ packets                         │ (Phase 4)
          │ (eBPF decode)                   ▼
+-----------------------+      ┌─────────────────────────┐
| coroot-node-agent     |      │ prometheus              │
| (privileged, host PID)| ───▶ │ (internal, no host port)│
+-----------------------+      └────────────┬────────────┘
                                            │
                              ┌─────────────┴─────────────┐
                              ▼                           ▼
                  ┌────────────────────┐      ┌────────────────────┐
                  │ coroot UI          │      │ clickhouse         │
                  │ http://localhost:  │ ───▶ │ traces + logs      │
                  │ 18080              │      │ (internal only)    │
                  └────────────────────┘      └────────────────────┘
```

The eBPF agent watches **all** processes on the host — meaning it'll
also see the user's `sentry-self-hosted` containers and the LGTM stack
running for `Practica_de_Planificacion`. Coroot scopes the UI to the
application under test via process-name filters configured in its UI on
first boot.

## Quickstart

1. **Bring up the obs stack**:
   ```bash
   cp observability/.env.obs.example observability/.env.obs
   docker compose -f observability/docker-compose.obs.yml --env-file observability/.env.obs up -d
   ```
   First boot pulls ~1.5 GiB of images (Coroot + ClickHouse + Prometheus
   + 2 agents). Subsequent boots are fast.

2. **Wait ~30 s for healthchecks** to settle:
   ```bash
   docker compose -f observability/docker-compose.obs.yml ps
   ```
   All five services should show `Up` (the two `*-agent` containers
   don't have healthchecks; they're either running or not).

3. **Open the UI**: <http://localhost:18080>. First-time setup is
   automatic — Coroot picks up the bootstrap flags from
   `docker-compose.obs.yml`, creates a default project, and starts
   ingesting data from the node-agent within ~60 s.

4. **Run the Spring Boot app** (in a separate terminal):
   ```bash
   make web
   ```
   The JVM listens on `:8081` as before. Within ~60 s the Coroot UI's
   "Applications" view should show a `java` (or `subgrup-prop`, after
   labelling) entry with HTTP traffic + RED metrics.

5. **Add the JMX exporter** (Phase 4 of this PoC) to surface JVM
   internals (GC, heap, thread pool). See [JMX exporter setup](#jmx-exporter-setup-phase-4)
   below.

## Tear down

```bash
# Preserve data:
docker compose -f observability/docker-compose.obs.yml down

# Wipe everything (volumes too):
docker compose -f observability/docker-compose.obs.yml down -v
```

Bringing the obs stack down does NOT touch the Spring Boot JVM. The
JVM keeps running on `:8081`; without Coroot it's just an
unobserved app, which is the same shape as `master` after Phase 2.

## Port map

Host ports are deliberately non-default to dodge the user's existing
`sentry-self-hosted` stack (port 9000) and the LGTM PoC (`13000`,
`4317-4318`, `9100-9101`, etc.). Container-internal ports stay stock so
any future `docker exec` reaches the canonical service ports.

| Host port | Container port | Service          | Purpose                         |
|-----------|----------------|------------------|---------------------------------|
| 18080     | 8080           | coroot           | Web UI                          |
| 8081      | n/a            | spring-boot JVM  | Unchanged (existing `make web`) |
| 12345     | n/a            | jmx-exporter     | JVM `/metrics` (Phase 4)        |
| —         | 9090           | prometheus       | Internal-only (Coroot reads it) |
| —         | 9000           | clickhouse-tcp   | Internal-only (Coroot reads it) |
| —         | 8123           | clickhouse-http  | Internal-only (healthcheck)     |

## Memory budget

| Service        | Approximate usage | Notes                                    |
|----------------|-------------------|------------------------------------------|
| coroot         | ~256 MiB          | Web UI + alerting engine                 |
| node-agent     | ~128 MiB          | Per-host eBPF; scales with process count |
| cluster-agent  | ~64 MiB           | Lightweight scraper                      |
| prometheus     | ~256 MiB          | Default `--storage.tsdb.retention=15d`   |
| clickhouse     | ~512 MiB          | With system logs disabled (config above) |
| **Total**      | ~1.2 GiB          |                                          |

Combined with `sentry-self-hosted` (~6 GiB) and the LGTM PoC (~3 GiB),
the dev box is under pressure. Recommended: **stop the LGTM stack** and
the Sentry self-hosted stack while running this PoC, since none of them
are needed simultaneously for this experiment.

## What's removed vs. the prior `master` branch

- `web/pom.xml` — `sentry-spring-boot-starter-jakarta` and `sentry-logback`
  deps gone (Phase 2).
- `web/src/main/resources/application.properties` — all `sentry.*` keys
  gone (Phase 2).
- `web/src/main/java/web/config/SessionIdFilter.java` — `Sentry.setTag`
  call replaced with an SLF4J MDC put so logback still correlates logs
  by `session_id` (Phase 2).
- `web/src/main/resources/logback-spring.xml` — Sentry appender gone;
  the JSON encoder remains so Coroot's container-log scraper sees
  structured fields (Phase 2).

`rg -i sentry web` returns zero matches after Phase 2.

## JMX exporter setup (Phase 4)

The eBPF agent gives HTTP / network / infra metrics for free, but
nothing about the JVM's internals (GC, heap, thread pool). The JMX
exporter — a single Java agent JAR added to the JVM via
`JAVA_TOOL_OPTIONS=-javaagent:...` — closes that gap without touching
application code.

```bash
# From the repo root, fetch the agent JAR (idempotent):
make jmx-exporter

# Then:
make web
# JVM now also exposes /metrics on http://localhost:12345
# Prometheus picks it up automatically via the scrape config.
```

See [Makefile](../Makefile) for the `jmx-exporter` target. The agent
config is in [`jmx_exporter/jmx-config.yaml`](jmx_exporter/jmx-config.yaml).

## Files in this directory

- `docker-compose.obs.yml` — master compose, 5 services.
- `.env.obs.example` — version + port template, safe to commit.
- `.env.obs` — local overrides, gitignored.
- `.gitignore` — pinned for the obs working tree.
- `prometheus/prometheus.yml` — Prometheus scrape config (self + JMX
  exporter target).
- `clickhouse/disable-system-logs.xml` — ClickHouse override config to
  silence its noisy internal logs.
- `coroot/` — reserved for future Coroot project YAML (SLO definitions,
  integrations) added in Phase 5.
- `jmx_exporter/jmx-config.yaml` — Spring Boot scrape rules for the JMX
  agent (Phase 4).
- `README.md` — this file.
- `SCENARIOS.md` (Phase 5) — two scripted experiments mirroring the
  other PoCs for like-for-like comparison.
- `obs-experiment-notes.md` (Phase 6) — the comparison artefact you fill
  in as you actually use Coroot.

## Coexistence with the portfolio

The portfolio orchestrator (`PersonalPortfolio/scripts/dev-all-demos.sh`)
sets `SENTRY_DSN` / `SENTRY_ENVIRONMENT` / `SENTRY_RELEASE` for the
subgrup-prop container as part of the multi-stack development workflow.
With Sentry deleted on this branch, those env vars are simply ignored —
the Spring Boot app won't emit Sentry events, and the orchestrator
doesn't need to change.

The Coroot stack is fully self-contained on this branch — it ingests
data via the host kernel eBPF probes and doesn't need any orchestrator
plumbing.
