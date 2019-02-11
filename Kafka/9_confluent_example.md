#Kafka 기초 다지기

#### Kafka CI Tools 실습해보기

#### **목차**

1. [**Kafka CI Tools**](#1-Kafka-CI-Tools)

2. [**Kafka ElasticSearch Connector**](#-2-Kakfa_ElasticSearch_Connector)

   

___

### 1. Kafka CI Tools

**경로 (macOS)**

`/usr/local/Cellar/kafka/2.1.0/bin`



**kafka-topics**
Create, alter, list, and describe topics

```sh
[입력]
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

[입력]
kafka-topics --list --zookeeper localhost:2181

[출력]
__consumer_offsets
_schemas
mall_change
test
```



**kafka-console-producer**
Read data from standard output and write it to a Kafka topic

```
kafka-console-producer --broker-list localhost:9092 --topic test

> Hello Kafka
```



**kafka-console-consumer**
Read data from a Kafka topic and write it to standard output

```sh
[입력]
kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning

[출력]
Hello Kafka
```



**kafka-consumer-groups**
Check the number of messages read and written, 
as well as the lag for each consumer in a specific consumer group.

```sh
[입력]
kafka-consumer-groups  --bootstrap-server localhost:9092 --list

[출력]
logstash
console-consumer-92519
```

```sh
[입력]
kafka-consumer-groups  --bootstrap-server localhost:9092 --describe --group logstash

[출력]
TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                     HOST            CLIENT-ID
mall_change     0          2               2               0               logstash-0-c1d2cf12-8efe-47cd-8dfb-8d51b4f54e53 /127.0.0.1      logstash-0
```



___

#### 2. Kafka ElasticSearch Connector

**커넥터 종류 확인**

```
./confluent list connectors
```

**커넥터 실행**

```
./confluent load elasticsearch-sink
```

**리스트 확인**
List the connector plugins available on this worker

```
curl localhost:8083/connector-plugins | jq
```

**실행 중인 리스트 확인**
Listing active connectors on a worker

```
curl localhost:8083/connectors
./confluent status connectors
```

**상태 확인**

```
./confluent status elasticsearch-sink
```



- 편해보이기는 했지만 순수하게 Kafka에 있는 내용을 ElasticSearch로 옮기기만 하는 기능이었기 때문에
  - ElasticSearch로 보내기 전에 추가적으로 해줘야 하는 로직이 있을 수 있었음...
- 일단 Logstash를 통해 옮기는 것으로 방향을 바꾸게 되었음!
