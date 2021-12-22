SOURCES=$(shell find . -name '*.java')
MAIN=../EXE/Main.class
JOC1=../EXE/jocs_de_proves/joc1/Joc1.class
JOC2=../EXE/jocs_de_proves/joc2/Joc2.class
JOC2=../EXE/jocs_de_proves/joc3/Joc3.class

# Default target: called with just make
# Creates the directory if needed
$(MAIN): $(SOURCES)
	@mkdir -p ../EXE
	@javac -d ../EXE -sourcepath . ./Main.java
	@javac -d ../EXE -sourcepath . ./jocs_de_proves/joc1/Joc1.java
	@javac -d ../EXE -sourcepath . ./jocs_de_proves/joc2/Joc2.java
	@javac -d ../EXE -sourcepath . ./jocs_de_proves/joc3/Joc3.java

clean:
	rm -rf ../EXE/domini
	rm -rf ../EXE/persistencia
	rm -rf ../EXE/presentacio
	rm -rf ../EXE/utilitats
	rm -rf ../EXE/excepcions
	rm -rf ../EXE/libs
	rm -rf ../EXE/Main.class
	rm -rf ../EXE/jocs_de_proves/joc1/Joc1.class
	rm -rf ../EXE/jocs_de_proves/joc2/Joc2.class
	rm -rf ../EXE/jocs_de_proves/joc3/Joc3.class

.SILENT:
Driver%:
	@mkdir -p ../EXE
	@javac -d ../EXE -sourcepath . ./domini/tests/$@.java
	-java -cp ../EXE domini.tests.$@

.SILENT:
%Test:
	@javac -d ../EXE -sourcepath . -cp lib/junit-4.12.jar ../FONTS/domini/tests/*.java
	-java -cp ../EXE:lib/* org.junit.runner.JUnitCore domini.tests.$@

# Running the project requires Main.class to exist
.SILENT:
run: $(MAIN)
	-java -cp ../EXE Main

.SILENT:
joc1: $(JOC1)
	-java -cp ../EXE jocs_de_proves.joc1.Joc1

.SILENT:
joc2: $(JOC2)
	-java -cp ../EXE jocs_de_proves.joc2.Joc2

.SILENT:
joc3: $(JOC3)
	-java -cp ../EXE jocs_de_proves.joc3.Joc3