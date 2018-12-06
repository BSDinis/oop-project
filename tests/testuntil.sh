#!/usr/bin/env bash
#!/bin/bash
#!/usr/bin/sh
#!/bin/sh

# default
src_dir=..

if [[ $# -eq 1 ]]
then
  last_test_basename=$(basename $1 | cut -d '.' -f 1)
else
  ./testall.sh
  exit 0
fi

if ! [[ -r $src_dir/Makefile ]]
then
  echo "$0: that dir has no Makefile!"
  exit 1
fi

lightred="\e[31m"
lightgreen="\e[32;m"
red="\e[31;1m"
green="\e[32;1m"
reset="\e[0m"

# compiling: if you want to show the compilation shenanigans,
# remove everything from &> onwards onwards
echo -n "compiling... "
make -C $src_dir &> .compilation_msg
if [[ $? -ne 0 ]]
then
  echo -e "${red}failed${reset}"
  cat .compilation_msg
  rm -f .compilation_msg
  echo -e "$0: ${red}build failed"
  echo -e "$0: ${red}aborting"
  exit 1
fi
echo -e "${green}success${reset}"

rm -f .compilation_msg

if ! [[ -d results ]]
then
  mkdir results
fi

total=0
succs=0
for file in $( ls auto-tests/*.in )
do
  input=$file
  raw=$(basename $input | cut -f 1 -d'.')
  import=auto-tests/$raw.import
  expected=auto-tests/expected/$raw.out
  out=results/$raw.outhyp
  if ! [[ -r $input ]]
  then
    echo -e "$0: cannot read file "$input"${red}aborting"
    exit 1
  fi
  if ! [[ -r $import ]]
  then
    echo -e "$0: cannot read file $import${red}aborting"
    exit 1
  fi
  if ! [[ -r $expected ]]
  then
    echo -e "$0: cannot read file $expected${red}aborting"
    exit 1
  fi

  total=$(echo "$total + 1" | bc)
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

  if [ "$raw" = "$last_test_basename" ]
  then
    break
  fi
done

disp_score=$(echo "scale=2; 100 * $succs / $total" | bc -l)
score=$(echo "100 * $succs / $total" | bc)
if [ $score -eq 100 ]
then
  echo -e "Score$green 100 [$succs/$total]"
else
  echo -e "Score$red $disp_score [$succs/$total]"
fi

echo -e -n "${reset}cleaning... " 
make clean -C .. &> /dev/null
echo "done"
echo -e "$0: finished"
