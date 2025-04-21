
javac -cp ".;lib/mysql-connector-j-9.3.0.jar" *.java

if ($?) {
    java -cp ".;lib/mysql-connector-j-9.3.0.jar" ExpenseTracker
}
