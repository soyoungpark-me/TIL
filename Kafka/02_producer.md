# Kafka 기초 다지기

 **출처 : [카프카 핵심 가이드 (O'Reilly)](https://book.naver.com/bookdb/book_detail.nhn?bid=14093855)**

#### 목차

1. 카프카 훑어보기
2. **카프카 프로듀서 : 카프카에 메시지 쓰기**
   - 프로듀서 개요
   - 
3. 카프카 컨슈머 : 카프카에서 데이터 읽기
4. 카프카 내부 메커니즘
5. 신뢰성 있는 데이터 전달
6. 데이터 파이프라인 구축하기
7. 크로스 클러스터 데이터 미러링
8. 카프카 관리하기
9. 카프카 모니터링
10. 스트림 프로세싱

------

## **카프카 프로듀서 : 카프카에 메시지 쓰기**

### 1. 프로듀서 개요

#### 1) 

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