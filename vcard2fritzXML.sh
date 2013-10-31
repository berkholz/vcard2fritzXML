#!/bin/bash
mvn exec:java -Dexec.args="$(echo $@)" | grep -v "^\[INFO"
