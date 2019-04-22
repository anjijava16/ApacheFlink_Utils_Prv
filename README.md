# Apache Flink:

Apache Flink is an open source platform which is a streaming data flow engine that provides communication, fault-tolerance, and data-distribution for distributed computations over data streams. Flink is a top-level project of Apache.

It is a scalable data analytics framework that is fully compatible with Hadoop. Flink can execute both stream processing and batch processing easily.
# 
Version:flink-1.2.0
vi flink-conf.yaml(change port number )
./bin/start-local.sh

#

Run Flink Job(Jar file) $./bin/flink run wordcount.jar --port 9000

See: UI running jobs http://ip:8080

logs: cd flink-1.20/log/
tail -100f flink_jobmanager.com.out

Kernal: Runtime (Distributed Streaming Dataflow

# API & Libraries:
	 DataSetAPI(Batch Processing)
	 DataStraem API (Stream Processing)
	 Flink ML
	 Gelly (Graph)
	 Table(SQL )
	 Table(SQL using DataStream)
 
 Deploy:
  Local JVM(Single JVM)
  Cluster( Standalone,Yarn,Mesos,tez)
  Cloud (Google GCE,Amazon EC2)
 
 Storage:
  Local FS
  HDFS ,S3
 
 DataBase:
   MonoDB,Hbase,SQL,Cassandra,Any File System
 
 Streams:
   RabbitMQ,Kafka,Flume ,MQTT
   

refer: github flink
Project: flink-examples



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
