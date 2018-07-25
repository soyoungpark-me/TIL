* XML 기반 빈 관리 컨테이너
  - 스프링에서는 자바 객체를 빈(Bean)이라고 부른다.
  - 그래서 객체를 관리하는 컨테이너를 빈 컨테이너라고 부른다.
  - 스프링 IoC 컨테이너는 빈을 생성할 때 기본으로 한 개만 생성한다.
    getBean()을 호출하면 계속 동일한 객체를 반환한다!
  - 스프링 IoC 컨테이너는 두 가지 방법으로 빈 정보를 다룬다.
    1. XML
    2. 애노테이션

  > 스프링은 IoC 컨테이너가 갖추어야 할 기능들을 ApplicationContext 인터페이스에 정의해 둔다.
    a. ClassPathXmlApplicationContext : 자바 클래스 경로에서 XML로 된 빈 설정 파일을 찾음.
    b. FileSysteXmlApplicationContext : 파일 시스템 경로에서 빈 설정 파일을 찾음.
    c. WebApplicationContext : 웹 애플리케이션을 위한 IoC 컨테이너로, web.xml 파일에 설정된 정보에 따라 XML 파일을 찾음.
       1) 인스턴스를 생성할 때 생성자에 빈 설정 파일의 경로를 넘긴다.
       2) beans.xml 파일을 로딩한다.
       3) 빈 설정 파일에 설정된 빈을 생성해 객체 풀에 보관한다.
       4) 빈 컨테이너가 준비되었으면 getBean()을 호출해 빈을 꺼낸다. 매개변수는 빈 설정 파일에 선언한 빈 아이디!
          # getBean()의 반환 타입은 Object이므로 제대로 사용하려면 실제 타입으로 형변환해야 한다.
          # getBeanDefinitionNames() : 빈 컨테이너에 보관된 빈의 이름들을 String 배열으로 반환.
          # getAliases(빈의 이름) : 해당 빈의 별명을 String 배열으로 반환.

  > XML 파일에 <bean id="아이디 이름" class="클래스 경로"> 추가.
    - class : 클래스 이름 지정. #반드시 패키지 이름을 포함한 클래스 이름(Fully Qualified Name)이어야 함.
    - id : 객체의 식별자. 빈 컨테이너에서 객체를 꺼낼 때 이 식별자 사용.
    - name : 클래스 이름을 지정하거나, "이름;별명;별명" 등 별명도 지정할 수 있다.
    - id도, name도 지정하지 않으면 [익명 빈]으로 선언할 수 있다.
      해당 클래스의 객체가 컨테이너에 보관될 때 "패키지 이름 + 클래스 이름 + #인덱스"가 이름으로 사용된다.
    + java.lang.Class 객체로도 빈을 호출할 수 있다.
      하지만 같은 타입의 객체가 여러 개 있을 경우 빈 컨테이너 입장에서 뭘 리턴할지 알 수 없으므로 예외가 발생한다.

---------------------------------------------------------------------------------------------------------

* 생성자와 프로퍼티 설정
  - 인스턴스에 대해 호출될 생성자 지정하기. 호출될 생성자는 매개변수 값에 의해 결정된다.
  - 호출될 생성자의 매개변수 순서대로 <contructor-arg>를 사용해 매개변수 값을 설정한다.
    > <constrtuctor-arg> 엘리먼트를 이용해 호출될 생성자를 지정할 수 있다.
    > 매개변수는 <constructor-arg>의 자식 엘리먼트로 추가한다.
      <constructor-arg><value type="float">91</value></constructor-arg>
      <constructor-arg type="java.lang.String" value="박소영"/>
    > 다른 생성자와 혼동되지 않는다면 매개변수의 type 속성을 생략할 수 있다. 자동으로 형변환된다.
    > index 속성을 이용해 매개변수의 속성도 지정할 수 있다.
      <constructor-arg value="100" index="3"/> //4번째 매개변수로 지정 (index는 0부터 시작)

  - 프로퍼티로 설정하기. 기본 생성자로 만든 다음에 세터 함수로 지정할 수 있다.
    <property name="name"><value>박소영</value></property>
    <property name="name" value="박소영"/>

  - <bean>의 속성을 이용해 생성자 및 프로퍼티 설정하기
    1. 프로퍼티 설정하기
    xmlns:p="http://www.springframework.org/schema/p" 추가해준다.
    <bean id="score1" class="exam.test04.Score"
      p:name="홍길동" p:kor="100" p:eng="95" p:math="90"/>

    2. 생성자 설정하기
    xmlns:c="http://www.springframework.org/schema/c" 추가해준다.
    <bean id="score2" class="exam.test04.Score"
      c:name="임꺽정" c:kor="80" c:eng="90" c:math="100"/>

  + 매개변수 이름 뒤에 -ref가 붙으면 빈의 레퍼런스 주소를 설정하겠다는 뜻!
    <property name="engine"><ref bean="engine1"/></property>
    <property name="engine" ref="engine1"/>
    p:engine-ref="engine1"
    c:engine-ref="engine1"

---------------------------------------------------------------------------------------------------------

* 개별 인스턴스로 주입하기
  1. 생성자 매개변수를 설정할 때 새로 빈을 생성해 할당하기.
     자식 태그로 <bean>을 설정하면 됨!
      <bean id="car1" class="exam.test06.Car">
        <constructor-arg value="Avante"/>
        <constructor-arg>
          <bean class="exam.test06.Engine" p:maker="Hyundai" p:cc="1495"/>
        </constructor-arg>
      </bean>

  2. 프로퍼티도 생성자 매개변수처럼 새로 빈을 생성해 할당할 수 있다.
      <bean id="car2" class="exam.test06.Car">
        <property name="model" value="Sonata"/>
        <property name="engine">
          <bean class="exam.test06.Engine" p:maker="Hyundai" p:cc="1997"/>
        </property>
      </bean>

---------------------------------------------------------------------------------------------------------
* 컬렉션 값 주입하기. (List, Set, Map, Properties ...)
  1. List 값 주입하기
      <property name="tires">
       <list>
         <bean class="exam.test07.Tire" p:maker="Kumho" p:spec="P185/65R14"/>
  2. Set 값 주입하기 : 기존에 등록된 객체와 값이 같은지 조사해 같지 않을 경우에만 Set에 추가
      <property name="프로퍼티 이름">
        <set>
          <value></value><ref bean="빈 레퍼런스"/><bean class="클래스명 (패키지명 포함)"/> ...

  3. Map 값 주입하기 : 식별자(key)와 값(value)을 한 값으로 묶어서 저장해야 한다.
      맵에 들어갈 식별자와 값은 <entry>로 정의 식별자는 <key>, 값은 <value>
      <property name="options">
        <map>
          <entry>
            <key><value>sunroof</value></key>   // key에 값을 바로 넣을 순 없고 <value>로 싸줌.
            <value>yes</value>
          </entry>
          <entry key="airbag" value="dual"/>    // 속성값으로 설정해도 됨.
          <entry key="sparetire">
            <ref bean="spareTire"/>             // 다른 객체의 레퍼런스 설정 가능.
          </entry>
        </map>
  4. Properties 값 주입하기 : <props> 태그 사용. Properties에 저장할 항목은 <prop> 태그로 정의.
      <property name="spec">
        <props>
          <prop key="width">205</prop>
