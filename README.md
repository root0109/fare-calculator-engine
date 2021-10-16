TigerCard lot Problem

### Project Requirements

* JDK 16.

* Maven 3.

* Junit 5

The project can be run as follows :
1) open Run(windows +R) and type cmd
2) go to the folder where you have unzipped farecalculator
3) Run as 
 mvn exec:java -Dexec.args="<path to file>"
 
sample Run below 
mvn exec:java -Dexec.args="src/test/resources/test_weekly.txt"