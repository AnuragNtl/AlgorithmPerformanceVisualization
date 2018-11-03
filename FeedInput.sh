#!/bin/bash
#Use This Script to dynamically run $CPA/Run.sh
#Pass Parameters To Run.sh
#Example $CPA/Run.sh "MyProgram arg1 arg2" 20000
#This will be passed to InputGenerator.sh
#where you can use $1 to acccess this argument
nano $CPA/InputGenerator.sh
LIVE_SHOW_GRAPH=1
SHOW_GRAPH_LATER=2
declare -r LIVE_SHOW_GRAPH
declare -r SHOW_GRAPH_LATER

###########################Choose between LIVE_SHOW_GRAPH(Show Time
#Graph while running,and
#SHOW_GRAPH_LATER(Show Graph After Running)################################
export SHOW_GRAPH=$LIVE_SHOW_GRAPH
##################################################

export TIMEFORMAT="%3R"
rm $CPA/Inputs.dat
rm $CPA/outputs.dat
rm $CPA/time.dat
if test $SHOW_GRAPH -eq 1
	then
	java -cp $CPA/GraphManager GraphManager&
fi
####################################Change Accuracy#################################################
#set accuracy(anywhere from 0.1 to 1000)
#in calculating time:ex:1000 for milliseconds,1 for seconds,0.01666667(1/60) for minutes
export ACCURACY=100
####################################Change Accuracy#################################################

####################################FeedInput Script Here###########################################
for((i=1;i<=100;i++))
do
bash $CPA/Run.sh "$1" $i
done
####################################FeedInput Script Here###########################################
