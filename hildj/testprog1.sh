#!/bin/bash

# testprog1.sh
# This script tests the processes.cpp executable

PROG1EXE="./processes"
PROG1ARG="sh"
echo "Running ps -A | grep $PROG1ARG | wc -l"; ps -A | grep $PROG1ARG | wc -l;
echo "Running $PROG1EXE $PROG1ARG"; $PROG1EXE $PROG1ARG;
PROG1ARG="mingetty"
echo "Running ps -A | grep $PROG1ARG | wc -l"; ps -A | grep $PROG1ARG | wc -l;
echo "Running $PROG1EXE $PROG1ARG"; $PROG1EXE $PROG1ARG;
PROG1ARG="gnome"
echo "Running ps -A | grep $PROG1ARG | wc -l"; ps -A | grep $PROG1ARG | wc -l;
echo "Running $PROG1EXE $PROG1ARG"; $PROG1EXE $PROG1ARG;