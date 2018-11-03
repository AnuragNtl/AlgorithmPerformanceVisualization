#!/bin/bash
function randomString()
{
	local rs=""
	local len
	declare -i len
if [ $# -ge 1 ]
	then
	len=$1
else
	len=1
fi
for((i=0;i<$len;i++))
do
rs=$rs$(randomCharacter)
done
echo $rs
}
function randomAlphabetString()
{
	local rs=""
	local len
	declare -i len
if [ $# -ge 1 ]
	then
	len=$1
else
	len=1
fi
for((i=0;i<$len;i++))
do
rs=$rs$(randomAlphabet)
done
echo $rs
}
function randomUpperCaseString()
{
	local rs=""
	local len
	declare -i len
if [ $# -ge 1 ]
	then
	len=$1
else
	len=1
fi
for((i=0;i<$len;i++))
do
rs=$rs$(randomUpperCaseAlphabet)
done
echo $rs
}
function randomLowerCaseString()
{
	local rs=""
	local len
	declare -i len
if [ $# -ge 1 ]
	then
	len=$1
else
	len=1
fi
for((i=0;i<$len;i++))
do
rs=$rs$(randomLowerCaseAlphabet)
done
echo $rs
}
function randomAlnumString()
{
		local rs=""
	local len
	declare -i len
if [ $# -ge 1 ]
	then
	len=$1
else
	len=1
fi
for((i=0;i<$len;i++))
do
rs=$rs$(randomAlnumCharacter)
done
echo $rs
}
function randomSymbolString()
{
		local rs=""
	local len
	declare -i len
if [ $# -ge 1 ]
	then
	len=$1
else
	len=1
fi
for((i=0;i<$len;i++))
do
rs=$rs$(randomSymbol)
done
echo $rs
}
function randomUpperCaseAlphabet()
{
	printf "\x$(printf %x $(randomInteger 65 90))"
}
function randomLowerCaseAlphabet()
{
	printf "\x$(printf %x $(randomInteger 97 122))"
}
function randomSymbol()
{
	case $((RANDOM%2)) in
	0 ) printf "\x$(printf %x $(randomInteger 59 64))"
	;;
	* ) printf "\x$(printf %x $(randomInteger 91 96))"
	;;
	esac
}
function randomAlphabet()
{
local decide
	#declare -i decide
	decide=$((RANDOM%2));
	case $decide in
		0 ) randomUpperCaseAlphabet
		;;
		* ) randomLowerCaseAlphabet
		;;
	esac	
}
function randomAlnumCharacter()
{
	local decide
	#declare -i decide
	decide=$((RANDOM%3));
	case $decide in
		0 ) randomUpperCaseAlphabet
		;;
		1 ) randomLowerCaseAlphabet
		;;
		* ) randomInteger
		;;
	esac
}
function randomInteger()
{
	local len
	local ub
	local lb
	local num
	declare -i lb
	declare -i ub
	if test $# -eq 1
		then
		len=$1
	elif test $# -eq 2
		then
		len=0
		lb=$1
		ub=$2
	else
		len=1
	fi
	if [ $len -gt 0 ]
		then
	for((i=0;i<$len;i++))
	do
		num=$num$((RANDOM%10))
	done
	else
		num=$((lb+(RANDOM%(ub+1-lb))));
	fi
	echo $num
}
function randomCharacter()
{
printf "\x$(printf %x $(randomInteger 48 122))"
}
function randomByte()
{
printf "\x$(printf %x $(randomInteger 0 255))"
}
function randomFraction()
{
	local maxFLen
	if [ $# -eq 3 ]
		then
		maxFLen=$3
	else
		maxFLen=2
	fi
echo "$(randomInteger $1 $2).$(randomInteger $3)"
}
function probablyDisplay()
{
if [ ! $# -ge 2 ]
then
return 1;
fi
p2=$2
if test $((RANDOM%101)) -le $((p2))
	then
	echo $1
fi
}
function randomNegativeInteger()
{
	echo "$(probablyDisplay - 100)$(randomInteger $1 $2)"
}
function randomAnyInteger()
{
	echo "$(probablyDisplay - 50)$(randomInteger $1 $2)"
}
function randomNegativeFraction()
{
	echo "$(probablyDisplay - 100)$(randomFraction $1 $2 $3)"
}
function randomAnyFraction()
{
		echo "$(probablyDisplay - 50)$(randomFraction $1 $2 $3)"
}
