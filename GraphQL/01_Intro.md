# GraphQL 기초 다지기

 **출처 : [GraphQL 공식 레퍼런스](https://graphql.github.io/learn)**

#### 목차

1. **GraphQL 소개**
   - [**GraphQL 이란?**](#1-GraphQL-이란?)
2. 쿼리와 뮤테이션
3. 스키마와 타입

___

## GraphQL 소개

### 1. GraphQL 이란?

#### 1) 소개

- GraphQL은 API를 위한 쿼리 랭귀지이다.
- 쿼리를 실행하기 위해 서버 사이드에서 runtime으로 돈다.
- 쿼리에는 미리 지정한 타입 시스템이 쓰인다.



#### 2) 예제

- GraphQL 서비스는 특정 타입으로 필드를 정의하면서 시작된다.

- 그리고 각 타입의 각 필드별로 **함수를 제공한다** 

- Ex) 로그인 한 사용자가 누구인지, 그 사용자의 이름이 무엇인지 보여주는 GraphQL

  ```java
  type Query {
    me: User
  }
  
  type User {
    id: ID
    name: String
  }
  ```

  각 타입에는 함수가 동반된다.

  ```java
  function Query_me(request) {
    return request.auth.user;
  }
  
  function User_name(user) {
    return user.getName();
  }
  ```

- GraphQL 서비스가 실행된다면... 쿼리를 보내서 결과를 받을 수 있다.

- 쿼리는 validate되고 실행될 수 있다.

  - 먼저 이미 정의된 필드와 타입으로 요청했는지를 체크하고,
  - 주어진 함수들을 실행해 결과를 만들어 반환한다.

  ```javascript
  {
    me {
      name
    }
  }
  
  {
    "me": {
      "name": "Luke Skywalker"
    }
  }
  ```
