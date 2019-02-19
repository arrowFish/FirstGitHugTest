set _PATH_CUR_=%~dp0
title "yyc-qpid-tester"
java -Dfile.encoding=UTF-8 -Xmx512m -Xms64m -XX:MaxPermSize=256m -XX:MaxDirectMemorySize=128m -verbose:gc -Xloggc:./logs/gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:-TraceClassUnloading -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./logs/heapdump.hprof -cp ./*;./lib/* com.yyc.testqpid.TestqpidApplication  file:./config/application.properties
