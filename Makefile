SOURCES=$(shell find src -name '*.java')
MAIN=bin/Main.class
DOXYGEN=doc/Doxygen

# Default target: called with just make
# Creates the directory if needed
$(MAIN): $(SOURCES)
	@mkdir -p bin
	@javac -d bin -sourcepath src src/Main.java

# Creates the directory if needed
$(DOXYGEN):
	@mkdir -p doc/Doxygen
	@doxygen Doxyfile

doc: $(DOXYGEN)

clean:
	rm -rf bin/*

# Compiles and executes a driver
.SILENT:
Driver%:
	@mkdir -p bin
	@javac -d bin -sourcepath src src/domain/tests/$@.java
	-java -cp bin domain.tests.$@

.SILENT:
test:
	@javac -d bin -sourcepath src -cp lib/junit-4.12.jar src/domain/tests/DatasetTest.java
	-java -cp bin:lib/* org.junit.runner.JUnitCore domain.tests.DatasetTest

# Running the project requires Main.class to exist
.SILENT:
run: $(MAIN)
	-java -cp bin Main

.SILENT:
run-cli: $(MAIN)
	-java -cp bin Main CLI