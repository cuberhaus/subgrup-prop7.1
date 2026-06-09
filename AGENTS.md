# subgrup-prop7.1

Frozen FIB-UPC PROP coursework: a Java **Recommender System** structured in three layers (Domain, Persistence, Presentation), applying software design patterns. Originally a team project (subgroup 7.1); a Spring Boot web frontend was added later and is reused as a live-demo backend by the `PersonalPortfolio` repo.

## Architecture

- `FONTS/` — Java source code, the canonical three-layer split:
  - `domini/` — domain model + domain controllers (business logic).
  - `persistencia/` — file/data I/O.
  - `presentacio/` — Swing GUI + presentation controllers.
  - `utilitats/`, `excepcions/`, `lib/` (bundled JUnit), `jocs_de_proves/` (test drivers).
  - Entry point: [FONTS/Main.java](FONTS/Main.java).
- `EXE/` — generated `.class` output and runtime datasets (do not edit by hand).
- `DOCS/`, `PROP/` — JavaDoc, deliverables, internal team docs.
- `web/` — Spring Boot + Thymeleaf web UI ([web/pom.xml](web/pom.xml)), wraps the domain layer.

## Build and Test

Root [Makefile](Makefile) delegates everything to [FONTS/Makefile](FONTS/Makefile) via a catch-all rule.

- `make compile` — compile all Java sources into `EXE/`.
- `make run` — launch the Swing GUI.
- `make Driver<ClassName>` — run a specific domain driver.
- `make <ClassName>Test` — run a JUnit unit test.
- `make joc1` / `joc2` / `joc3` — scripted practical test runs.
- `make clean` — remove compiled `.class` files.
- `make web` — start the Spring Boot UI on `http://localhost:8081`.

## Pitfalls

- **Frozen coursework** — do not refactor, rename, or restructure existing code; the three-layer separation (`domini` / `persistencia` / `presentacio`) is the grading criterion and must be preserved.
- The `web/` Spring Boot module is consumed by `PersonalPortfolio` as a live demo backend — keep its HTTP surface stable.
- Source paths and comments are in **Catalan**; match the existing language when editing.
- All `make` targets must resolve through the root Makefile's catch-all to [FONTS/Makefile](FONTS/Makefile).

See [README.md](README.md).
