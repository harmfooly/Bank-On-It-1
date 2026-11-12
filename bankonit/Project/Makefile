JAVAC = javac
JAVA  = java
SRC   = CheckingAccount.java
MAIN  = CheckingAccount

.PHONY: all run clean cleandata

all:
	$(JAVAC) $(SRC)

run: all
	$(JAVA) $(MAIN)

clean:
	rm -f *.class

cleandata: clean
	rm -f customers.ser
