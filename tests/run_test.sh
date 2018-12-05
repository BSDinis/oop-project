#!/bin/env bash

if [[ $# -ne 1 ]] 
then
  echo "insert test name"
  exit 1
fi

src_dir=..

lightred="\e[31m"
lightgreen="\e[32;m"
red="\e[31;1m"
green="\e[32;1m"
reset="\e[0m"

base=$(basename $1 | cut -d '.' -f 1)
input=auto-tests/$base.in
import=auto-tests/$base.import 
expected=auto-tests/expected/$base.out 
out=results/$base.outhyp

java -Dimport=$import -Din=$input -Dout=$out -cp "/usr/share/java/po-uuilib.jar:$src_dir/sth-core/sth-core.jar:$src_dir/sth-app/sth-app.jar" sth.app.App
diff -b $expected $out &> /dev/null
if [[ $? -eq 0 ]] 
then
  echo -e "Test $raw -- ${green}OK$reset"
  succs=$(echo "$succs + 1" | bc)
else
  echo -e "Test $raw -- ${red}FAILED"
  echo -e $reset $lightred
  diff -b $expected $out 
  echo -e $reset
fi
