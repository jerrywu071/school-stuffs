#!/bin/bash
# Name: Jerry Wu
# Date: Due June 12
# Description: shell script for running process ring program

args=("10 0 10""10 0 100""10 0 1000" "10 0 10000")

for arg in "${args[@]}"

do
    ./ring $arg>>timing.out
done

echo "done"