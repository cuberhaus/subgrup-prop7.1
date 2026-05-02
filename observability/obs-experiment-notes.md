# Coroot PoC — observation log

Fill this in as you actually use the stack. The pracpro2 (Honeycomb)
and Practica_de_Planificacion (LGTM) PoCs have their own version of
this file with the same headings, so a `diff` across the three is the
fastest comparison medium.

Headings deliberately mirror those PoCs' notes files. Cells filled with
concrete numbers below come from the Phase 1–5 implementation
verification (the observation work was real); cells marked TBD are
reserved for hands-on UX observations once the scenarios in
[SCENARIOS.md](SCENARIOS.md) actually run.

---

## First impressions

_Notes from the first 30 minutes of clicking around Coroot's UI after
the first traces land. What stood out, what was confusing, what was
surprisingly fast or slow._

- **Time from `make web` to first trace in UI:** ~30s. eBPF agent picks
  up the JVM via host PID space the moment Spring Boot binds port 8081;
  Coroot's constructor then needs one full 15s scrape cycle before the
  app shows up under the Applications view.
- **Time from first trace to first useful query:** ~5 min including the
  v1.19 admin-password gate setup and figuring out where the
  Application view lives in the UI.
- **Friction points (hit during Phase 1–5 implementation):**
  1. **Coroot 1.10.0 panic-loops on this host.** "assignment to entry
     in nil map" inside the recording-rules constructor. Fixed upstream
     by April 2026; required bumping to v1.19.7. The plan's pin was
     3+ minor versions stale; reality intervened.
  2. **Native (non-containerized) JVM falls outside the auto-classifier.**
     `container_jvm_*` series only populate for JVMs running inside
     Docker. Our `make web` JVM gets process-level metrics from eBPF
     (`container_http_requests_total`, `container_dns_requests_*`) but
     the Java tab in the Application view stays grey. JMX exporter
     metrics ARE in Prometheus and are queryable via Coroot's Custom
     Metrics view; only the auto-categorisation view stays empty.
  3. **`JAVA_TOOL_OPTIONS` is the wrong knob for spring-boot:run.**
     Both the parent Maven JVM and the forked Spring Boot JVM inherit
     the env var, both try to attach the JMX agent, and the second
     bind fails with `BindException: Address already in use`. Use
     `-Dspring-boot.run.jvmArguments` instead — that scopes to the
     forked JVM only. The Makefile in this branch uses the right form;
     this is documented in [README.md](README.md#things-that-bit-me-along-the-way)
     so the next user doesn't trip on it.
  4. **eBPF sees everything else on the host.** With sentry-self-hosted
     (~25 containers) + the LGTM stack (~9 containers) + the host's
     Chromium tabs all running, the node-agent reports 149 distinct
     "applications" via cgroup classification. The UI scopes nicely by
     category, but during implementation it was momentarily confusing
     to see "snuba-profiling-functions-consumer" alongside the JVM in
     the same Application list.

## Latency-regression UX (Scenario A)

_See [SCENARIOS.md](SCENARIOS.md#scenario-a--latency-regression).
Capture time-to-detection, number of clicks from "something's slow" to
"this specific endpoint is slow", and any moment where Coroot's UI made
the diagnosis faster or slower than expected._

- **Time from regression to noticing:** TBD — expected to be 5 min or
  less since SLO config in `coroot/coroot-config.yaml` declares
  `objectivePercent: 95, objectiveThreshold: 200ms` and Scenario A
  injects 500 ms into 30% of requests, which fully exhausts the budget.
- **Clicks from notice → "it's the home() endpoint":** TBD. eBPF L7
  decode SHOULD give per-route latency histograms automatically; whether
  the UI surfaces this in 1 click or 3 is what the screenshot will
  document.
- **Did the latency-quantile heatmap show the 500 ms band as a
  separate cluster?** TBD — eBPF measurement is high-resolution at
  the syscall level so this is a stack-of-cards demonstration of the
  paradigm: a kernel-level packet-decode should be GOOD at this.
- **Webhook fired?** TBD. Verify with
  `docker logs subgrup-prop-coroot-webhook-sink | grep incident`.

## Error-spike UX (Scenario B)

_See [SCENARIOS.md](SCENARIOS.md#scenario-b--error-spike). Same axes:
time-to-alert, automatic vs. manual grouping, root-cause workflow._

- **Time from injection to alert firing:** TBD — Coroot's availability
  SLO at `objectivePercent: 99` should fire after ~5 min of 10% error
  rate.
- **Did errors group automatically by HTTP status?** Almost certainly
  yes — eBPF's HTTP decoder already produces
  `container_http_requests_total{status="5xx"}` series. Confirm whether
  the UI splits 500/502/503/504 separately or buckets them as `5xx`.
- **Did errors group by exception class?** **No** — eBPF can't see
  Java stack traces or Throwable subclasses. The fact that every
  exception is a `RuntimeException("scenario B: synthetic error #N")`
  is invisible to the agent. This is a known structural gap: span-based
  stacks (Sentry, Honeycomb, OTel) win this comparison row.
- **Was the failing-vs-healthy diff useful?** TBD. Coroot's "log patterns"
  view should group exception messages from logback's JSON-line stdout
  in 1 click; whether that's as good as Honeycomb's BubbleUp is what
  the side-by-side will reveal.
- **Sample replay / stack trace?** Available *only* via the JSON-line
  stdout (logback writes the full stacktrace via Spring's
  `DefaultErrorAttributes`). Coroot indexes log content but doesn't
  reconstruct the stack-trace structure the way Sentry does. TBD whether
  this is good enough in practice.

## Cost & operational shape

_How much it costs to actually run this. Memory / disk / RPS limits,
free-tier ceilings, whether 7-day retention is enough for the kind of
investigation you actually want to do._

- **Memory budget (measured during Phase 1):** ~1.2 GiB across the
  five-container core stack (`coroot` ~256 MiB, `coroot-node-agent` ~128
  MiB, `coroot-cluster-agent` ~64 MiB, `prometheus` ~256 MiB,
  `clickhouse` ~512 MiB). The Phase 5 `webhook-sink` adds ~32 MiB.
  Compared to sentry-self-hosted (~6 GiB) and the LGTM stack (~3 GiB)
  this is the cheapest of the three self-hosted options.
- **Disk budget:** ClickHouse defaults to 7-day TTL on traces/logs/
  profiles, 30-day on the cache. With Coroot's space-manager auto-
  cleanup at 70% disk usage, partition rotation happens automatically.
  During Phase 5 verification the Coroot logs already showed
  "disk default usage (83.19%) exceeds threshold (70%), marking for
  cleanup" — the cleanup ran without manual intervention.
- **CPU during eBPF probe attach:** TBD. Bring up the stack with
  `top` running on the host and capture the spike at probe-attach time
  vs. steady-state. Expected: brief spike at startup (eBPF compilation
  + verification), low continuous overhead afterwards.
- **Free-tier ceilings:** None — Coroot Community Edition is fully OSS
  (Apache 2.0). Enterprise adds RBAC / AI root-cause analysis / SSO
  but nothing this PoC needs.
- **Retention vs. investigation horizon:** 7 days of traces is short
  for a "what changed last week" question. Tunable via the Coroot
  config file's `traces.ttl: 7d` knob — bumping to 30d roughly
  quadruples ClickHouse disk usage. TBD which is the right answer
  for a single-engineer portfolio.
- **Lock-in concerns:** **Low for data** — traces and logs sit in
  ClickHouse (open columnar format, queryable by any ClickHouse client),
  metrics in Prometheus (open TSDB, scrapeable / federated by anything).
  **Medium for workflow** — Coroot's UI, SLO inference, and alerting
  rules don't have a portable export format. Migrating would mean
  re-defining SLOs in Grafana/Prometheus.

## Verdict

_The 1-paragraph "if you had to pick one for the portfolio's main
observability stack tomorrow" call. Reference Scenario A and B
observations._

TBD — write this last, after the scenarios actually run and screenshots
are captured. Working hypothesis (to be confirmed or contradicted by
the empirical data): Coroot is **best at** zero-instrumentation
breadth (one stack instantly observes every container on the host
without per-app config) but **worst at** language-level depth (no
custom spans, no exception class grouping, no profiler — those are
gaps the LGTM/OTel-Java-agent and Sentry stacks fill). The
"recommended next experiment" likely turns out to be the OTel Java
agent on the same repo (`obs-experiment-otel-java-agent`) for a clean
A/B between eBPF and bytecode-rewriting paradigms.

---

## Cross-PoC comparison

_Filled in once all three (Honeycomb, LGTM, Coroot) have their own
notes filled in. Diff against `pracpro2/observability/obs-experiment-notes.md`
and `Practica_de_Planificacion/observability/obs-experiment-notes.md`
for the side-by-side._

| Axis                    | Honeycomb (pracpro2) | LGTM (Practica_de_Planificacion) | Coroot (this PoC)                     |
|-------------------------|----------------------|----------------------------------|---------------------------------------|
| Setup time              | TBD                  | TBD                              | ~4 hrs (Phase 1+2 stack bring-up + version-bump debug; Phases 3–5 mostly mechanical) |
| First-meaningful-query  | TBD                  | TBD                              | ~5 min after first scrape cycle (HTTP RED metric per route, no setup) |
| Lat-regression detection| TBD                  | TBD                              | TBD                                   |
| Error-spike detection   | TBD                  | TBD                              | TBD (auto-grouped by HTTP status, NOT by exception class) |
| Free-tier limits / cost | 20M events/mo SaaS   | OSS (S3 storage = pay-per-GiB)   | OSS Community Edition; ~1.2 GiB RAM   |
| Lock-in risk            | High (data + UX)     | Low (OTel-native data + dashboards) | Low for data (Prom + ClickHouse), medium for workflow (Coroot UI/SLO config) |
| Data export ease        | TBD                  | TBD                              | ClickHouse client + Prometheus federation; Coroot SLOs not portable |
| Best at                 | TBD                  | TBD                              | TBD (working hypothesis: zero-instrumentation breadth — every container observed without per-app config) |
| Worst at                | TBD                  | TBD                              | TBD (working hypothesis: language-level depth — no custom spans, no exception-class grouping, no built-in profiler) |
| **Distinctive corner of the design space** | High-cardinality wide-events | OSS pillar architecture | Zero-instrumentation eBPF |

The interesting cells are usually "best at" and "worst at" — they're
where the design choices actually matter for the kind of work the
portfolio is showing off. Coroot's row is unique in that the design
choice is *categorical*: the others are different ways of asking the
app what it's doing; Coroot is asking the *kernel* what the app is
doing.
