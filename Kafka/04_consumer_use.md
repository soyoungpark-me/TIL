# Kafka 기초 다지기

 **출처 : [카프카 핵심 가이드 (O'Reilly)](https://book.naver.com/bookdb/book_detail.nhn?bid=14093855)**

#### 목차

1. [카프카 훑어보기](https://github.com/3457soso/TIL/blob/master/Kafka/01_Introduction.md)
2. [카프카 프로듀서 : 카프카에 메시지 쓰기](https://github.com/3457soso/TIL/blob/master/Kafka/02_producer.md)
3. [카프카 프로듀서 : 중요 개념](https://github.com/3457soso/TIL/blob/master/Kafka/03_consumer_core.md)
4. **카프카 컨슈머 : 카프카에서 데이터 읽기**
   - 카프카 컨슈머 생성하기
   - 토픽 구독하기
   - 폴링 루프
   - 커밋하기
   - 밸런싱 리스너
   - 역직렬처리기
5. 스키마 레지스트리
6. 카프카 내부 메커니즘
7. 데이터 파이프라인 구축하기

- confluent 예제
- schema registry 예제



------

## **카프카 컨슈머 : 카프카에서 데이터 읽기**

### 1. 카프카 컨슈머 생성하기

