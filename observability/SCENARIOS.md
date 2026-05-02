# Scripted scenarios — like-for-like with the LGTM and Honeycomb PoCs

These two experiments produce comparable observations across the three
existing PoCs (LGTM, Honeycomb, Coroot). Run each, capture screenshots
and time-to-detection, write them up in `obs-experiment-notes.md`.

The expectation is **not** that one stack "wins" — it's that the
trade-offs become concrete instead of theoretical. Coroot's specific
pitch is "no SDK, no app code change", so these scenarios deliberately
exercise that boundary: how good is eBPF + JMX exporter at surfacing
issues that the other PoCs caught with their in-process SDKs?

## Scenario A — latency regression

**Setup**: a hidden 500 ms `Thread.sleep(...)` inside `HomeController.home()`,
the no-precondition GET handler at `/`. The regression is invisible in
p50, mildly visible in p95, glaring in p99.

Add this temporary block at the top of `home()` in
`web/src/main/java/web/controllers/HomeController.java` (revert when
done):

```java
import java.util.concurrent.ThreadLocalRandom;

// SCENARIO A — remove when capture is done.
try {
    if (ThreadLocalRandom.current().nextInt(100) < 30) {
        Thread.sleep(500);
    }
} catch (InterruptedException ignored) {
    Thread.currentThread().interrupt();
}
```

(30% of requests get the slowdown — keeps p50 sane while making p95/p99
unmistakable. Match this percentage across all three PoCs for the
comparison to be fair.)

**Drive load** (any of these works; pick one and stick with it across
all three PoCs):

```bash
# Simple curl loop — generates ~10 req/s
while true; do
  curl -s -H 'X-Session-Id: scenario-a-baseline' \
       http://localhost:8081/ > /dev/null
  sleep 0.1
done
```

**Comparison axes**:

| Question                                        | Honeycomb answer                       | LGTM answer                          | Coroot answer                                     |
|-------------------------------------------------|----------------------------------------|--------------------------------------|--------------------------------------------------|
| Time from regression to noticing it?            | Query #1 board: p99 spike on `command.name` | Mimir alert on `histogram_quantile(0.99, ...)` | Coroot SLO inference: latency budget burn in <5 min |
| Clicks from "it's slow" to "it's this endpoint"? | 1 (group by `command.name`)            | 2 (alert → dashboard → drilldown)    | 0 (Application view auto-pins by URL path)       |
| Did you spot the 500 ms band as a separate cluster? | heatmap on `child.elapsed_ms`        | Tempo trace search by latency       | eBPF L7 decode shows latency histogram per route |
| Cost / friction to ask follow-up questions?     | free-form query rebuilds in seconds    | PromQL recording rule + dashboard reload | Coroot's "deviation" widget surfaces it automatically |
| Visibility into JVM-side cause (GC pause vs. blocking call)? | yes (span events)                  | yes (Pyroscope flamegraph at the time)  | partial — JMX exporter shows GC charts BUT they're queried via Custom Metrics, not Coroot's prebuilt Java tab (native-JVM gap) |

## Scenario B — error spike

**Setup**: throw on every 10th request to `home()` with a synthetic
runtime exception. Spring Boot's default error handler turns it into a
500 response, which is what eBPF / Coroot's HTTP-status decoder sees.

Add this temporary block inside `home()` AFTER the Scenario A block
(revert when done):

```java
import java.util.concurrent.atomic.AtomicLong;

// SCENARIO B — remove when capture is done.
private static final AtomicLong REQ_COUNTER = new AtomicLong();
// ... and inside home(), at the very top:
long n = REQ_COUNTER.incrementAndGet();
if (n % 10 == 0) {
    throw new RuntimeException("scenario B: synthetic error #" + n);
}
```

(The `AtomicLong` field needs to live on the controller class itself,
not inside the method. Move the declaration up if Spring complains.)

**Drive load**: same curl loop as Scenario A, plus run for 10 minutes.

**Comparison axes**:

| Question                                                    | Honeycomb answer                        | LGTM answer                              | Coroot answer                                        |
|-------------------------------------------------------------|-----------------------------------------|------------------------------------------|-----------------------------------------------------|
| Time from spike to alert firing?                            | Trigger #4 (5 min eval)                | Mimir alert rule (similar)               | Coroot SLO availability budget burn (5 min eval)    |
| Are errors grouped automatically?                           | yes (`error.kind`)                      | no (manual `group by`)                  | partially — grouped by HTTP status (5xx) per route, not by exception class (eBPF can't see Java stack traces) |
| Can you see what's *different* about failing requests vs. healthy ones? | BubbleUp (1 click)               | manually in Loki / Tempo (3 clicks)    | log-pattern view groups exception messages from the JSON-line stdout (1 click) |
| Sample replay / stack trace?                                | span events with stacktrace            | requires Sentry (separate stack)        | log lines with full stack from logback-spring.xml (since the catch path lets Spring's DefaultErrorAttributes serialize the stacktrace into stdout) |
| Did the webhook fire?                                       | Slack via Honeycomb integration         | Alertmanager → webhook                   | Coroot's webhook integration (see `coroot/coroot-config.yaml`); fired alert lands in `docker logs subgrup-prop-coroot-webhook-sink` |
| Effort to keep this experiment running for a week?          | none (SaaS)                            | non-zero (3 GiB MinIO budget on the LGTM stack) | non-zero (~1 GiB ClickHouse + Prometheus on this dev box) |

## Verifying the webhook sink received the alert

After Scenario A runs long enough for the SLO latency budget to burn:

```bash
# Watch incoming webhook payloads in real time:
docker logs -f subgrup-prop-coroot-webhook-sink

# Or grep for an incident notification:
docker logs subgrup-prop-coroot-webhook-sink 2>&1 | grep -i incident | head
```

Expected payload shape (from `coroot/coroot-config.yaml`'s
`incidentTemplate`):

```json
{
  "kind": "incident",
  "service": "java",
  "severity": "critical",
  "summary": "SLO violation: latency",
  "link": "http://localhost:18080/p/<project-id>/app/_:Application:java"
}
```

If `service` shows up as `"java"` instead of `"subgrup-prop"`, that's
the native-JVM categorisation gap surfacing — the application is
correctly identified as a JVM by Coroot's process classifier but isn't
labelled with our friendly name because it's not running in a docker
container Coroot can introspect. The `applicationCategories` block in
`coroot-config.yaml` is supposed to fix this via cmdline pattern
matching; mileage varies depending on Coroot's classifier version.

## Reverting the scenarios

Both scenarios edit `web/src/main/java/web/controllers/HomeController.java`
temporarily. Use:

```bash
git diff web/src/main/java/web/controllers/HomeController.java   # see what's still injected
git checkout web/src/main/java/web/controllers/HomeController.java   # nuke the scenario edits
```

…to restore the clean instrumented state once observations are captured.

## Optional: make-targets

Once the scenarios stabilise, lift them to the Makefile:

```make
obs-scenario-a:
	bash observability/scenarios/run-latency.sh
obs-scenario-b:
	bash observability/scenarios/run-errors.sh
```

Out of scope for this initial branch but a natural follow-up — the
Honeycomb PoC has a similar TODO open.
