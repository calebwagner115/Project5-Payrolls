#!/bin/bash
# set -x                          # echo on

for i in {1..13}; do
    echo --------------------------
    echo "Implementation $i"
    javac -cp .:junit-4.12.jar:imp$i *.java
    java -cp .:junit-4.12.jar:imp$i PayrollTest
done | grep -e "Tests run\|Implementation\|OK "
