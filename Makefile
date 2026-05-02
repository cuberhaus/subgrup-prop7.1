# Pass all commands to the Makefile inside the FONTS directory

.PHONY: all run clean web web-build web-clean help helper compile jmx-exporter

# JMX Exporter Java agent (fetched on first `make web` of the Coroot
# branch). Pinned because the agent's metric names are part of this
# branch's behavioural contract — bumping the version can rename
# series in subtle ways.
JMX_EXPORTER_VERSION ?= 1.5.0
JMX_EXPORTER_PORT    ?= 12345
JMX_EXPORTER_DIR     := observability/jmx_exporter
JMX_EXPORTER_JAR     := $(JMX_EXPORTER_DIR)/jmx_prometheus_javaagent-$(JMX_EXPORTER_VERSION).jar
JMX_EXPORTER_CONFIG  := $(JMX_EXPORTER_DIR)/jmx-config.yaml
JMX_EXPORTER_URL     := https://github.com/prometheus/jmx_exporter/releases/download/$(JMX_EXPORTER_VERSION)/jmx_prometheus_javaagent-$(JMX_EXPORTER_VERSION).jar

# Default target
all: helper

helper:
	@echo "========================================"
	@echo "   PROP Recommendation System Helper    "
	@echo "========================================"
	@echo "Available commands:"
	@echo "  make run          - Launch Swing GUI"
	@echo "  make web          - Launch Web Interface (with JMX exporter on the Coroot branch)"
	@echo "  make jmx-exporter - Download the JMX exporter Java agent"
	@echo "  make compile      - Compile all Java sources"
	@echo "  make clean        - Remove compiled .class files"
	@echo "  make help         - Show this help message"
	@echo "========================================"

compile:
	@$(MAKE) -C FONTS

help: helper
	@:

run:
	@$(MAKE) -C FONTS run

clean:
	@$(MAKE) -C FONTS clean

# Spring Boot web interface — Coroot branch.
#
# The JMX exporter Java agent is attached unconditionally so the
# Coroot-stack Prometheus (which scrapes host.docker.internal:12345)
# always has a target. This target depends on `jmx-exporter` so the
# JAR is auto-fetched on the first run.
#
# Passing the `-javaagent` flag via `spring-boot.run.jvmArguments`
# (NOT via `JAVA_TOOL_OPTIONS`) is intentional: the env-var route is
# inherited by both the Maven JVM and the forked Spring Boot JVM, so
# they both try to attach the same agent and bind the same exporter
# port — the second one fails with `BindException: Address already in
# use` even though no other process is on the port. The plugin
# property only flows to the forked app JVM, sidestepping the race.
web: $(JMX_EXPORTER_JAR)
	@echo "Launching web interface on http://localhost:8081"
	@echo "  JMX exporter at http://localhost:$(JMX_EXPORTER_PORT)/metrics"
	cd web && ./mvnw spring-boot:run \
	  -Dspring-boot.run.jvmArguments="-javaagent:$(abspath $(JMX_EXPORTER_JAR))=$(JMX_EXPORTER_PORT):$(abspath $(JMX_EXPORTER_CONFIG))"

web-build:
	cd web && ./mvnw package -DskipTests

web-clean:
	cd web && ./mvnw clean

# JMX exporter Java agent — downloaded on demand. Idempotent:
# Make's file-target rule re-fetches only if the JAR is missing.
# Direct GitHub-release URL works without auth.
jmx-exporter: $(JMX_EXPORTER_JAR)

$(JMX_EXPORTER_JAR):
	@echo "Fetching JMX exporter $(JMX_EXPORTER_VERSION) into $(JMX_EXPORTER_DIR)/"
	@mkdir -p $(JMX_EXPORTER_DIR)
	@curl -fSL --retry 3 -o $(JMX_EXPORTER_JAR) $(JMX_EXPORTER_URL)
	@echo "  done: $$(du -h $(JMX_EXPORTER_JAR) | cut -f1)"

# Catch-all for any other targets (like Driver*, *Test, joc1, etc.)
%:
	@$(MAKE) -C FONTS $@
