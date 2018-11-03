#!/bin/bash
if [ $# -eq 0 ]
	then
echo "	*************Input Generator**********"
echo "	*************Usage: bash Run.sh Executable [args]**********"
echo "	Edit InputGenerator.sh script to Change Inputs"
fi
echo "	Generating The Inputs"
if test $# -ge 2
	then
	bash $CPA/InputGenerator.sh $2 > $CPA/Inputs.dat
	else
bash $CPA/InputGenerator.sh > $CPA/Inputs.dat
fi
echo "	Generated Inputs:"
cat $CPA/Inputs.dat | more
echo "Executing The Program"
if test $# -ge 1
	then
/usr/bin/time -f "%e" -o $CPA/ptime.dat $1 < $CPA/Inputs.dat
tm=$(cat $CPA/ptime.dat)
if [ $SHOW_GRAPH -eq 1 ]
	then
echo "$2 $(echo $tm*$ACCURACY | bc)" > $CPA/time.dat
java -cp $CPA/GraphManager GraphClient $CPA/time.dat
else
echo "$2 $(echo $tm*$ACCURACY | bc)" >> $CPA/time.dat 
fi
fi
