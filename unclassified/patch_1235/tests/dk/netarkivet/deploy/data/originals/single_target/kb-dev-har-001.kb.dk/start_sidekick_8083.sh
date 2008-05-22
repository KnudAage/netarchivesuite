#!/bin/bash
export CLASSPATH=/home/dev/UNITTEST/lib/dk.netarkivet.harvester.jar:/home/dev/UNITTEST/lib/dk.netarkivet.archive.jar:/home/dev/UNITTEST/lib/dk.netarkivet.viewerproxy.jar:/home/dev/UNITTEST/lib/dk.netarkivet.monitor.jar:$CLASSPATH;
cd /home/dev/UNITTEST
java -Xmx1536m -Ddk.netarkivet.settings.file=/home/dev/UNITTEST/conf/settings_harvester_8083.xml -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.Jdk14Logger -Djava.util.logging.config.file=/home/dev/UNITTEST/conf/log_sidekick.prop -Dsettings.common.jmx.port=8105 -Dsettings.common.jmx.rmiPort=8205 -Dsettings.common.jmx.passwordFile=/home/dev/UNITTEST/conf/jmxremote.password -Djava.security.manager -Djava.security.policy=/home/dev/UNITTEST/conf/security.policy  dk.netarkivet.harvester.sidekick.SideKick dk.netarkivet.harvester.sidekick.HarvestControllerServerMonitorHook ./conf/start_harvester_8083.sh  < /dev/null > start_sidekick_8083.sh.log 2>&1 &
