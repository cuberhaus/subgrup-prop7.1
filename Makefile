SOURCES=$(shell find src -name '*.java')
MAIN=bin/Main.class

# Default target: called with just make
# Creates the directory if needed
$(MAIN): $(SOURCES)
	@mkdir -p bin
	@javac -d bin -sourcepath src src/Main.java

clean:
	rm -rf bin/*

# Compiles and executes a driver
.SILENT:
Driver%:
	@mkdir -p bin
	@javac -d bin -sourcepath src src/domini/tests/$@.java
	-java -cp bin domini.tests.$@

.SILENT:
test:
	@javac -d bin -sourcepath src -cp lib/junit-4.12.jar src/domini/tests/*.java
	echo "Test ConjuntItem"
	-java -cp bin:lib/* org.junit.runner.JUnitCore ConjuntItemsTest

.SILENT:
joc1: $(MAIN)
	javac -classpath src -d bin jocs_de_proves/joc1/Joc1.java
	-java -cp bin/jocs_de_proves/joc1 Joc1


.SILENT:
joc2: $(MAIN)
	javac -classpath src -d bin jocs_de_proves/joc2/Joc2.java
	-java -cp bin/jocs_de_proves/joc2 Joc2

# Running the project requires Main.class to exist
.SILENT:
run: $(MAIN)
	-java -cp bin Main




