#!/bin/bash

app_path=`dirname $0`
cp="$app_path/classes";

for i in $app_path/lib/*.jar; do
	cp="$cp:$i"
done

#echo $cp | sed -e "s,:,;,g"

java -cp "$cp" de.pd.pjc.Main $@
