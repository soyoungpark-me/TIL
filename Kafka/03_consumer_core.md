# Kafka 기초 다지기

 **출처 : [카프카 핵심 가이드 (O'Reilly)](https://book.naver.com/bookdb/book_detail.nhn?bid=14093855)**

#### 목차

1. [카프카 훑어보기](https://github.com/3457soso/TIL/blob/master/Kafka/01_Introduction.md)
2. [카프카 프로듀서 : 카프카에 메시지 쓰기](https://github.com/3457soso/TIL/blob/master/Kafka/02_producer.md)
3. **카프카 컨슈머 : 중요 개념**
   - 컨슈머와 컨슈머 그룹
   - 컨슈머 그룹과 리밸런싱
   - 독자 실행 컨슈머
   - 커밋과 오프셋
4. [카프카 컨슈머 : 카프카에서 데이터 읽기](https://github.com/3457soso/TIL/blob/master/Kafka/04_consumer_use.md)
5. 스키마 레지스트리
6. 카프카 내부 메커니즘
7. 데이터 파이프라인 구축하기

- confluent 예제
- schema registry 예제



------

## **카프카 컨슈머 : 중요 개념**

### 1. 컨슈머와 컨슈머 그룹

#### 1) 컨슈머 그룹이란?