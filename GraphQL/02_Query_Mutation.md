# GraphQL 기초 다지기

 **출처 : [GraphQL 공식 레퍼런스](https://graphql.github.io/learn)**

#### 목차

1. GraphQL 소개
2. 쿼리와 뮤테이션
   - [**필드와 인자**](#1-필드와-인자)
   - [**별칭과 프래그먼트**](#2-별칭과-프래그먼트)
   - [**작업 이름과 변수**](#3-작업-이름과-변수)
   - [**지시어**](#4-지시어)
   - [**뮤테이션**](#5-뮤테이션)
   - [**인라인 프래그먼트**](#6-인라인-프래그먼트)

___

## 쿼리와 뮤테이션

### 1. 필드와 인자

#### 1) Fields

- 간단하게, GraphQL은 오브젝트의 특정 필드를 요청한다.

  ```json
  {
    hero {
      name
    }
  }
  
  {
    "data": {
      "hero": {
        "name": "R2-D2"
      }
    }
  }
  ```

- 결과를 보면, 쿼리와 결과가 같은 모양을 하고 있다는 걸 알수 있다.

  - **이는 GraphQL에서 굉장히 중요한 개념!**
  - 사용자는 기대한 것을 받아갈 수 있고, 서버는 클라이언트가 정확히 무엇을 요청하는지 알 수 있다.
  - 상단의 쿼리는 **interactive**하다. 
    - 사용자는 받고 싶은 모양이 있으면 그에 따라 쿼리를 바꿀 수 있다.

- String 뿐 아니라 Object 또한 반환받을 수 있다. 

  - 이럴 때는 `sub-selection`을 사용한다.
  - GraphQL은 관련된 오브젝트의 그들의 필드를 순회하고, 
  - 클라이언트가 관련된 데이터들을 **한 번에 요청할 수 있게끔** 한다.
  - **이는 RESTful API에서 발생하는 N+1문제를 해결하는 방법이 된다.**

  ```json
  {
    hero {
      name
      friends {
        name
      }
    }
  }
  
  {
    "data": {
      "hero": {
        "name": "R2-D2",
        "friends": [
          {
            "name": "Luke Skywalker"
          },
          {
            "name": "Han Solo"
          },
          {
            "name": "Leia Organa"
          }
        ]
      }
    }
  }
  ```



#### 2) Arguments

- 필드에 인자를 전달하는 기능을 추가하면 다양하게 활용할 수 있다.

  ```json
  {
    human(id: "1000") {
      name
      height(unit: FOOT)
    }
  }
  
  {
    "data": {
      "human": {
        "name": "Luke Skywalker",
        "height": 5.6430448
      }
    }
  }
  
  
  ```

  - REST에서는 요청할 때, 쿼리 파라미터와 URL 세그먼트 같은 단일 인자만 전달 가능하다.

    - GraphQL에서는 모든 필드와 충접된 **객체가 인자를 가질 수 있다**
    - 필드에 인자를 전달해 서버에서 데이터 변환을 한 번만 구현하게 만들 수 있다.

  - 인자는 다양한 타입이 올 수 있다.

    - GraphQL의 기본 타입이 제공된다
    - GraphQL서버 내에서 직렬화를 할 수 있으면 커스텀할 수 있다.

___

### 2. 별칭과 프래그먼트

#### 1) 별칭 (Alias)

- 배경

  - 결과 객체 필드는 **쿼리의 필드 이름**과 일치하지만, 인자는 **쿼리의 필드 이름과 일치하지 않는다**
  - 다른 인자를 사용해서 같은 필드를 여러 번 쿼리할 수가 없다.
  - 인자도 같고 결과도 같으면 결국 같은 결과가 되기 때문에 문제가 된다...

- 서로 다른 이름의 별칭을 지정해 **한 요청에서 두 결과를 얻을 수 있다**

  ```json
  {
    empireHero: hero(episode: EMPIRE) {
      name
    }
    jediHero: hero(episode: JEDI) {
      name
    }
  }
  
  {
    "data": {
      "empireHero": {
        "name": "Luke Skywalker"
      },
      "jediHero": {
        "name": "R2-D2"
      }
    }
  }
  ```


#### 2) 프래그먼트

- 상대적으로 복잡한 페이지가 있다고 가정하면 쿼리가 복잡해질 수 있다.

- **프래그먼트**는 재사용 가능한 단위이다

  - 사용하면 **필드셋을 구성한 다음, 필요한 쿼리에 포함**시킬 수 있다.

  ```json
  {
    leftComparison: hero(episode: EMPIRE) {
      ...comparisonFields
    }
    rightComparison: hero(episode: JEDI) {
      ...comparisonFields
    }
  }
  
  fragment comparisonFields on Character {
    name
    appearsIn
    friends {
      name
    }
  }
  ```

  - 복잡한 데이터 요구사항을 **작은 단위로 분할** 하는데 사용한다.
  - 다른 여러 UI 구성 요소를 **하나의 초기 데이터 fetch**로 통합해야할 때 사용한다.

- **프래그먼트 안에서 변수 사용하기**

  - 쿼리나 뮤테이션에 선언된 변수는 프래그먼트에 접근할 수 있다.

  ```json
  query HeroComparison($first: Int = 3) {
    leftComparison: hero(episode: EMPIRE) {
      ...comparisonFields
    }
    rightComparison: hero(episode: JEDI) {
      ...comparisonFields
    }
  }
  
  fragment comparisonFields on Character {
    name
    friendsConnection(first: $first) {
      totalCount
      edges {
        node {
          name
        }
      }
    }
  }
  ```



___

### 3. 작업 이름과 변수

#### 1) 작업 이름

- `query` 키워드와 `query` 이름을 모두 생략 해도 실행이 가능하다.

- 하지만 코드를 덜 헷갈리게 하기 위해 **작업 이름** 을 정해주는 것이 좋다.

  ```json
  query HeroNameAndFriends {
    hero {
      name
      friends {
        name
      }
    }
  }
  ```

- 작업 타입은 **쿼리**, **뮤테이션**, **구독**이 될 수 있다.

- 작업 이름은 의미있고 명시적인 작업의 이름이다. (= 함수 이름과 비슷)



#### 2) 변수

- 지금까지는 모든 인자를 쿼리 문자열 안에 작성했지만, 필드에 대한 인자는 동적일 수 있다.
  - Ex) 드롭다운, 검색 필드, 필터 등등...
- GraphQL에서는 동적 값을 쿼리에서 없애고, 이를 **별도로 전달**할 수 있다.

- **방법**

  1. 쿼리 안의 정적 값을 `$variableName` 으로 변경한다.
  2. `$variableName` 을 쿼리에서 받는 변수로 선언한다.
  3. 별도의 전송규약 (JSON) 변수에 `variableName: value` 를 전달한다.

  ```json
  query HeroNameAndFriends($episode: Episode) {
    hero(episode: $episode) {
      name
      friends {
        name
      }
    }
  }
  
  {
    "episode": "JEDI"
  }
  ```

  - 변수는 여기서 `(episode: $episode)` 에서 정의된다.
  - 선언된 모든 변수는 **스칼라, 열거형, input Object type**이어야 한다.
  - 복잡한 객체를 필드에 전달하려면 서버에서 일치하는 입력 타입을 알아야 한다.
  - 타입 옆에 !가 없으면 **Optional**하다.

- 타입 선언 값 다음에 **기본값을 명시**해 줄 수 있다.

  ```json
  query HeroNameAndFriends($episode: Episode = "JEDI") {
    hero(episode: $episode) {
      name
      friends {
        name
      }
    }
  }
  ```

  - 모든 변수에 기본값이 제공되면 변수를 전달하지 않고도 쿼리를 호출할 수 있다.
  - 변수가 전달되면 **변수는 기본값을 덮어쓴다**



___

#### 4. 지시어

- 동적 쿼리를 구현하기 위해 **변수를 사용** 하는 병법도 있지만,

- 변수를 사용해 **쿼리의 구조와 형태**를 동적으로 변경하는 방법이 필요할 수도 있다.

  ```json
  query Hero($episode: Episode, $withFriends: Boolean!) {
    hero(episode: $episode) {
      name
      friends @include(if: $withFriends) {
        name
      }
    }
  }
  ```

  이 상태에서 변수를 아래와 같이 주면

  ```json
  {
    "episode": "JEDI",
    "withFriends": false
  }
  ```

  결과는 다음과 같다

  ```json
  {
    "data": {
      "hero": {
        "name": "R2-D2"
      }
    }
  }
  ```

  반대로 `withFriends` 를 `true` 로 주면, `friends` 필드도 출력하게 된다.

  ```json
  {
    "data": {
      "hero": {
        "name": "R2-D2",
        "friends": [
          {
            "name": "Luke Skywalker"
          },
          {
            "name": "Leia Organa"
          }
        ]
      }
    }
  }
  ```

- 지시어는 필드나 프래그먼트 안에 삽입될 수 있고, 쿼리 자체의 형식을 바꿔준다.
- 코어 GraphQL 사양에는 두 가지 지시어가 포함되어 있고, GraphQL서버에서 지원해줘야 한다.
  - `@include(if: Boolean)` : 인자가 `true` 일 때만 **이 필드를 결과에 포함**
  - `@skip(if: Boolean)` : 인자가 `true` 이면 **이 필드를 스킵함 (건너뜀)**

- 서버에서 정의해주면 **새 지시어를 추가해서 쓸 수 있다**



___

### 5. 뮤테이션

#### 1) 뮤테이션이란?

- 지금까지는 데이터를 가져오는 이야기였지만, 뮤테이션에서는 **서버 측 데이터를 수정한다**

- 뮤테이션 필드가 객체 타입을 반환하면 **중첩 필드를 요청할 수 있다**

  - 변경된 객체의 **새로운 상태** 를 가져오는 데 유용하다

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
  
  {
    "data": {
      "createReview": {
        "stars": 5,
        "commentary": "This is a great movie!"
      }
    }
  }
  ```

  - `createReview()` 필드는 새로 생성된 리뷰의 `stars` 와 `commentary` 필드를 반환한다.
  - **하나의 요청**으로 필드의 새 값을 변경하고 새 데이터를 받아올 수 있다.



#### 2) 뮤테이션의 다중 필드

- 뮤테이션은 쿼리와 마찬가지로 **여러 필드를 포함할 수 있다**

- **쿼리 필드는 병렬로 실행되지만 뮤테이션 필드는 하나씩 차례대로 실행된다**



____

### 6. 인라인 프래그먼트

#### 1) 인라인 프래그먼트

- GraphQL 스키마에는 **인터페이스** 와 **유니온** 타입을 정의하는 기능이 있다.

- 이를 반환하는 필드를 쿼리해야 한다면, **인라인 프래그먼트를 사용한다**

  ```json
  query HeroForEpisode($ep: Episode!) {
    hero(episode: $ep) {
      name
      ... on Droid {
        primaryFunction
      }
      ... on Human {
        height
      }
    }
  }
  {
    "ep": "JEDI"
  }
  
  {
    "data": {
      "hero": {
        "name": "R2-D2",
        "primaryFunction": "Astromech"
      }
    }
  }
  ```

  - `hero` 필드는 `Character` 를 반환한다.

  - `episode` 인자에 따라서 `Human` 일 수도 있고 `Droid` 일 수도 있다.

  - 필드를 직접 선택할 때는 `name` 과 같이 `Character` 인터페이스에 존재해야 한다.

  - 결과는, `hero`에서 반환된 `Character`가 

    - **Droid** 타입인 경우 `primaryFunction` 필드를 반환하고,
    - **Human** 타입인 경우 `height` 필드를 반환하게 된다.


#### 2) 메타 필드

- GraphQL 서비스에서 리턴될 타입을 모르는 상황이 발생하면...

- **클라이언트에서 해당 데이터를 어떻게 처리할지 결정할 방법이 필요하다!**

  - 쿼리의 어느 지점에서나 **메타 필드인 __typename** 을 요청해서...
  - **그 시점에서의 객체 타입의 이름을 얻을 수 있다**

  ```json
  {
    search(text: "an") {
      __typename
      ... on Human {
        name
      }
      ... on Droid {
        name
      }
      ... on Starship {
        name
      }
    }
  }
  
  {
    "data": {
      "search": [
        {
          "__typename": "Human",
          "name": "Han Solo"
        },
        {
          "__typename": "Human",
          "name": "Leia Organa"
        },
        {
          "__typename": "Starship",
          "name": "TIE Advanced x1"
        }
      ]
  }
  ```

  - `__typename` 을 붙여서 결과 값이 무엇인지에 따라 다르게 보여줄 수 있다.


