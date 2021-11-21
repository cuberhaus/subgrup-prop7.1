SOURCES=$(shell find src -name '*.java')
DOXYGEN=doc/Doxygen

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

