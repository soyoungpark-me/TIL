# Lucene 기초 다지기

 **출처 :** [**실전비급 아파치 루씬 7: 엘라스틱서치 검색 엔진을 향한 첫걸음**](https://book.naver.com/bookdb/book_detail.nhn?bid=14134564)

#### 목차

1. [루씬의 이해](https://github.com/3457soso/TIL/blob/master/Lucene/01_Intro.md)
2. [텍스트 색인](https://github.com/3457soso/TIL/blob/master/Lucene/02_index.md)
3. [텍스트 분석](https://github.com/3457soso/TIL/blob/master/Lucene/03_analyze.md)
4. **텍스트 검색과 질의 방법**
   - [**전문 검색의 이해**](#1-전문-검색의-이해)
   - [**IndexReader와 IndexSearcher**](#2-indexreader와-indexsearcher)
   - [**Query와 QueryParser**](#3-query와-queryparser)
   - [**다양한 검색 Query**](#4-다양한-검색-query)
5. [루씬의 고급 검색](https://github.com/3457soso/TIL/blob/master/Lucene/05_core.md)
6. [루씬 동작 방식 이해하기](https://github.com/3457soso/TIL/blob/master/Lucene/06_inside.md)
7. [다양한 확장 기능](https://github.com/3457soso/TIL/blob/master/Lucene/07_extensions.md)



___

## 텍스트 검색과 질의 방법

### 1. 전문 검색의 이해 

#### 1) 관계형 데이터베이스의 검색

- 검색 엔진 뿐 아니라 관계형 데이터베이스에서도 검색을 지원한다.
- **LIKE 검색**
  - 긴 문장의 컬럼을 색인하기 어렵고, 색인하더라도 **성능에 문제** 가 있다.
  - 데이터가 많아질수록 검색은 느려진다.
  - 관계형 데이터베이스는 언어적인 변이를 검색할 수 없다 (형태소 분석, 동사 원형 찾기 등)

#### 2) 루씬의 전문 검색

- 자연어 텍스트 검색은, 본문 데이터를 구조화해야 하고... 형태소, 문자, 문장 등 고려할 것들이 많다.

- 역색인은 저장된 데이터를 하나하나 찾지 않아도, 텀이 포함된 도큐먼트를 빠르게 찾을 수 있다.

- **전문 검색 과정**

  1. 질의를 해석해 무엇을 묻는지 알아내기
     - 사용자 질의는 `Query`라는 객체로 만들어짐! 조건을 여기에 설정함
     - 쿼리로 한 질의는 **분석기에 의해 분석** (과정에서 토크나이저와 토큰 필터 사용)
  2. 가능한 답 찾기
     - 사용자 질의와 일치하거나 유사한 도큐먼트 검색
  3. 가능한 답에 `Score` 매기기
  4. `Score`가 가장 높은 답을 검색 결과로 반환하기

- **전문 검색의 핵심**

  - 사용자 질의와 본문의 **관련성 계산**

  - 여기서 관련성은 **도큐먼트의 필드에 텀이 나타난 횟수**

    → 일치한 텀의 횟수가 높을수록 관련성이 높다고 간주!

#### 3) 루씬 검색 클래스

- `IndexSearcher` : **특정 시점에 해당** 하는 색인에 접근해 검색
- `Query` : **사용자 질의** 를 위한 클래스. 다양한 질의 가능!
  - `QueryParser` : 텍스트 문자열을 **구문 분석** 해 `Query` 객체 생성
- `TopDocs` : **사용자 질의 결과 **를 여기에 담아 반환
  - `ScoreDocs` : `TopDocs`에 담긴 검색 결과 중 **상위 검색 결과 반환**

#### 4) 루씬 전문 검색의 특징

- 형태소 기반의 전문 검색 지원
- 관련성에 따라 결과의 유사도 점수 계산
- 빠른 색인과 광범위한 텍스트 검색 지원

<br>

___

### 2. IndexReader와 IndexSearcher

`IndexReader`는 데이터 구조에 접근하고, `IndexSearcher`는 검색을 수행한다

#### 1) IndexReader 클래스

- 특정 시점에 해당하는 색인에 접근 가능한 추상 클래스
- **주요 역할** : 특정 시점의 뷰 (`Point In Time View Of An Index` 제공) → **준 실시간 검색**
  - 따라서 `IndexWriter`에 의해 색인이 변경되는 시점에도 `IndexReader` 객체는 색인 검색 가능!
  - `IndexReader` 인스턴스는 스레드로부터 안전! → 여러 스레드가 동시에 **해당 메소드 호출**
- **검색 요청** : 색인은 세그먼트 (Segment)라는 물리적 공간에 저장됨
  - 사용자가 검색을 요청하면 `IndexReader`를 사용해 세그먼트에서 색인을 읽음!
- **색인 변경** : 색인이 변경될 때 `IndexReader` 객체를 이용해 변경 전의 데이터를 읽을 수 있음
  - `Commit` : 색인 작업을 담당하는 `IndexWriter`가 작업하고, 
    - 변경이 끝나면 메모리에 임시 보관된 색인을 **실제 저장소에 옮겨 저장**
  - 커밋이 완료되면 새로운 `IndexReader` 객체 생성!
    - 이후로는 새로운 `IndexReader`를 통해 변경사항이 모두 반영된 **새로운 색인으로 검색**
- **종류**
  - `LeafReader` : 색인에 액세스하는 데 필요한 인터페이스를 제공하는 추상 클래스
  - `CompositeReader` : `LeafReader`에 있는 `StoredField`만 읽을 수 있고, 색인을 직접 조회할 순 없음

#### 2) IndexSearcher 클래스

- `IndexReader`에 의해 만들어진 **특정 시점에 해당하는 색인에 접근 가능**
  - 하나의 `IndexReader`를 통해 검색 기능 구현
- 검색 결과로 `TopDocs` 반환!
- 색인에 변경이 없으면 → 검색 시마다 새로 생성하지 말고, 하나의 `IndexSearcher` 인스턴스를 공유해 사용!
  - 변경될 때에는 **반드시** `IndexSearcher`를 새로 생성해야 검색 가능

#### 3) TopDocs 클래스

- `IndexSearcher` 클래스의 `search()` 메소드로 반환된 결과
- 사용자 질의 (쿼리)와 일치하는 상위 n개의 도큐먼트를 담은 객체
  - 루씬은 모든 검색 결과를 반환하지 않고 가장 적합한 **상위 n개의 도큐먼트** 를 `ScoreDoc`에 담아 반환
- `TopDocs`에는 도큐먼트의 아이디(`DocId`)가 있을 뿐, 실제 도큐먼트 내용까지는 포함 X

<br>

___

### 3. Query와 QueryParser

#### 1) Query란

- 질의를 위한 추상 클래스로, 사용자의 질의에 따라 **검색 엔진에 의해 생성되는 일종의 요청 정보**
- 사용자 질의는 텍스트이지만, 검색을 위해선 검색 엔진에 구조에 맞는 문법으로 질의해야 함
- `Query` 객체에는 세밀한 조건을 넣어 생성할 수 있음! [(4번 참고)](#4-다양한-검색-query)

#### 2) QueryParser란

- 문자열 (Query String)을 `Query` 객체로 변환하는 **인터프리터 (Interpreter)**
- **구글 고급 검색** (사용자의 복잡한 질의를 구글 내부 검색 엔진의 구조에 맞게 변경)과 유사함

#### 3) QueryParser 문법

- 텍스트 문자열을 구문 분석해 `Query` 객체를 만든다.
- 내부적으로는 질의를 분리해 검색 엔진에 전송할 쿼리 구조 (쿼리 트리)를 만든다!
- **원리** : 분석기로 질의를 분석한 결과를 `Query` 객체로 만든 후, `IndexSearcher`로 전달!
  - 사용자 질의를 `IndexSearcher`가 이해하고 명렁을 실행하는 쿼리 명령으로 해석됨!

<br>

___

### 4. 다양한 검색 Query

#### 1) TermQuery

- 쿼리와 일치하는 **텀이 포함된** 도큐먼트를 찾는다!
- 가장 많이 쓰이면서 기본이 됨!

#### 2) BooleanQuery

- 여러 쿼리를 논리조건 (**AND, OR, NOT**)으로 결합한 결과와 일치하는 도큐먼트 찾기
- 쿼리를 조합해 `BooleanClause`를 사용한다
- 2개 이상의 `BooleanClause`가 필요하지만, 너무 많이 추가하면 검색 시 `TooManyClauses` 예외가 발생함!

#### 3) RangeQuery

- `TermRangeQuery` : 문자 범위를 지정함. 특정 시작 텀과 끝 텀 사이에 텀이 있는지 조회
- `PointRangeQuery` : 수의 범위로 조회 (`IntPoint`, `LongPoint`, `FloatPoint`, `DoublePoint` 중 하나여야)

#### 4) WildcardQuery

- 와일드카드 (*, ?)를 이용함!
- 여러 조건을 반복해야 해서 속도가 느려질 수 있음에 주의해야 함 (편리하지만 성능이 떨어진다)
- `PrefixQuery` : 접두어 (특정 문자열로 시작하는 텀) 검색! → `WildcardQuery`보다 빠름
- `RegexpQuery` : `WildcardQuery`의 성능을 보완함. **정규식 패턴과 일치** 하는 단어로 조회
  - 잘못된 표현식 작성 시 속도가 떨어질 수 있음에 주의

#### 5) PhraseQuery

- 2개 이상의 단어가 결합된 절이나 문장의 일부분인 구가 포함된 도큐먼트 찾기
- `Slop` : 두 팀이 인접한 최대 거리! 검색하려는 검색 구문과 텀 사이에 허용되는 기타 단어의 수

#### 6) FuzzyQuery

- 지정된 단어와 유사한 단어가 있는 도큐먼트 찾기!
- 텀의 철자 변형을 고려할 때 유용함!
- CPU 자원을 많이 쓰므로 최소 접두어 길이를 지정하는 것이 좋다