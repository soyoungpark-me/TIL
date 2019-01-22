# TypeScript 기초 다지기

 **출처 : [타입스크립트 공식 홈페이지](https://www.typescriptlang.org/docs/home.html)**

#### 목차

1. 타입스크립트 Intro
2. 타입스크립트 in 5 minutes

___

## 타입스크립트 in 5 minutes

### 1. 타입스크립트 설치

- 노드 깔고, 
- `npm install -g typescript` 
- 끝!



___

### 2. 타입스크립트 써보기

#### 1) 기존 자바스크립트 코드

- 먼저 작성한 후에

  ```javascript
  function greeter(person) {
      return "Hello, " + person;
  }
  
  let user = "Soyoung Park";
  
  document.body.innerHTML = greeter(user);
  ```

- 실행한다~

  ```sh
  tsc greeter.ts
  ```

- 실행한 후에는 같은 경로에 **같은 이름의 js파일이 생긴 것을 알 수 있다**



#### 2) 타입 적용하기

- `greeter(person)` 함수의 인자에 타입을 붙여보자

  ```typescript
  function greeter(person: string) {
      return "Hello, " + person;
  }
  
  let user = [0, 1, 2];
  
  document.body.innerHTML = greeter(user);
  ```

  위와 같이 타입에 맞지 않은 값을 준 다음에 실행해보면, **에러가 발생한다**

  ```sh
  $ tsc 01_greeter.ts
  01_greeter.ts:7:35 - error TS2345: Argument of type 'number[]' is not assignable to parameter of type 'string'.
  
  7 document.body.innerHTML = greeter(user);
  ```

  `greeter(person)` 함수에 인자를 주지 않아도 에러가 발생한다.

  ```typescript
  01_greeter.ts:7:27 - error TS2554: Expected 1 arguments, but got 0.
  
  7 document.body.innerHTML = greeter();
                              ~~~~~~~~~
  
    01_greeter.ts:1:18
      1 function greeter(person: string) {
                         ~~~~~~~~~~~~~~
      An argument for 'person' was not provided.
  
  
  Found 1 error.
  ```



___

### 3. 클래스와 인터페이스

#### 1) Interfaces
- 타입스크립트에는 인터페이스가 존재한다
- 인터페이스에서 구현해야 하는 내용물만 가지고 있으면 `implements`를 쓰지 않아도 해당 인터페이스를 구현했다고 친다.

  ```typescript
  interface Person {
      firstName: string;
      lastName: string;
  }
  
  function greeter(person: Person) {
      return "Hello, " + person.firstName + " " + 
          person.lastName;
  }
  
  let user = { firstName: "Jane", lastName: "User" };
  
  document.body.innerHTML = greeter(user);
  ```

  `user`는 `Person`을 구현하지 않았지만, `firstName`과 `lastName`을 모두 가지고 있어 구현한 걸로 친다!

#### 2) Classes
- 타입스크립트에서는 **class-based한 OOP를 지원해준다.**
- 클래스의 생성자 필드에 **접근 제어자**가 붙을 수 있다.
- 클래스와 인터페이스는 서로 적절하게 혼용할 수 있다.

  ```typescript
  class Student {
      fullName: string;
      constructor(public firstName: string, 
                  public middleInitial: string, 
                  public lastName: string) {
          this.fullName = firstName + " " + middleInitial
              + " " + lastName;
      }
  }
  
  interface Person {
      firstName: string;
      lastName: string;
  }
  
  function greeter(person : Person) {
      return "Hello, " + person.firstName + " " + 
          person.lastName;
  }
  
  let user = new Student("Jane", "M.", "User");
  
  document.body.innerHTML = greeter(user);
  ```

  - 위의 예제에서도, `greeter()` 함수는 `Person` 클래스는 받지만,
  - `Student` 클래스가 `firstName`과 `lastName`을 모두 가지고 있기 때문에 ...
  - `Student` 클래스의 객체를 `greeter()` 함수의 인자로 주어도 잘 돌아간다.

  

- 자바스크립트 파일으로 변환된 결과는 다음과 같다.

  ```javascript
  var Student = /** @class */ (function () {
      function Student(firstName, middleInitial, lastName) {
          this.firstName = firstName;
          this.middleInitial = middleInitial;
          this.lastName = lastName;
          this.fullName = firstName + " " + middleInitial
              + " " + lastName;
      }
      return Student;
  }());
  
  function greeter(person) {
      return "Hello, " + person.firstName + " " + 
      	person.lastName;
  }
  
  var user = new Student("Jane", "M.", "User");
  
  document.body.innerHTML = greeter(user);
  ```

  - 자바스크립트에는 클래스의 개념이 없기 때문에, **그냥 함수 객체로 만들고,**
  - **생성자 또한 함수로 구현된다.**
