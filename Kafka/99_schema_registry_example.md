# Kafka 기초 다지기

#### Docker로 Schema Registry 실습해보기

#### **목차**

1. [**준비작업**](#1-준비작업)
2. [**테스트용 토픽 생성 및 메시지 전송**](#-2-테스트용-토픽-생성-및-메시지-전송)
3. [**스키마 레지스트리 활용**](#3-스키마-레지스트리-활용)
4. [**REST 프록시 사용**](#3-REST-프록시-사용)
5. [**Control Center 시작하기**](#5-Control-Center-시작하기)



#### :house: [**돌아가기**](https://github.com/3457soso/TIL/tree/master/Kafka)



___

#### 1. 준비작업

**Zookeeper 실행**

```
docker run -d \
  --net=confluent \
  --name=zookeeper \
  -e ZOOKEEPER_CLIENT_PORT=2181 \
  confluentinc/cp-zookeeper:5.1.0
```
> 포트 2181으로 지정

___

**Kafka 실행**

```
docker run -d \
  --net=confluent \
  --name=kafka \
  -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  confluentinc/cp-kafka:5.1.0
```
> 2181은 미리 설정해 둔 주키퍼 포트

___

**Schema Registry 실행**

```
docker run -d \
--net=confluent \
--name=schema-registry \
-e SCHEMA_REGISTRY_KAFKASTORE_CONNECTION_URL=zookeeper:2181 \
-e SCHEMA_REGISTRY_HOST_NAME=schema-registry \
-e SCHEMA_REGISTRY_LISTENERS=http://0.0.0.0:8081 \
confluentinc/cp-schema-registry:5.1.0
```
> 포트를 8081으로 지정



___

#### 2. 테스트용 토픽 생성 및 메시지 전송

**토픽 생성**

```
docker run \
--net=confluent \
--rm confluentinc/cp-kafka:5.1.0 \
kafka-topics --create --topic ${토픽 이름} --partitions 1 --replication-factor 1 \
--if-not-exists --zookeeper zookeeper:2181
```
`Created topic "foo".`

___

**토픽 검증 (verify)**

```
docker run \
--net=confluent \
--rm \
confluentinc/cp-kafka:5.1.0 \
kafka-topics --describe --topic foo --zookeeper zookeeper:2181
```

`Topic:foo	PartitionCount:1	ReplicationFactor:1	Configs:`
`Topic: foo	Partition: 0	Leader: 1001	Replicas: 1001	Isr: 1001`

___

**새 데이터 pub**

```
docker run \
--net=confluent \
--rm \
confluentinc/cp-kafka:5.1.0 \
bash -c "seq 42 | kafka-console-producer --request-required-acks 1 \
--broker-list kafka:9092 --topic foo && echo '${메시지 내용}"
```

`Produced 42 messages. // 메시지 내용`

___

**생성한 데이터 sub (built-in Consumer)**

```
docker run \
--net=confluent \
--rm \
confluentinc/cp-kafka:5.1.0 \
kafka-console-consumer --bootstrap-server kafka:9092 --topic ${토픽 이름} --from-beginning --max-messages 42
```

`1`
`....`
`42`
`Processed a total of 42 messages`



___

#### 3. 스키마 레지스트리 활용

**interactive (-it) 모드로 실행**

```
docker run -it --net=confluent --rm confluentinc/cp-schema-registry:5.1.0 bash
```
___

**스키마 검증 (validate)**

```
# /usr/bin/kafka-avro-console-producer \
  --broker-list kafka:9092 --topic bar \
  --property schema.registry.url=http://schema-registry:8081 \
  --property value.schema='{"type":"record","name":"myrecord","fields":[{"name":"f1","type":"string"}]}'
```

`Error deserializing json  to Avro of schema {"type":"record","name":"myrecord","fields":``[{"name":"f1","type":"string"}]}`
> 스키마와 맞지 않는 값을 보내면 에러를 뱉는다



___

#### 4. REST 프록시 사용

**REST 프록시 실행**

```
docker run -d \
--net=confluent \
--name=kafka-rest \
-e KAFKA_REST_ZOOKEEPER_CONNECT=zookeeper:2181 \
-e KAFKA_REST_LISTENERS=http://0.0.0.0:8082 \
-e KAFKA_REST_SCHEMA_REGISTRY_URL=http://schema-registry:8081 \
-e KAFKA_REST_HOST_NAME=kafka-rest \
confluentinc/cp-kafka-rest:5.1.0
```

___

**interactive (-it) 모드로 실행**

```
docker run -it --net=confluent --rm confluentinc/cp-schema-registry:5.1.0 bash
```

___

**컨슈머 인스턴스 생성**

```
curl -X POST -H "Content-Type: application/vnd.kafka.v1+json" \
  --data '{"name": "my_consumer_instance", "format": "avro", "auto.offset.reset": "smallest"}' \
  http://kafka-rest:8082/consumers/my_avro_consumer
```

`{"instance_id":"my_consumer_instance","base_uri":"http://kafka-rest:8082/consumers/my_avro_consumer/instances/my_consumer_instance"}`

___

**bar 토픽의 데이터 가져오기**

```
curl -X GET -H "Accept: application/vnd.kafka.avro.v1+json" \
  http://kafka-rest:8082/consumers/my_avro_consumer/instances/my_consumer_instance/topics/bar
```

`[{"key":null,"value":{"f1":"value1"},"partition":0,"offset":0},{"key":null,"value":{"f1":"value2"},"partition":0,"offset":1},{"key":null,"value":{"f1":"value3"},"partition":0,"offset":2}]`



___

#### 5. Control Center 시작하기

**실행부터**

```
docker run -d \
--name=control-center \
--net=confluent \
--ulimit nofile=16384:16384 \
-p 9021:9021 \
-v /tmp/control-center/data:/var/lib/confluent-control-center \
-e CONTROL_CENTER_ZOOKEEPER_CONNECT=zookeeper:2181 \
-e CONTROL_CENTER_BOOTSTRAP_SERVERS=kafka:9092 \
-e CONTROL_CENTER_REPLICATION_FACTOR=1 \
-e CONTROL_CENTER_MONITORING_INTERCEPTOR_TOPIC_PARTITIONS=1 \
-e CONTROL_CENTER_INTERNAL_TOPICS_PARTITIONS=1 \
-e CONTROL_CENTER_STREAMS_NUM_STREAM_THREADS=2 \
-e CONTROL_CENTER_CONNECT_CLUSTER=http://kafka-connect:8082 \
confluentinc/cp-enterprise-control-center:5.1.0
```

> HTTP 인터페이스로, 포트를 9092로 지정해서 사용함!
> 접속할 때, 도커 썼으면 `http://${host-ip}:9092`

___

**테스트용 토픽 생성**

```
docker run \
--net=confluent \
--rm confluentinc/cp-kafka:5.1.0 \
kafka-topics --create --topic c3-test --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181
```

`Created topic "c3-test".`

___

**콘솔 프로듀서로 메시지 전송**

```
  while true;
do
  docker run \
    --net=confluent \
    --rm \
    -e CLASSPATH=/usr/share/java/monitoring-interceptors/monitoring-interceptors-5.1.0.jar \
    confluentinc/cp-kafka-connect:5.1.0 \
    bash -c 'seq 10000 | kafka-console-producer --request-required-acks 1 --broker-list kafka:9092 --topic c3-test --producer-property interceptor.classes=io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor --producer-property acks=1 && echo "Produced 10000 messages."'
    sleep 10;
done
```

`Produced 10000 messages`

___

**콘솔 컨슈머로 데이터 소비**

```
OFFSET=0
  while true;
do
  docker run \
    --net=confluent \
    --rm \
    -e CLASSPATH=/usr/share/java/monitoring-interceptors/monitoring-interceptors-5.1.0.jar \
    confluentinc/cp-kafka-connect:5.1.0 \
    bash -c 'kafka-console-consumer --consumer-property group.id=qs-consumer --consumer-property interceptor.classes=io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor --bootstrap-server kafka:9092 --topic c3-test --offset '$OFFSET' --partition 0 --max-messages=1000'
  sleep 1;
  let OFFSET=OFFSET+1000
done
```

`1`
`....`
`1000`
`Processed a total of 1000 messages`

> 이후 UI를 통해 경과를 확인할 수 있다