#!/bin/bash
if [ "$OSTYPE" == "msys" -o "$OSTYPE" == "cygwin" -o "$OSTYPE" == "darwin"* ] ; then
	echo -e "\e[37;2m[Compiling...]"
	javac src/*.java
	echo -e "\e[37m[Creating executable...]\e[2m"
	#jar cvfm ExpertSystem.jar manifest.fm "src/*"
	echo -e "\e[1;92m[Ok]\e[0m"
else
	echo "Can't recognize OSTYPE. Try to build manually."
fi