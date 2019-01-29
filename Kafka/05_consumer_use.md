# Kafka 기초 다지기

 **출처 : [카프카 핵심 가이드 (O'Reilly)](https://book.naver.com/bookdb/book_detail.nhn?bid=14093855)**

#### 목차

1. [카프카 훑어보기](https://github.com/3457soso/TIL/blob/master/Kafka/01_Introduction.md)
2. [범용 메시지 큐와 비교하기](https://github.com/3457soso/TIL/blob/master/Kafka/02_compare.md)
3. [카프카 프로듀서 : 카프카에 메시지 쓰기](https://github.com/3457soso/TIL/blob/master/Kafka/03_producer.md)
4. [카프카 컨슈머 : 중요 개념](https://github.com/3457soso/TIL/blob/master/Kafka/04_consumer_core.md)
5. **카프카 컨슈머 : 카프카에서 데이터 읽기**
   - [**카프카 컨슈머 생성하기**](#1-카프카-컨슈머-생성하기)
   - [**토픽 구독과 폴링 루프**](#2-토픽-구독과-폴링-루프)
   - [**커밋하기**](#3-커밋하기)
   - [**밸런싱 리스너**](#4-밸런싱-리스너)
   - [**역직렬처리기**](#5-역직렬처리기)
6. [스키마 레지스트리](https://github.com/3457soso/TIL/blob/master/Kafka/06_schema_registry.md)
7. [카프카 내부 메커니즘](https://github.com/3457soso/TIL/blob/master/Kafka/07_inside.md)
8. [신뢰성 있는 데이터 전달](https://github.com/3457soso/TIL/blob/master/Kafka/08_reliability.md)
9. [데이터 파이프라인 구축하기](https://github.com/3457soso/TIL/blob/master/Kafka/09_data_pipeline.md)

- [confluent 예제](https://github.com/3457soso/TIL/blob/master/Kafka/99_confluent_example)
- [schema registry 예제](https://github.com/3457soso/TIL/blob/master/Kafka/99_schema_registry_example)



------

## **카프카 컨슈머 : 카프카에서 데이터 읽기**

### 1. 카프카 컨슈머 생성하기

#### 1) `KafkaConsumer`

- 메시지를 가지는 레코드의 소비를 시작하려면, 컨슈머 클래스인 `KafkaConsumer`의 인스턴스를 생성해야 한다.
  1. 컨슈머에게 전달하고자 하는 속성들을 갖는 **자바 Properties 인스턴스를 생성**한 후,
  2. 이것을 `KafkaConsumer` 생성자의 인자로 전달해 인스턴스를 생성한다.



#### 2) 컨슈머 설정하기

- `bootstrap.servers` : 카프카 클러스터에 최초로 연결하기 위해 프로듀서가 사용하는 브로커들의 `host:port` 정보

- `key.deserializer` : 프로듀서가 생성하는 레코드의 메시지 키를 역직렬화하기 위해 사용되는 클래스

- `value.deserializer` : 레코드의 메시지 값을 역직렬화하는 데 사용되는 클래스

  > 직렬화된 바이트 배열의 값을 다시 자바 객체로 환연(역직렬화)하는 클래스를 지정해야 함.

- `group.id` : 컨슈머가 속하는 **컨슈머 그룹**

  - 흔하지는 않지만, 어떤 컨슈머 그룹에 속하지 않을 수도 있음!

  

#### 3) 컨슈머 생성하기

```java
Properties props = new Properties();
props.put("bootstrap.servers", "broker1:9092,broker2:9092");
props.put("group.id", "CountryCounter");
props.put("key.deserializer", "...StringDeserializer");
props.put("value.deserializer", "...StringDeserializer");

KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
```



___

### 2. 토픽 구독과 폴링 루프

#### 1) 토픽 구독

- 컨슈머를 생성한 후에는 **하나 이상의 토픽을 구독해야한다**

- 이 때 `subscribe()` 메소드를 사용한다.

  ```java
  consumer.subscribe(Collections.singletonList("customerCountries"));
  ```

  - 정규 표현식을 매개변수로 전달해 호출할 수도 있다.

    - 다수의 토픽 이름들이 표현식과 일치할 수 있고, 
    - 해당 표현식과 일치되는 이름의 **새로운 토픽을 생성**하면 그 즉시로 **리밸런싱이 수행**된다.
    - Kafka와 다른 시스템 간에 **데이터를 복제**하는 앱에서 정규 표현식을 사용한 **다수의 토픽 구독**을 많이 씀!

    Ex) 모든 test 토픽 구독

    - `consumer.subscribe(Pattern.compile("test.*"))`	



#### 2) 폴링 루프

- **컨슈머 API의 핵심은 서버로부터 연속적으로 많은 데이터를 읽기 위해 폴링(polling)하는 루프에 있다!**
- 컨슈머의 토픽 구독 요청이 정상적으로 처리되면, 그 다음에 폴링 루프에서 데이터를 읽는 데 필요한 **모든 상세 작업을 처리**한다.

```java
try {
    while (true) { // 많은 데이터를 읽기 위해 카프카 계속 폴링
        ConsumerRecords<String, String> records = consumer.poll(100); 
        // 여기서 100은 타임아웃 간격
        // 이 안에 poll()이 안이루어지면 죽은 것으로 간주됨
        // poll()은 레코드들이 저장된 List 반환
        
        for (ConsumerRecord<String, String> record : records) {
            // 할 일 하자...
            
            int updatedCount = 1;
            
            if (custCountryMap.containsValue(record.value())) {
                updatedCount = custCountryMap.get(record.value()) + 1;
            }
            custCountryMap.put(record.value(), updatedCount);
            
            JSONObject json = new JSONObject(custCountryMap);            
        }
    }
} finally {
    customer.close(); // 컨슈머가 종료될 때는 항상 close()를 실행해야 한다!
}
```

- 폴링 루프는 `poll()` 호출이 포함된 무한 루프를 말한다.

- 컨슈머 클래스에서 스레드로 실행되는 `run()` 메소드에 포함된다.

- **이외에 하는 일**

  - 새로운 컨슈머에서 최초로 `poll()`을 호출하면 
    1. 이 메소드에서 `GroupCoordinator`를 찾고,
    2. 컨슈머 그룹에 추가시키고,
    3. 해당 컨슈머에게 할당된 파티션 내역을 받는다.
  - 리밸런싱이 생길 때 필요한 처리
  - 컨슈머가 계속 살아 동작할 수 있게 해주는 **하트비트 전송**

- **[정리]** *폴링 루프에서는 poll 메소드를 포함해 여러 가지를 처리하므로, 빠르고 효율적으로 처리되도록 해야 함*




#### 3) 어떻게 폴링 루프를 벗어나야 할까?

- 컨슈머에서는 스레드로 동작하면서 **무한의 폴링 루프**를 생성한다. 어떻게 탈출하고 **컨슈머를 종료**시킬까?

  →또 다른 스레드에서 `KafkaConsumer` 객체의 `wakeup()` 메소드를 호출해야 함!

  →만약 `main 스레드`에서 실행 중이면, 자바의 **셧다운 후크 (shutdown hook)**를 사용한다.

- `wakeup()` 메소드 사용

  - 루프 반복에서 `poll()`을 실행할 때 `WakeupException`이 전달된다!
  - 컨슈머 스레드를 종료하기 전에 **반드시**`close()` 메소드를 호출해 닫아줘야 한다!

- **셧다운 후크**란?

  - 자바 애플리케이션이 강제로 종료되면서 런타임 JVM이 셧다운 될 때, **우리가 등록한 스레드**를 먼저 실행시킴!
  - 애플리케이션이 종료될 때마다 마무리해줘야 하는 작업이 있다면 여기서 해주면 된다!

```
public static void main(String args[]) {
    // ...
    Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
            consumer.wakeup();
            
            try {
                mainThread.join();
            } catch (InterruptException e) {
                e.printStackTrace();
            }
        }
    });
    
    // ...
    
    try {
        // 루프를 계속 반복 실행... Ctrl + C 를 누르면 바로 앞의 코드에서
        // addShutdownHook()로 등록한 스레드의 run() 메소드가 실행됨
        
        while (true) {
            ConsumerRecords<String, String> records = movingAvg.consumer.poll(1000);
            
            for (ConsumerRecord<String, String> record : records) {
            	// 할 거 하자!   
            }
            for (TopicPartition tp : consumer.assignment()) {
                movingAvg.consumer.commitSync();
            }
        }
    } catch (WakeupException e) {
        // 컨슈머 스레드를 닫고 애플리케이션을 종료할 것이므로 이 에러는 무시한다.
    } finally {
        consumer.close(); // 컨슈머 스레드는 종료 전에 반드시 닫아야 한다!
    }
    
}
```



___

### 4. 커밋하기

#### 1) 현재의 오프셋 커밋하기

- 오프셋을 직접 커밋하면 **리밸런싱이 생길 때 누락 or 중복되는 메시지**를 줄일 수 있다.

- `commitSync()` : 애플리케이션이 요구할 때만 오프셋이 커밋되는데, **간단하고 신뢰도 ↑**

  - `poll()` 메소드에서 반환된 마지막 오프셋 커밋

  - 특정 이유로 커밋에 실패하면 **예외 발생**시킴!

  - **[주의]** `poll()`*에서 반환된 가장 최근의 오프셋을 커밋한다*

    →`poll()`에서 반환된 모든 레코드의 처리가 다 된 후에 호출해야 한다!

  ```java
  while (true) {
      ConsumerRecords<String, String> records = consumer.poll(100);
      
      for (ConsumerRecord<String, String> record : records) {
          // 할 일 하자!
      }    
      try {
          consumer.commitSync(); // 추가로 메시지를 폴링하기 전에 마지막 오프셋 커밋!
      } catch (CommitFailedException e) {
          log.error(e);
      }
  }
  ```



#### 2) 비동기 커밋

- 브로커가 커밋 요청에 응답할 때까지 **패키지 애플리케이션이 일시 중지**된다는 것이 수동 커밋의 단점...

  - 이로 인해 애플리케이션의 처리량을 제한한다!

- **비동기 커밋 (asynchronous commit API)** : 브로커의 커밋 응답을 기다리는 대신, **커밋 요청을 전송하고 처리 지속**

  ```java
  while (true) {
      ConsumerRecords<String, String> records = consumer.poll(100);
      
      for (ConsumerRecord<String, String> record : records) {
          // 할 일 하자!
      }    
      consumer.commitAsync(); // 마지막 오프셋을 처리하고 할 일 한다!
  }
  ```

  - 커밋이 성공하거나 재시도 불가능한 에러가 생길 때까지 `commitSync()`는 **커밋을 재시도**하지만,
  - `commitAsync()`는 **재시도 하지 않는다!**



#### 3) 동기와 비동기 커밋 함께 사용하기

- 폴링 루프의 실행이 끝나고 컨슈머를 닫기 전, 또는 리밸런싱이 시작되기 전의 **마지막 커밋**이라면...

  - *성공 여부를 추가로 확인해야 함*
  - 이럴 경우 `commitAsync()`와 `commitSync()`를 함께 사용한다.

  ```java
  try {
      while (true) {
          ConsumerRecords<String, String> records = consumer.poll(100);
          
          for (ConsumerRecord<String, String> record : records) {
              // 할 일 하자!
          }
          consumer.commitAsync(); // 모든 처리가 정상일 경우 오프셋 커밋!
      }
  } catch (Exception e) {
      log.error(e);
  } finally {
      try {
          consumer.commitSync(); // 컨슈머를 닫을 때는 재시도 가능하도록
      } finally {
          consumer.close();
      }
  }
  ```



#### 4) 특정 오프셋 커밋하기

- 만약 **더 자주 커밋**을 하고 싶다면...?

- `poll()` 메소드에서 용량이 큰 배치를 반환할 때 리밸런싱으로 인한 **중복 처리를 막고자** 한다면...?

- `commitSync()`, `commitAsync()`는 **항상 마지막으로 반환된 오프셋을 커밋**한다.

- `commitSync()`, `commitAsync()`를 호출할 때 **커밋하기를 원하는 파티션과 오프셋을 전달**할 수 있다.

  - 이 때 `Map`으로 담아서 보낸다!

  ```java
  private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
  int count = 0;
  
  // ...
  
  while (true) {
      ConsumerRecords<String, String> records = consumer.poll(100);
      
      for (ConsumerRecord<String, String> record : records) {
          // 할 일 하자!
          
          currentOffsets.put(
          	new TopicPartition(record.topic(), record.partition()),
          	new OffsetAndMetadata(record.offset() + 1, "no metadata")
          ); // 현재 오프셋을 Map에 추가!
          
          if (count % 1000 == 0)
          	consumer.commitAsync(currentOffsets, null);
          count++;
      }
  }
  ```

  

___

### 5. 리밸런싱 리스너

- 컨슈머는 종료되니 전이나 파티션 리밸런싱이 시작되기 전에 **클린업하는 처리**를 해줘야 한다.
  - Ex) 컨슈머가 파티션의 소유권을 잃게 되는 걸 알게 되면...
    - 처리했던 마지막 메시지의 오프셋을 커밋해야 하고,
    - 사용했던 파일 핸들, 데이터베이스 연결 등도 닫아야 한다!
- `ConsumerRebalancingListener` : 두 가지 메소드를 정의하고 있고, 구현해주면 된다.
  - `public void onPartitionRevoked (Collection<TopicPartition> partitions)`
    - 리밸런싱이 시작되기 전에,
    - 컨슈머가 메시지 소비를 중단한 후 호출된다
    - **오프셋을 커밋해야 하는 곳**이 바로 이 메소드!
  - `public void onPartitionAssigned (Collection<TopicPartition> partitions)`
    - 파티션이 브로커에서 재할당된 후에,
    - 컨슈머가 파티션을 새로 할당받아 메시지 소비를 시작하기 전에 호출

```
private Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

private class HandleRebalance implements ConsumerRebalanceListener {
    public void onPartitionAssigned(Collection<TopicPartition> partitions) {
    }
    
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        // 할 일 하자!
        consumer.commitSync(currentOffsets);
    }
}
```



___

### 6. 역직렬처리기

#### 1) 기본 역직렬처리기

- `Producer`에서 직렬 처리기를 썼던 것과 같이, `Consumer`에서는 역직렬처리기가 필요하다.
  - 카프카 객체로부터 받은 바이트 배열을 **자바 객체로 변환**해줘야 한다.
- `StringSerializer`, `IntegerSerializer` 등등의 기본 직렬처리기는 내장되어 있다.
- 카프카에 쓰는 메시지를 생성하기 위해 쓰이는 직렬처리기는 역직렬처리기와 궁합이 맞아야 한다.
  - Ex) `StringSerializer`로 직렬화해놓고 `IntegerDeserializer`로 역직렬화하면 잘 안됨!



#### 2) 커스텀 역직렬처리기

```java
public class CustomerDeserializer implements Deserializer<Customer> {
    
    @Override
    public void configure(Map configs, boolean isKey) {}
    
    @Override
    public Customer deserialize(String topic, byte[] data) {
        int id; // 필드를 미리 정의해둬야 한다.
        int nameSize;
        String name;
        
        try {
            if (data == null)
            	return null;
            if (data.length < 0)
            	throw new SerializationException("...");
            	
            ByteBuffer buffer = ByteBuffer.wrap(data);
            id = buffer.getInt();
            nameSize = buffer.getInt();
            byte[] nameBytes = new byte[nameSize];
            buffer.get(nameBytes);
            name = new String(nameBytes, "UTF-8");
            
            return new Customer(id, name);
        } catch (Exception e) {
            throw new SerializationException("...");
        }
    }
    
    @Override
    public void close() {
    }
}
```

- 커스텀으로 하게 되면... 필드가 바뀌거나 하게 될 시 Producer와 Consumer 코드를 모두 손대줘야 한다.

- **프로듀서와 컨슈머가 너무 밀접하게 연관**되어 

  - 유지보수가 어렵고...
  - 에러도 쉽게 발생할 수 있다!!!

  → 따라서 `JSON`, `Thrift`, `Protobuf`, `Avro`와 같은 **표준 메시지 형식**을 사용하는 것이 좋다.