#!/bin/bash
if [ "$OSTYPE" == "msys" -o "$OSTYPE" == "cygwin" -o "$OSTYPE" == "darwin"* ] ; then
	echo -e "\e[37;2m[Compiling...]"
	find * -name "*.java" > sources.txt
	javac -sourcepath . @sources.txt
	echo -e "\e[1;92m[Ok]\e[0m"
else
	echo "Can't recognize OSTYPE. Try to build manually."
fi