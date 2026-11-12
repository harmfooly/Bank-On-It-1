# Makefile for Bank-On-It
# Each class lives in its own .java file in this folder

JAVAC = javac
JAVA  = java
MAIN  = CheckingAccount

.PHONY: all run clean cleandata

# Default target: compile every .java file
all:
	$(JAVAC) *.java

# Build (if needed) and start the program at the bank menu
run: all
	$(JAVA) $(MAIN)

# Remove compiled class files
clean:
	rm -f *.class

# Remove compiled files AND saved customer data (resets to sample customers)
cleandata: clean
	rm -f customers.ser
