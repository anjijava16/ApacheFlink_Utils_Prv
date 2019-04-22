Port:
Version:flink-1.2.0
vi flink-conf.yaml
  change port number 
  
# Windows: 
start-cluster.bat
flink run E:/FlinkWorks/wordcount.jar --input E:/FlinkWorks/wordcount.txt --output E:/FlinkWorks/

<br/>
refer: github flink
Project: flink-examples

<br/>
# Linux: 
./bin/start-local.sh
$./bin/flink run wordcount.jar --port 9000

<br/>
See: UI running jobs 
logs: cd flink-1.20/log/
tail -100f flink_jobmanager.com.out

<br/>
 /bin/start-local.sh
http://localhost:8081/#/overview
Apache Flink
