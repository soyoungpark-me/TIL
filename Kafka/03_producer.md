# Kafka 기초 다지기

 **출처 : [카프카 핵심 가이드 (O'Reilly)](https://book.naver.com/bookdb/book_detail.nhn?bid=14093855)**

#### 목차

1. [카프카 훑어보기](https://github.com/3457soso/TIL/blob/master/Kafka/01_Introduction.md)
2. [범용 메시지 큐와 비교하기](https://github.com/3457soso/TIL/blob/master/Kafka/02_compare.md)
3. **카프카 프로듀서 : 카프카에 메시지 쓰기**
   - [**프로듀서 개요**](#1-프로듀서-개요)
   - [**카프카에 메시지 전송하기**](#2-카프카에-메시지-전송하기)
   - [**직렬처리기**](#3-직렬처리기)
   - [**파티션**](#4-파티션)
4. [카프카 프로듀서 : 중요 개념](https://github.com/3457soso/TIL/blob/master/Kafka/04_consumer_core.md)
5. [카프카 컨슈머 : 카프카에서 데이터 읽기](https://github.com/3457soso/TIL/blob/master/Kafka/05_consumer_use.md)
6. [스키마 레지스트리](https://github.com/3457soso/TIL/blob/master/Kafka/06_schema_registry.md)
7. [카프카 내부 메커니즘](https://github.com/3457soso/TIL/blob/master/Kafka/07_inside.md)
8. [신뢰성 있는 데이터 전달](https://github.com/3457soso/TIL/blob/master/Kafka/08_reliability.md)
9. [데이터 파이프라인 구축하기](https://github.com/3457soso/TIL/blob/master/Kafka/09_data_pipeline.md)

- [confluent 예제](https://github.com/3457soso/TIL/blob/master/Kafka/99_confluent_example)
- [schema registry 예제](https://github.com/3457soso/TIL/blob/master/Kafka/99_schema_registry_example)



------

## **카프카 프로듀서 : 카프카에 메시지 쓰기**

### 1. 프로듀서 개요

#### 1) 카프카 프로듀서 

- 아파치 카프카는 **클라이언트 API**를 포함해 배포된다.
- 이것을 사용해 개발자는 카프카의 **프로듀서**와 **컨슈머** 애플리케이션을 개발할 수 있다.
- **요구사항**
  - 모든 메시지가 중요한가?
  - 메시지가 일부 유실되어도 괜찮은가?
  - 반드시 충족해야 하는 처리 대기 시간이나 처리량이 있는가?



#### 2) 카프카 프로듀서의 작업 처리 개요

![이미지](https://www.learningjournal.guru/_resources/img/jpg-7x/kafka-producer-workflow.jpg)

1. 카프카에 쓰고자 하는 메시지를 가지는 `ProducerRecode`를 생성한다
2. **직렬처리기** : 키와 값의 쌍으로 구성되는 메시지 객체들이 네트워크로 전송될 수 있도록 바이트 배열로 **직렬화**한다
3. 해당 데이터는 **파티셔너** 컴포넌트에 전달된다.
4. 같은 토픽과 파티션으로 전송될 레코드들을 모은 **레코드 배치**에 추가한다.
5. 별개의 스레드가 그 배치를 **카프카 브로커에게 전달**한다.
6. **브로커**는 수신된 레코드의 메시지를 처리한 후 **응답을 전송**한다.
   - 메세지가 **성공**적으로 쓰이면 `RecordMetadata`를 반환한다.
   - 이 객체는 토픽, 파티션, 파티션 내부의 메시지 오프셋을 갖는다.
   - **실패**하면 에러를 반환한다.



#### 3) 프로듀서 설정하기

- `bootstrap.servers` : 카프카 클러스터에 최초로 연결하기 위해 프로듀서가 사용하는 브로커들의 `host:port` 정보
- `key.serializer` : 프로듀서가 생성하는 레코드의 메시지 키를 직렬화하기 위해 사용되는 클래스
- `value.serializer` : 레코드의 메시지 값을 직렬화하는 데 사용되는 클래스

``` java
private Properties kafkaProps = new Properties();

kafkaProps.put("bootstrap.servers", "broker1:9092,broker2:9092");
kafkaProps.put("key.serializer", 	"org.apache.kafka.common.serialization.StringSerializer");
kafkaProps.put("value.serializer", 	"org.apache.kafka.common.serialization.StringSerializer");

Producer<String, String> producer = 
    new KafkaProducer<String, String> (kafkaProps);
```

- **메시지 전송의 3가지 방법**
  - **Fire-and-forget (전송 후 망각)** : 메시지를 전송만 하고 후속 조치 X
  - **Synchronous send (동기식 전송)** : 전송 후 `Future`객체 반환
  - **Asynchronous send (비동기식 전송)** : 전송 후 `Callback` 호출



___

### 2. 카프카에 메시지 전송하기

#### 1) 간단한 예시

```java
ProducerRecode<String, String> record = 
    new ProducerRecode<>("CustomerCountry", "Precision Products", "France");

try {
    producer.cend(record);
} catch (Exception e) {
    e.printStackTrace();
}
```

- 프로듀서는 메시지를 갖는 레코드 (`ProducerRecode`) 객체를 전송하므로, 이 객체를 먼저 생성한다.
- 키와 값의 타입은 **직렬처리기**와 **프로듀서 객체**에서 사용하는 타입과 같아야 한다.
- `send()` 메소드를 사용해 레코드를 전송한다.
  - 이 메시지는 버퍼에 수록된 후 **별개의 스레드**에서 브로커로 전송된다.
- **발생할 수 있는 오류들**
  - `SerializationException` : 메시지 직렬화에 실패하는 경우
  - `BufferExhaustedException` | `TimeoutException` : 버퍼가 가능 찬 경우
  - `InterruptException` : 메시지를 전송하는 스레드가 중단되었을 경우



#### 2) 동기식으로 메시지 전송하기

```java
ProducerRecode<String, String> record = 
    new ProducerRecode<>("CustomerCountry", "Precision Products", "France");

try {
    producer.cend(record).get();
} catch (Exception e) {
    e.printStackTrace();
}
```

- `Future` 객체의 `get()` 메소드를 사용해 카프카의 응답을 기다린다.

- `ProducerRecord` 객체가 카프카에 성공적으로 전송되지 못하면 **이 메소드에서 에러 발생!**

- **발생할 수 있는 에러의 종류**

  1. **재시도 가능한 (retriable) 에러** : 메시지를 다시 전송하면 해결될 수 있음 (Ex. 연결 문제)
  2. **그렇지 않은 에러** : 재시도하지 않고 즉시 에러 반환 (Ex. 메시지 크기가 너무 큰 경우)

  

#### 3) 비동기식으로 메시지 전송하기

```java
priate class DemoProducerCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e != null) P
        	e.printStackTrace();
    }
}

ProducerRecode<String, String> record = 
    new ProducerRecode<>("CustomerCountry", "Precision Products", "France");

producer.send(record, new DemoProducerCallback());
```

- 각 메시지를 전송하고 응답을 기다리면 **100개의 메시지를 전송하는 데 특정 시간이 소요**될 것.
- 모든 메시지를 **전송만 하고 응답을 기다리지 않으면** 전송하는 데 거의 시간이 소요되지 않는다.
- 비동기식으로 메시지를 전송하고, 이 때 발생할 수 있는 에러를 처리하기 위해 **콜백**을 추가할 수 있다.



___

### 3. 직렬처리기

#### 1) 기본 직렬처리기

- 프로듀서의 필수 구성에는 **직렬처리기**가 포함된다.
- `StringSerializer`, `IntegerSerializer` 등등의 기본 직렬처리기는 내장되어 있다.



#### 2) 커스텀 직렬처리기

- 하지만 기본 직렬처리기만으로는 모든 데이터의 직렬화를 충족시킬 수 없다.

  - 이럴 경우 또 다른 직렬처리기가 필요할 수 있다.

- 카프카로 전송해야 하는 데이터가 **단순한 문자열이나 정수가 아닐 때** : 범용 직렬화 라이브러리를 사용할 수 있다.

  Ex)  `Avro`, `Thrift`, `Protobuf` 등

  - 직접 커스텀 직렬처리기를 만들어 쓰는 것보다 **범용 직렬화 라이브러리**를 쓰는 게 좋다.

```java
public class CustomerSerializer implements Serializer<Customer> {
    @Override
    public void configure(Map configs, boolean isKey) {
        // 구성이 필요한 내용
    }
    
    @Override
    /**
    Customer의 직렬화는 다음과 같이 한다.
    - customerId를 나타내는 4바이트의 정수
    - customerName의 길이를 나타내는 4바이트의 정수
    - customerName의 내용이 들어간느 N바이트의 문자열
    */
    public byte[] serialize(String topic, Customer data) {
        try {
            byte[] serialziedName;
            int stringSize;
            if (data == null)
                return null;
            else {
                if (data.getName() != null) {
                    serializedName = data.getName.getBytes("UTF-8");
                    stringSize = serializedName.length;
                } else {
                    serializedName = new byte[0];
                    stringSize = 0;
                }
            }
            
            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
            buffer.putInt(data.getID());
            buffer.putInt(stringSize);
            buffer.put(serializedName);
            
            return buffer.array();
        } catch (Exception e) {
            throw new SerializationException();
        }
    }
    
    @Override
    public void close() {
        // close 해줘야 할 내용
    }
}
```

- **취약점**
  - 고객이 굉장히 많아져 `Integer` 타입을 `Long` 타입으로 변경해야 하거나 새 필드를 추가해야 하면,
  - **기존 메시지와 새 메시지 간의 호환성**을 유지하는 게 어려워진다.
  - 여러 곳에서 이 메시지를 가져다 쓴다면, **모두 이 직렬처리기를 사용하도록 수정**해줘야 한다.



#### 3) 범용 직렬처리기 (Apache Avro)

- **아파치 Avro**

  - 언어 중립적인 데이터 직렬화 시스템이다.
  - 언어 독립적인 스키마로 데이터 구조를 표현하는데, 주로 `JSON` 형식으로 기술한다.
  - 직렬화 역시 `JSON`을 지원하지만, **주로 이진 파일을 사용**한다.
  - **Avro**가 파일을 읽고 쓸 때에는 **스키마가 있다고 간주**한다. (Avro 파일에는 스키마가 포함된다.)
  - **Avro** 파일은 헤더와 데이터 블록으로 구성되며, **헤어데 스키마가 저장**된다.

- **Avro**가 카프카와 같은 **메시지 시스템에 적합한 이유**

  - 메시지를 쓰는 애플리케이션이 **새로운 스키마로 전환**해도, 해당 메시지를 읽는 애플리케이션은 **일체의 변경 없이 계속해서 메시지를 처리할 수 있다**
  - 스키마를 변경해도 **에러가 발생하지 않고**, **기존 데이터를 변경하지 않아도 된다**

- **주의사항**

  - 데이터를 쓰는 데 사용되는 스키마와 **읽는 애플리케이션에서 기대하는 스키마**가 **호환**되어야 함!
  - **역직렬처리기는 데이터를 쓸 때 사용되었던 스키마를 사용해야 한다**

  

___

### 4. 파티션

#### 1) 기본 파티션

- 우리가 생성한 `ProducerRecord` 객체는 **토픽 이름**과 **키**, **값**을 포함한다.
- 카프카 메시지는 **키와 값의 쌍**으로 구성되지만, 기본값이 null로 설정된 키와 함께 **토픽과 값만 갖는** `ProducerRecord` 객체를 생성할 수도 있다.
- **키의 목적**
  - 메시지를 식별하는 추가 정보를 갖는 것
  - 메시지를 쓰는 토픽의 여러 파티션 중 **하나를 결정**하는 것
  - **같은 키를 같은 모든 메시지는 같은 파티션에 저장**된다.
- **기본 파티셔너**
  - 키가 null이면서 카프카의 **기본 파티셔너**를 사용하면, 
    - 사용 가능한 토픽의 파티션들 중 하나가 **무작위로 선택**됨
    - 각 메시지에 저장된느 메시지 개수의 균형을 맞추기 위해 **라운드 로빈** 알고리즘 사용!
  - 키가 있으면서 **기본 파티셔서** 사용
    - 카프카에서 키의 해시 값을 구한 후,
    - 그 값에 따라 **특정 파티션에 메시지 저장**

#### 2) 커스텀 파티셔너 구현하기

- 반드시 키의 해시 값을 이용해 파티션을 찾아야 하는 것은 아니다!
- 이럴 때는 **커스텀 파티셔너**를 구현해 사용한다.