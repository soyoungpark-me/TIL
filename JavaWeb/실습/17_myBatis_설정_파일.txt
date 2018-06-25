* mybatis 프레임워크는 자체 커넥션 풀을 구축할 수 있다.
* 여러 개의 데이터베이스 연결 정보를 설정해 두고 실행 상황(개발, 테스트, 배포)에 따라 다르게 쓸 수 있다.
* 실행 성능을 높이기 위해 SELECT 결과를 캐싱할 수도 있다.
* SQL 맵퍼 파일에서 사용할 값 객체(VO)에게 별명을 부여할 수도 있다.

-----> 이러한 mybatis 프레임워크의 동작 환경은 '설정 파일'에서 설정해준다.

* 루트 엘리먼트는 <configuration>. 주요한 자식 엘리먼트들은 아래와 같다.
  - properties : 프로퍼티 파일이 있는 경로 설정.
    > 데이터베이스 연결 정보처럼 자주 변경될 수 있는 값은 mybatis 설정 파일에 두지 않고 프로퍼티 파일에 저장한다.
    > 파일이 클래스 경로에 있다면 resource 속성을, 다른 데 있다면 url 속성을 사용한다.
    > 프로퍼티 파일에 저장된 값은 ${프로퍼티명} 형식으로 꺼내올 수 있다.
    > 자식 태그로 <property name="username" value="soyoung"/> 이런 식으로 프로퍼티를 추가할 수 있다.

  - settings : 프레임워크의 실행 환경 설정. 로그 설정도 여기서 함.
      <settings>
        <setting name="logImpl" value="LOG4J"/>
      </settings>
      
  - typeAliases : 자바 클래스 이름(패키지 이름 포함)에 대한 별명 설정. 별명들은 자식 태그로 나열한다.
    Ex) <typeAlias type="vo.User" alias="user"/>

  - typeHandlers : 칼럼의 값을 자바 객체로, 자바 객체를 칼럼의 값으로 변환해 주는 클래스 설정.

  - environments : 프레임워크에서 사용할 데이터베이스 정보(트랜잭션 관리자, 데이터 소스) 설정.
      <environments default="development">
        <environment id="development"> ... </environment>
        <environment id="deployment"> ... </environment> ...

    > environment 태그에는 트랜잭션 관리자와 데이터 소스를 설정하는데... 자식 태그로 추가하면 된다.
      1. 트랜잭션 관리 : JDBC(mybatis 자체에서 트랜잭션 관리), MANAGED(서버의 트랜잭션 관리 이용)
         Ex) <transactionManager type="JDBC"/>
      2. 데이터 소스 설정 : UNPOOLED(풀링X), POOLED(풀링 이용), JNDI(서버에서 제공하는 데이터 소스 사용)
         Ex) <dataSource type="POOLED">
         + 가능한 애플리케이션에서 데이터베이스 정보를 설정하지 않는 것이 좋다.
           톰캣 서버의 경우처럼 서버에서 데이터 소스를 제공했을 때 그걸 갖다 쓰면 유지보수 측면에서 유리하다.
           웹 애플리케이션이 데이터베이스 서버와 무관해지기 때문.

  - mappers : SQL 맵퍼 파일들이 있는 경로 설정.
    > 경로를 설정하는 파일은 두 가지가 있다.
      1. 자바의 클래스 경로 사용 : resource 속성으로 지정
      2. 운영체제의 파일 시스템 경로 사용 : url 속성으로 지정
