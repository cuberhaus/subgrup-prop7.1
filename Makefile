# Pass all commands to the Makefile inside the FONTS directory

.PHONY: all run clean web web-build web-clean help helper compile

# Default target
all: helper

helper:
	@echo "========================================"
	@echo "   PROP Recommendation System Helper    "
	@echo "========================================"
	@echo "Available commands:"
	@echo "  make run        - Launch Swing GUI"
	@echo "  make web        - Launch Web Interface"
	@echo "  make compile    - Compile all Java sources"
	@echo "  make clean      - Remove compiled .class files"
	@echo "  make help       - Show this help message"
	@echo "========================================"

compile:
	@$(MAKE) -C FONTS

help: helper
	@:

run:
	@$(MAKE) -C FONTS run

clean:
	@$(MAKE) -C FONTS clean

# Spring Boot web interface
web:
	@echo "Launching web interface on http://localhost:8081"
	cd web && ./mvnw spring-boot:run

web-build:
	cd web && ./mvnw package -DskipTests

web-clean:
	cd web && ./mvnw clean

# Catch-all for any other targets (like Driver*, *Test, joc1, etc.)
%:
	@$(MAKE) -C FONTS $@

##@ Understand (knowledge graph)

.PHONY: understand-dashboard
understand-dashboard: ## Launch the Understand Anything knowledge-graph dashboard (graph dir = repo root)
	@node -e "require(require('os').homedir()+'/.understand-anything/repo/understand-anything-plugin/packages/dashboard/launch.cjs')"
