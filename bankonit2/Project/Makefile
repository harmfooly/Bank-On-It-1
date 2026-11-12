JAVAC = javac
JAVA  = java
MAIN  = CheckingAccount

.PHONY: all run clean cleandata

all:
	$(JAVAC) *.java

run: all
	$(JAVA) $(MAIN)

clean:
	rm -f *.class

cleandata: clean
	rm -f customers.ser
