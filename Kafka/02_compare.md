# Kafka 기초 다지기

 **출처 : [카프카 핵심 가이드 (O'Reilly)](https://book.naver.com/bookdb/book_detail.nhn?bid=14093855)**

#### 목차

1. [카프카 훑어보기](https://github.com/3457soso/TIL/blob/master/Kafka/01_Introduction.md)
2. **범용 메시지 큐와 비교하기**
   - [**메시지 큐**](#1-메시지-큐)
   - [**프로토콜 정리**](#2-프로토콜-정리)
   - [**오픈소스 메시지 큐들**](#3-오픈소스-메시지-큐들)
   - [**MQ별 성능 비교**](#4-MQ별-성능-비교)
3. [카프카 프로듀서 : 카프카에 메시지 쓰기](https://github.com/3457soso/TIL/blob/master/Kafka/02_producer.md) 
4. [카프카 컨슈머 : 중요 개념](https://github.com/3457soso/TIL/blob/master/Kafka/03_consumer_core.md)
5. [카프카 컨슈머 : 카프카에서 데이터 읽기](https://github.com/3457soso/TIL/blob/master/Kafka/04_consumer_use.md)
6. [스키마 레지스트리](https://github.com/3457soso/TIL/blob/master/Kafka/06_schema_registry.md)
7. [카프카 내부 메커니즘](https://github.com/3457soso/TIL/blob/master/Kafka/07_inside.md)
8. [신뢰성 있는 데이터 전달](https://github.com/3457soso/TIL/blob/master/Kafka/08_reliability.md)
9. [데이터 파이프라인 구축하기](https://github.com/3457soso/TIL/blob/master/Kafka/09_data_pipeline.md)

- [confluent 예제](https://github.com/3457soso/TIL/blob/master/Kafka/99_confluent_example)
- [schema registry 예제](https://github.com/3457soso/TIL/blob/master/Kafka/99_schema_registry_example)



___

## 범용 메시지 큐와 비교하기

### 1. 메시지 큐 

#### 1) 메시지 큐란?

- **메시지 지향 미들웨어 (Message Oriented Middleware: MOM)** 
  - 비동기 메시지를 사용하는 다른 응용 프로그램 사이에서 데이터의 송수신을 이용하는 것
  - 이 MOM을 구현한 시스템을 **메시지 큐 (Message Queue)** 라고 한다!
  
- 프로그래밍에서 `MQ`는 프로세스 또는 인스턴스가 **데이터를 서로 교환할 때** 사용하는 방법이다
  - 이렇게 서로 다른 프로세스나 프로그램 사이에서 메시지를 교환할 때
  - `AMQP (Advanced Message Queueing Protocol)`을 이용한다.



#### 2) 메시지 큐의 장점

- **비동기** (Asynchronous) : 큐에 넣기 떄문에 **나중에 처리할 수 있음**
  - 사용자는 빠른 응답을 받고 싶어하기 때문에 비동기로 처리할건 큐에 넣어버리고 **빨리 응답!**
- **비동조** (Decoupling) : 애플리케이션과 분리할 수 있음.
- **탄력성** (Resilience) : 작업의 일부가 실패해도 전체에 영향을 주지 않음.
- **과잉** (Redundancy) : 실패할 경우 **재실행** 가능.
- **보증** (Guarantees) : 작업이 처리된 것을 확인할 수 있음.
- **확장성** (Scalable) : 다수의 프로세스들이 큐에 메시지를 보낼 수 있음.



#### 3) 언제 사용할까?

- 다른 곳의 API로부터 데이터를 송수신해야 할 경우
- 다양한 애플리케이션에서 비동기 통신을 필요로 하는 경우
- 많은 양의 프로세스를 처리해야 하는 경우

###  

___

### 2. 프로토콜 정리

- 프로토콜은 서로 데이터를 주고 받을 때 **서로 지키기로 하는 규약**이다!

#### 1) MQTT

- **Message Queue Telemetry Transport**
- 주로 사물 인터넷에서 디바이스간 메시지를 주고 받아야 할 때 사용한다.
- 사물 인터넷에서는 **TCP/IP** 같은 안정된 프로토콜을 이용하지 못하고 주로 인터넷보다 단거리 통신을 이용한다.
  - Bluetooth, Zigbee 등등...
  - 따라서 안정되지 못하고 비교적 신뢰하기 힘들다.
  
- **MQTT를 쓰는 이유**
  - **저전력 (Quality of Service : QoS)**
    - 전력 소비를 줄이고, 
    - 신뢰할 수 없는 네트워크에서 데이터 통신을 성공시킨다!
  - **Pub/Sub 모델**
    - Client - Server의 구조가 아니라, **Publisher - Broker - Subscriber**의 Pub/Sub 구조이다.

#### 2) AMQP

- **Advanced Message Queing Protocol**
- 흔히 알고 있는 **MQ**의 오픈소스에 기반한 표준 프로토콜을 의미한다.

- **등장 배경**
  - 이전부터 상용화되어있던 **MQ** 제품은 많았지만, 대부분 **플랫폼 종속적**인 제품들이었다. 
    - 때문에 서로 다른 이기종간 메시지를 교환하기 위해서는... 
    - **메시지 포맷 컨버전**이나 (속도 저하) **시스템 자체를 통일**해줘야 했다.
  - 서로 다른 시스템간에 **비용/기술/시간**적인 측면에서 최대한 효율적으로 **메시지를 교환**하기 위해 등장했다!

- **라우팅 모델**
  - **AMQP**는 다음 세가지 구성요소들이 서로 어떻게 통신하는지에 대해 정의한 프로토콜!

  ![model](http://pds21.egloos.com/pds/201305/20/09/d0002609_51998d8e9e650.jpg)

  - **Exchange**
    - `Publisher`로부터 수신한 메시지를 적절한 `Queue` 또는 다른 `Exchange`로 **분배하는 라우터**
    - 각 `Queue`나 `Exchange`는 `Binding`을 사용해 `Exchange`에 **바인드 되어있다**
    - **Binding** vs **Exchange Type**
      - **Exchange Type**은 메시지를 어떤 방법으로 라우팅 시킬지 **결정**하는 것이고,
      - **Binding**은 이러한 방법을 이용해 **실제로** 어떤 메시지를 어떤 큐로 보낼지 정하는 **라우팅 테이블**임!

  - **Queue**
    - 일반적으로 알고있는 큐이다.
    - **메모리**나 **디스크**에 메시지를 저장하고, 이를 `Consumer`에게 전달한다.
    - 큐는 **스스로가** 관심있는 메시지 타입을 지정한 `Binding`을 통해 `Exchange`에 묶인다.

  - **Binding**
    - `Exchange`와 `Queue`와의 관계를 정의한 일종의 **라우팅 테이블**
    - 하나의 `Queue`가 여러개의 `Exchange`에 바인드될 수도 있고...
    - 하나의 `Exchange`가 여러 개의 `Queue`가 바인딩될 수도 있음!

  - **Exchange Type**
    - 메시지를 어떤 방법으로 라우팅할지 정하는 **알고리즘**이다.
    - **Direct** (1:1) : 바인딩 된 `Queue` 중, 메시지의 라우팅 키와 **매핑**되어 있는 `Queue`로 메시지 전달
    - **Topic** (1:N) : 메시지의 라우팅 키를 무시하고 `Exchange`에 바인딩 된 **모든** `Queue`에 메시지 전달
    - **Fanout** (Multicast) : `Exchange`에 바인딩 된 `Queue` 중 메시지의 라우팅 키가 **패턴에 맞는** 모든 `Queue`에게 메시지 전달
    - **Headers** : 라우팅 키 대신 **메시지 헤더**에 여러 속성을 더해, 속성이 매칭되는 큐에 메시지 전달
    - 등 다양한 방법들이 정의되어 있다.

    

___

### 3. 오픈소스 메시지 큐들

#### 1) RabbitMQ

- `AMQT` 프로토콜을 **구현해 놓은 프로그램**
- **유연한 라우팅** : `Message`가 `Queue`에 도착하기 전에 라우팅되며, 플러그인을 통해 **더 복잡한 라우팅**도 가능!
- **클러스터링** : 여러 **RabbitMQ** 서버를 **논리적으로 클러스터링**할 수 있다.
- 거의 모든 언어와 운영체제를 지원함!



#### 2) ActiveMQ

- **JMS (Java Message Service)** 클라이언트와 함께 **자바로 만든** 오픈소스 메시지 브로커!
- **엔터프라이즈 기능** 제공 : 하나 이상의 클라이언트와 서버간 커뮤니케이션을 증진시키는 기능...
- 자바 뿐 아니라 다른 **교차언어**를 사용하는 클라이언트를 지원한다.
- **Spring** 애플리케이션에 매우 쉽게 임베딩 될 수 있다고 함!
- **REST API**를 통한 웹 기반 메시징 API를 지원!
- 웹 브라우저가 메시징 도구가 될 수 있도록, **AJAX**를 통해 순수한 **DHTML**을 사용한 **웹스트리밍 지원**



#### 3) Kafka

- **대용량의 실시간 로그 처리에 특화**되어 설계된 메시징 시스템.
- 기존 범용 메시징 시스템 대비 **TPS**가 매우 우수하다!
  - 특화된 시스템이기 때문에 범용 시스템에서 제공하는 다양한 기능들은 (Ex.) 제공 X
  - **AMQP 프로토콜**이나 **JMS API**를 쓰지 않고 **단순한 메시지 헤더**를 지닌 **TCP 기반 프로토콜** 사용
    → **프로토콜에 의한 오버헤드 감소**
  - 메시징 전송 시 **헤더의 크기가 작아** 오버헤드를 감소시킴
  
- **분산 시스템**을 기본으로 설계해, 기존 메시지 시스템에 비해 **분산 및 복제 구성**을 손쉽게 할 수 있음!
- **파일 시스템**에 메시지를 저장하기 떄문에, **데이터의 영속성** (durability)이 보장됨.
  - 기존 메시징 시스템은 남은 메시지의 수가 많으면 성능 ↓
  - 카프카는 **파일 시스템에 저장**하기 때문에 메시지를 많이 쌓아두어도 **성능이 크게 감소하지 X**
  - 많은 메시지를 쌓아놓을 수 있어 **주기적인 batch**작업에 사용할 데이터를 쌓아두는 용도로도 사용 가능
  
- **처리된 메시지를 삭제하지 X** ... 파일 시스템에 그대로 두고, 특정 시간이 지나거나 용량이 차면 그 때 지운다.
  - 기존 메시징 시스템은 `Consumer`에 의해 처리된 메시지 (acknowledged message)를 바로 지운다.
  - 일정 기간동안 처리도니 메시지를 삭제하지 않아서...
    - 처리 도중 문제가 발생하거나 처리 로직이 변경되었을 경우,
    - `Consumer`가 메시지를 **처음부터 다시 처리 (rewind)** 하도록 할 수 있음!
    
- **push - pull** 구조!
  - 기존 **push 방식** 메시징 시스템에선 
    - `broker`가 직접 각 `Consumer`가 어떤 메시지를 처리해야 하는지 **계산**하고,
    - 어떤 메시지를 처리 중인지 **트랙킹**했다면... (이것도 사실 다 일이다)
    
  - Kafka의 **pull 방식**에서는 `Consumer`가 직접 `broker`로부터 메시지를 가지고 간다!
    - 따라서 `Consumer`는 자신의 **처리능력 만큼**의 메시지만 `broker`로부터 가져오기 때문에
    - **최적의 성능**을 낼 수 있게 된다!
    → `broker`의 `Consumer`와 메시지 관리로 인한 **부담이 경감**된다!
    → 메시지를 쌓아놨다가 주기적으로 처리하는 **batch Consumer**의 구현도 가능해진다!



___

### 4. MQ별 성능 비교

#### 1) MQ별 Producer 성능

![producer](https://t1.daumcdn.net/cfile/tistory/227E9935595117290F)

- **붉은색** 그래프 : 메시지를 한 번에 **50개씩 batch**로 전송한 결과
- **연두색** 그래프 : 메시지를 한 번에 **하나씩** 전송한 결과

→ batch로 몰아서 전송해버리면 성능이 훨씬 좋아진다!



#### 2) MQ별 Consumer 성능

![consumer](https://t1.daumcdn.net/cfile/tistory/221272425951176E16)

- `Consumer` 성능의 경우 Kafka가 월등히 좋아보인다!



___

**[정리]** *대용량 CEP 엔진에서는 Kafka를 사용하고, 이외의 작은 메시지큐는 다양한 기능을 제공하는 범용 메시지 큐를 쓰자*



___

#### Reference

- [[오픈소스] 메시지 큐 (Message Queue) 알아보기](https://12bme.tistory.com/176)
- [최신 메시지 큐 (Message Queue) MQ 기술](https://kji6252.github.io/2015/12/18/message-quere/)
- [MQTT 프로토콜이란?](https://dalkomit.tistory.com/82)
- [AMQP Introduction](http://egloos.zum.com/killins/v/3025514)

