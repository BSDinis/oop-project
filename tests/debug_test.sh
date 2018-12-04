#!/bin/env bash

if [[ $# -ne 1 ]] 
then
  echo "insert test name"
  exit 1
fi

base=$(basename $1 | cut -d '.' -f 1)
a=auto-tests/$base.in
b=auto-tests/$base.import 
c=auto-tests/expected/$base.out 
d=results/$base.outhyp

echo $a
echo $b
echo $c
echo $d

vim $a $b $c $d
