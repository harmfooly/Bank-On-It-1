# Makefile for Bank-On-It
# Assumes the code is saved as CheckingAccount.java (the public class's name)

JAVAC = javac
JAVA  = java
SRC   = CheckingAccount.java
MAIN  = CheckingAccount

.PHONY: all run clean cleandata

# Default target: compile everything
all:
	$(JAVAC) $(SRC)

# Build (if needed) and start the program at the bank menu
run: all
	$(JAVA) $(MAIN)

# Remove compiled class files
clean:
	rm -f *.class

# Remove compiled files AND saved customer data (resets to sample customers)
cleandata: clean
	rm -f customers.ser
