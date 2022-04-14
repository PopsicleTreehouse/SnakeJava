#!/bin/sh

clear
if [[ "$1" == "-c"  ||  ! -d "./bin/" ]]; then
    javac -d bin src/*.java
else
    echo "running without recompiling"
fi
java -cp bin SwingGraphicsGame