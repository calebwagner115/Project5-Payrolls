echo off
echo > results.txt
echo Running tests; output will be in results.txt
for /l %%i in (1,1,13) do (
echo --------------------------
echo Implementation %%i
echo javac -cp .;junit-4.12.jar;imp%%i *.java
javac -cp .;junit-4.12.jar;imp%%i *.java
java -cp .;junit-4.12.jar;imp%%i PayrollTest
) >> results.txt
echo Results in results.txt
