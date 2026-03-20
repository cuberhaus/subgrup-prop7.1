# Pass all commands to the Makefile inside the FONTS directory

.PHONY: all run clean web web-build web-clean

# Default target
all:
	@$(MAKE) -C FONTS

run:
	@$(MAKE) -C FONTS run

clean:
	@$(MAKE) -C FONTS clean

# Spring Boot web interface
web:
	cd web && ./mvnw spring-boot:run

web-build:
	cd web && ./mvnw package -DskipTests

web-clean:
	cd web && ./mvnw clean

# Catch-all for any other targets (like Driver*, *Test, joc1, etc.)
%:
	@$(MAKE) -C FONTS $@
