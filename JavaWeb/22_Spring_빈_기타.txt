* 날짜 값 주입하기
  - java.util.Date 타입의 프로퍼티 값을 설정하는 방법.
  - 스프링에서는 빈의 프로퍼티에 값을 설정할 때 태그의 콘텐츠나 속성 값으로 넣어준다.
    빈 설정 파일은 XML이기 때문에 프로퍼티의 값은 문자열로 표현된다.
    문자열은 숫자로 변환하기 쉽기 때문에 숫자 프로퍼티는 별도의 추가 작업 없이 값을 설정하기 쉽다.
    # 하지만! 그 외 타입은 자동으로 변환되지 않고, 개발자가 프로퍼티 타입에 맞는 객체를 생성해줘야 한다.
  - 따라서 java.util.Date 타입의 프로퍼티 타입을 설정하려면 이 객체를 생성해줘야 함.

    1. 일단 xml에 SimpleDateFormat 객체를 추가해준다. (날짜 형식의 문자열을 java.util.Date 객체로 변환해 줌)
       <bean id="dateFormat" class="java.text.SimpleDateFormat">
         <constructor-arg value="yyyy-MM-dd"/>
       </bean>

    2. 주고자 하는 곳에 태그의 콘텐츠에 객체를 통해 생성하면 됨!
       <property name="created_at">
         <bean factory-bean="dateFormat" factory-method="parse">
           <constructor-arg value="2014-5-5"/>

       > 근데 이런 방법을 쓰면 날짜 프로퍼티 값을 설정할 때마다 팩토리 메소드 빈을 선언해야 한다!!!
         이를 해결하기 위해 [프로퍼티 에디터]를 도입했다.

  - 커스텀 프로퍼티 에디터 : 문자열을 특정 타입의 값으로 변환해 주는 객체.
    Ex) CustomDateEditor : 날짜 형식의 문자열을 java.util.Date 객체로 변환해주는 클래스.
        URLEditor : URL 형식의 문자열을 java.net.URL 객체로 변환해주는 클래스.

    > 사용하기 위해서는 에디터 등록기(CustomPropertyEditorRegistrar 객체)를 생성해 등록해줘야 한다.
      등록기 얘는 스프링에서 제공하는 규칙(인터페이스)에 따라 작성해야 한다.

---------------------------------------------------------------------------------------------------------

* 애노테이션으로 의존 객체 자동 주입하기

  <bean class="org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor"/> 추가해야

  @Autowired가 붙은 프로퍼티에 대해 의존 객체(프로퍼티 타입과 일치하는 빈)을 찾아 주입(프로퍼티에 할당) 해준다.
    빈 컨테이너에 등록된 모든 빈을 조사해서 @Autowired가 붙은 셋터 메소드가 있다면 호출한다.
    세터 메소드가 넘기는 매개변수 값은 빈 컨테이너에서 매개변수 타입으로 찾은 객체이다.

    # 해당하는 객체가 없다면 오류가 발생한다. @Autowired가 붙은 프로퍼티는 반드시 값을 설정해야 하기 때문!
      > @Autowired의 required를 false로 설정하면 없어도 오류가 발생하지 않는다.
        @Autowired(required=false)

    # 타입에 해당하는 객체가 여러 개 있어도 오류 발생! 뭘 갖다 써야 하는지 모르기 때문!
      > @Qualifier로 주입할 객체를 지정할 수 있다.
        이 애노테이션을 쓰려면 빈의 후 처리기가 필요하다 (bean post processor)
        1. context 네임스페이스를 먼저 선언한다
           http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context.xsd
        2. <context:annotation-config/>를 선언하면 됨.
        3. @Autowired 밑에 @Qualifier("허용해줄 것")을 추가해준다.

  - @Autowired + @Qualifier = @Resource
    @Resource 같은 경우는 required 속성이 따로 없고 무조건 필수 입력으로 처리된다.

---------------------------------------------------------------------------------------------------------

* 빈 자동으로 등록하기 (컴포넌트 스캔 + 애노테이션)
  1. 빈 생성 대상이 되는 클래스에 @Component 애노테이션으로 표시를 해준다.
  2. 스프링 IoC 컨테이너는 @Component가 붙은 클래스를 찾아서 자동으로 빈을 생성한다.
  3. <context:component-scan base-package="패키지 이름"> beans.xml에 추가!
  클래스의 역할에 따라 붙일 수 있는 애노테이션을 추가로 제공한다.
  애노테이션 중 어떤 걸 붙이더라도 상관은 없지만, 알아보기도 쉽고 특정 애노테이션이 붙은 애들만 따로 처리할 수도 있어서 좋다.
    @Component  : 빈 생성 대상이 되는 모든 대상에 붙일 수 있다.
    @Repository : DAO와 같은 퍼시스턴스 역할을 수행하는 클래스에 붙임.
    @Service    : 서비스 역할을 수행하는 클래스에 붙임.
    @Controller : MVC 구조에서 Controller 역할을 수행하는 클래스에 붙임.
