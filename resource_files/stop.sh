#!/bin/bash

module_name="api_manual_pairing"

process=$module_name.jar

pid=`ps -ef | grep $process | grep java | grep $module_name | grep -v grep | awk '{print $2}'`

if [ "$pid" == "" ]
then

  echo "No process running for $module_name !!!"

else
  kill $pid
  echo "PID $pid for process $module_name is stopped !"

fi
