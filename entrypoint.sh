#!/bin/sh

echo Running Java App!

java -Xmx128m -jar -Dmicronaut.environments=docker people.jar