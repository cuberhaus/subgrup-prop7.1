SOURCES=$(shell find src -name '*.java')
MAIN=bin/Main.class
JOC1=bin/jocs_de_proves/joc1/Joc1.class
JOC2=bin/jocs_de_proves/joc2/Joc2.class

# Default target: called with just make
# Creates the directory if needed
$(MAIN): $(SOURCES)
	@mkdir -p bin
	@javac -d bin -sourcepath src src/Main.java
	@javac -d bin -sourcepath src:jocs_de_proves jocs_de_proves/joc1/Joc1.java

clean:
	rm -rf bin/*

# Compiles and executes a driver
.SILENT:
Driver%:
	@mkdir -p bin
	@javac -d bin -sourcepath src src/domini/tests/$@.java
	-java -cp bin domini.tests.$@

.SILENT:
%Test:
	@javac -d bin -sourcepath src -cp lib/junit-4.12.jar src/domini/tests/*.java
	-java -cp bin:lib/* org.junit.runner.JUnitCore domini.tests.$@

# Running the project requires Main.class to exist
.SILENT:
run: $(MAIN)
	-java -cp bin Main

.SILENT:
joc1: $(JOC1)
	-java -cp bin jocs_de_proves.joc1.Joc1

.SILENT:
joc2: $(JOC2)
	-java -cp bin jocs_de_proves.joc2.Joc2
