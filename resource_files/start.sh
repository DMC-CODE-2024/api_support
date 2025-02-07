#!/bin/bash

module_name="api_manual_pairing"
main_module="api_service" #keep it empty "" if there is no main module 
log_level="INFO" # INFO, DEBUG, ERROR

########### DO NOT CHANGE ANY CODE OR TEXT AFTER THIS LINE #########


. ~/.bash_profile

build="${module_name}.jar"

pid=`ps -ef | grep $build | grep java | grep $module_name | grep -v grep | awk '{print $2}'`

if [ "${pid}" != "" ]  ## Process is currently running
then
  echo "${module_name} process is currently running with PID ${pid} ..."

else  ## No process running

  if [ "${main_module}" == "" ]
  then
     build_path="${APP_HOME}/${module_name}_module"
     log_path="${LOG_HOME}/${module_name}_module"
  else
     if [ "${main_module}" == "utility" ] || [ "${main_module}" == "api_service" ] || [ "${main_module}" == "gui" ]
     then
       build_path="${APP_HOME}/${main_module}/${module_name}"
       log_path="${LOG_HOME}/${main_module}/${module_name}"
     else
       build_path="${APP_HOME}/${main_module}_module/${module_name}"
       log_path="${LOG_HOME}/${main_module}_module/${module_name}"
     fi
  fi

  cd ${build_path}

  ## Starting the process

  echo "Starting ${module_name} module..."

#   java -Xmx1024m -Xms256m -Dlog4j.configurationFile=./log4j2.xml -Dspring.config.location=file:./application.yml -jar $PNAME REMINDER_UTILITY duplicate

  java -Dlog4j.configurationFile=./log4j2.xml -Dlog.level=${log_level} -Dlog.path=${log_path} -Dmodule.name=${module_name} -Dspring.config.location=file:./application.properties,file:${commonConfigurationFile} -jar ${build} 1>/dev/null 2>${log_path}/${module_name}.error &

  ## check if process started successfully or not

  pid=`ps -ef | grep $build | grep java | grep $module_name | grep -v grep | awk '{print $2}'`
  if [ "$pid" == "" ]
  then

    echo "Failed to start $module_name process !!!"

  else
    
    echo "$module_name process is started successfully with PID ${pid} ..."

  fi
  
fi
