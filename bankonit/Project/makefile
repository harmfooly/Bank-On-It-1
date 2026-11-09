Project/CheckingAccount.class: Project/CheckingAccount.java
	javac -g Project/CheckingAccount.java

clean:
	rm -f Project/*.class

run: Project/CheckingAccount.class
	java -cp Project CheckingAccount

debug: Project/CheckingAccount.class
	jdb -classpath Project CheckingAccount