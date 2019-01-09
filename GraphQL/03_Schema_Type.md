# GraphQL 기초 다지기

 **출처 : [GraphQL 공식 레퍼런스](https://graphql.github.io/learn)**

#### 목차

1. GraphQL 소개
2. 쿼리와 뮤테이션
3. **스키마와 타입**
   - [**GraphQL의 타입 시스템**](#1-GraphQL의-타입-시스템 )
   - [**쿼리 타입과 뮤테이션 타입**](#2-쿼리-타입과-뮤테이션-타입)
   - [**다양한 타입들**](#3-다양한-타입들)
   - [**인터페이스와 유니온 타입**](#4-인터페이스와-유니온-타입)

___

## 스키마와 타입

### 1. GraphQL의 타입 시스템

#### 1) 타입 시스템

- GraphQL 쿼리의 언어는 **객체의 필드를 선택하는 것** 이다.

- 예제를 보면...

  ```json
  {
    hero {
      name
      appearsIn
    }
  }
  ```

  1. `root` 객체로 시작한다.
  2. `hero` 필드를 선택한다.
  3. `hero`에 의해 반환된 객체에 대해 `name` 과 `apperaIn` 필드를 선택한다.

- **스키마가 필요한 이유**

  - GraphQL 쿼리의 형태가 결과와 거의 일치한다!
    - **서버에 대해 모르는 상태에서 쿼리가 반환된 결과를 예측할 수 있다.**
  - 유효성 검사도 해준다!
    - 모든 GraphQL 서비스는 해당 서비스에서 쿼리 가능한 데이터들의 타입을 정의하고
    - **쿼리가 들어오면 해당 스키마에 대해 유효성을 검사해준다**



#### 2) 타입 언어

- GraphQL의 스키마는 **어떤 언어로든 생성할 수 있다**

- **GSL (GraphQL Schema Language)** : GraphQL 스키마 언어

  - 쿼리 언어와 비슷하다
  - GraphQL 스키마를 언어에 의존적이지 않게 만들어준다.


#### 3) 객체 타입과 필드

- GraphQL 스키마의 가장 기본적인 구성 요소는 **객체 타입** 이다.

- 객체 타입은 서비스에서 가져올 수 있는 **객체의 종류와 그 객체의 필드**이다.

  ```json
  type Character {
    name: String!
    appearsIn: [Episode]!
  }
  ```

  - `Character` : GraphQL 객체 타입.
  - `name`, `appearsIn` : `Character` 타입의 필드
  - `String` : 내장된 **스칼라 타입**. 스칼라 객체로 해석된다
  - `String!` : !가 붙으면 해당 필드는 non-nullable이란 뜻이다.
  - `[Episode]` : []는 객체의 **배열**ㅇㅡㄹ 나타낸다.

#### 4) 인자

- GraphQL 객체 타입의 **모든 필드는 0개 이상의 인수를 가질 수 있다.**

  ```json
  type Starship {
    id: ID!
    name: String!
    length(unit: LengthUnit = METER): Float
  }
  ```

- **모든 인수에는 이름이 있다**

- GraphQL의 모든 인자는 **특별한 이름으로 전달**된다.

  - 위 예제에서, `length` 필드는 하나의 인자 `unit` 을 가진다.

- 인자는 필수거나 옵셔널일 수 있다. 옵셔널일 경우 **기본값** 을 정의할 수 있다.



___

### 2. 쿼리 타입과 뮤테이션 타입

- 스키마 대부분의 타입은 **일반 객체 타입** 이지만, 스키마 내에는 특수한 두 타입이 있다.
- GraphQL 서비스는 **쿼리 타입을 꼭 가져야** 하지만, **뮤테이션 타입은 Optional** 하다.
- 이러한 타입은 일반 객체와 동일하지만, 모든 GraphQL 쿼리의 **진입점 (entry point)** 를 정의한다는 점이 다르다.

#### 1) 쿼리 타입

​	다음과 같은 쿼리를 작성하고 싶다고 가정하면,

- ```json
  query {
    hero {
      name
    }
    droid(id: "2000") {
      name
    }
  }
  ```

  스키마에는 `hero` 및 `droid` 필드가 있는 `Query` 타입이 있어야 한다.

  ```json
  type Query {
    hero(episode: Episode): Character
    droid(id: ID!): Droid
  }
  ```


#### 2) 뮤테이션 타입

- 뮤테이션 또한 위와 비슷하게 작동한다.
  - `Mutation` 타입의 필드를 정의하면 쿼리에서 호출할 수 있는 **루트 뮤테이션** 필드로 사용 가능하다.



___

### 3. 다양한 타입들

#### 1) 스칼라 타입

- GraphQL 객체 타입은 이름과 필드를 가지지만, 언젠가는 **구제적인 데이터로 해석** 되어야 한다.

- 스칼라 타입은 결국 **쿼리의 끝을 나타낸다** (하위 필드가 없다면 스칼라 타입!)

  ```json
  {
    hero {
      name
      appearsIn
    }
  }
  ```

  여기서는 `name` 과 `appearsIn` 이 스칼라 타입이 된다.

- GraphQL에서는 스칼라 타입들이 기본적으로 제공된다.

  - `Int` : 부호가 있는 32비트 정수.
  - `Float` : 부호가 있는 부동소수점 값.
  - `String` : UTF-8 문자열.
  - `Boolean` : `true` 또는 `false`.
  - `ID` : 객체를 다시 요청하거나, 캐시의 키로서 자주 사용되는 **고유 식별자**
    - `String` 과 같은 방법으로 직렬화된다.
    - 이는 사람이 읽을 수 있도록 하는 의도가 아니라는 것을 의미한다.

- **커스텀 스칼라 타입도 지정할 수 있다.**

  - 먼저 `Date` 타입을 지정해놓고,

  ```json
  scalar Date
  ```

  - 해당 타입을 **직렬화, 역직렬화, 유효성 검사**하는 방법을 구현해줄 수 있다.


#### 2) 열거형 타입 (Enums)

- **특정 값들로 제한되는 특별한 종류의 스칼라**이다.

- **활용**

  - 타입의 인자가 허용된 값 중 하나임을 **검증** 한다.
  - 필드가 항상 값의 열거형 집합 중 하나가 될 것임을 알려준다.

  ```java
  enum Episode {
    NEWHOPE
    EMPIRE
    JEDI
  }
  ```

  스키마에서 `Episode` 타입을 쓰면, 정확히 `NEWHOPE`, `EMPIRE`, `JEDI` 중 하나가 된다.

- 서버 구현 시, 열거형 타입을 처리할 수 있는 언어별 방법을 사용해 활용하면 된다.



#### 3) 리스트와 Non-Null

- 스키마의 다른 부분이나 쿼리 변수 선언에서 타입을 사용하면, **유효성 검사** 를 위해 **타입 수정자(type modifiers)** 를 적용할 수 있다.

  ```json
  type Character {
    name: String!
    appearsIn: [Episode]!
  }
  ```

  `String` 타입을 사용하고 타입 뒤에 느낌표 `!` 를 추가하여 *Non-Null* 로 표시!!

- 서버는 항상 이 필드가 `null` 이 아닌 값을 반환할 것을 기대한다!

  ```json
  query DroidById($id: ID!) {
    droid(id: $id) {
      name
    }
  }
  ```

  이런 식으로 **필드에 대한 인자**를 정의할 때도 사용할 수 있다.

- 만약 `null` 이 들어가면 GraphQL이 오류를 뱉는다.

  ```json
  {
    "errors": [
      {
        "message": "Variable \"$id\" of required type \"ID!\" was not provided.",
        "locations": [
          {
            "line": 1,
            "column": 17
          }
        ]
      }
    ]
  }
  ```

- 리스트에도 사용이 가능하다! 대신 `!` 의 위치에 따라 의미가 달라진다.

- `myField: [String!]` : 목록 자체는 `null` 일 수 **있지만**, 요소로 `null` 을 가질 수 **없다**

  ```json
  myField: null 
  myField: [] 
  myField: ['a', 'b'] 
  myField: ['a', null, 'b'] 	// error
  ```

- `myField: [String]!` : 목록 자체는 `null` 일 수 **없지만**, 요소로 `null` 을 가질 수 **있다**

  ```json
  myField: null 				// error
  myField: [] 
  myField: ['a', 'b'] 
  myField: ['a', null, 'b'] 
  ```


___

### 3. 인터페이스와 유니온 타입

#### 1) 인터페이스

- **타입이 포함해야 하는** 특정 필드들을 가지는 **추상 타입** 을 말한다!

  ```json
  interface Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
  }
  ```

  `Character` 를 구현한 (implements) 타입은 **이 인자와 리턴 타입을 가진 필드** 를 꼭 가져야 한다.

  ```json
  type Human implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    starships: [Starship]
    totalCredits: Int
  }
  
  type Droid implements Character {
    id: ID!
    name: String!
    friends: [Character]
    appearsIn: [Episode]!
    primaryFunction: String
  }
  ```

  두 필드는 **필수 필드**를 포함하면서 자신만의 **추가 필드** 를 가질 수 있다.

- **인라인 프래그먼트 활용**

  - 필수 필드가 아닌 경우, **어느 타입에** 해당 필드가 포함되어 있는지 알 수 없다.

  ```json
  query HeroForEpisode($ep: Episode!) {
    hero(episode: $ep) {
      name
      primaryFunction
    }
  }
  
   "errors": [
      {
        "message": "Cannot query field \"primaryFunction\" on type \"Character\". Did you mean to use an inline fragment on \"Droid\"?",
        "locations": [
          {
            "line": 4,
            "column": 5
          }
        ]
      }
    ]
  }
  ```

  `primaryFunction` 은 `Droid` 에만 구현되어 있기 때문에 에러가 발생한다.

  ```json
  query HeroForEpisode($ep: Episode!) {
    hero(episode: $ep) {
      name
      ... on Droid {
        primaryFunction
      }
    }
  }
  ```

  이렇게 **인라인 프래그먼트**로 싸줘야 한다.


#### 2) 유니온 타입

- **유니온 타입** 은 인터페이스와 매우 비슷하지만, **타입 간 공통 필드를 특정하지 않는다**

  ```json
  union SearchResult = Human | Droid | Starship
  ```

  이렇게 해놓으면, `SearchResult` 는 `Human`, `Droid`, `Starship` 셋 중 하나가 된다.

- 유니온 타입의 멤버는 **구체적인 객체 타입** 이어야 한다.

- 인터페이스나 유니온 타입에서 **다른 유니온 타입을 사용할 수는 없다**

  ```json
  {
    search(text: "an") {
      ... on Human {
        name
        height
      }
      ... on Droid {
        name
        primaryFunction
      }
      ... on Starship {
        name
        length
      }
    }
  }
  ```

  **[주의] : 어떤 필드든 쿼리 가능한 모든 인라인 프래그먼트를 붙여줘야 한다!**


#### 3) 입력 타입

- 스칼라 타입 외에도, **복잡한 객체도 인자로 쉽게 전달**할 수 있다

- 이는 뮤테이션에서 특히 유용함! (생성될 전체 객체를 전달할 수 있다)

- GraphQL 스키마 언어에서 입력 타입은 일반 객체 타입과 같지만, **type 대신 input을 사용한다**

  ```json
  input ReviewInput {
    stars: Int!
    commentary: String
  }
  ```

  뮤테이션에서 입력 객체를 쓰고 싶다면

  ```json
  mutation CreateReviewForEpisode($ep: Episode!, $review: ReviewInput!) {
    createReview(episode: $ep, review: $review) {
      stars
      commentary
    }
  }
  {
    "ep": "JEDI",
    "review": {
      "stars": 5,
      "commentary": "This is a great movie!"
    }
  }
  ```

- 입력 객체 타입의 입력란은 입력 객체 타입을 참조할 수 있지만,

- **입력 및 출력 타입을 스키마에 혼합할 수는 없고, 필드에 인자를 가질 수도 없다**