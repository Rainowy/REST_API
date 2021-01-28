#!/bin/sh
echo Waiting for rabbitmq service start...;
while ! curl -s http://rabbitmq:15672 >> temp.html;
do
  sleep 1
done
echo Connected!;

sh entrypoint.sh

