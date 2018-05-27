* 팩토리 메소드 패턴, 빌더 패턴 : 공장 역할을 하는 객체를 통해 필요한 인스턴스를 간접적으로 얻는 방식.
  - 팩토리 클래스 : 인스턴스 생성 과정을 캡슐화함.
  - 팩토리 클래스를 만드는 방법.
    1. 스태틱으로 선언해 클래스 메소드로 만들기
       - 일반 클래스의 메소드를 공장 기능을 하도록 하려면 static으로 선언해야 함! (스프링 IoC 컨테이너 규정임)
       - xml에서 클래스 생성하기
         class 속성에 팩토리 클래스 이름을 지정함.
         중요한 것은 factory-method 속성! 인스턴스를 생성할 때 필요한 메소드의 이름이 들어간다.
         매개변수 값은 <constructor-arg> 태그로 넘겨야 함.

         # 메소드는 반드시 static으로 선언되어 있어야 한다!!!
           <bean id="kumhoTire" class="exam.test09.TireFactory" factory-method="createTire">
             <constructor-arg value="Kumho"/>
           </bean>

          # 주의할 점은, 만들어진 객체는 팩토리 인스턴스가 아니라 함수가 리턴한 객체라는 것!

    2. 인스턴스 메소드로 만들기
       - 먼저 팩토리 인스턴스를 만들어준다.
         <bean id="tireFactory" class="exam.test10.TireFactory"/>

       - 그리고 나서 팩토리 인스턴스를 이용해서 찍어낸다.
         <bean id="hankookTire" factory-bean="tireFactory" factory-method="createTire">
           <constructor-arg value="Hankook"/>
         </bean>

* 스프링 규칙에 따라서 팩토리 빈 만들기
  - factoryFactoryBean (인터페이스)를 직접 구현해도 되지만, 보통 스프링에서 제공하는 추상 클래스를 이용한다.
  - org.springframework.beans.factory.config.AbstractFactoryBean 이거!
    > createInstance() : 실제로 빈을 생성하는 메소드. 이거 꼭 구현해야 함!
      public이 아니라 protected인 이유 : 직접 사용하는 메소드가 아니라 getObject()에서 내부적으로 호출되기 때문.
    > getObjectType() : 팩토리 메소드(getObject())가 생성하는 객체의 타입을 알려줌. 이것도 꼭 구현!
    > 얘는 다양한 타입의 빈을 다룰 수 있도록 제너릭 문법이 적용되어 있다. 팩토리 빈이 생성할 인스턴스의 타입을 지정해야 함!

  - 스프링 IoC 컨테이너는 class 속성에 주어진 클래스가 일반 클래스가 아니라 FactoryBean 타입이면,
    이 클래스의 인스턴스를 직접 보관하는 게 아니라 # 이 클래스가 생성한 빈을 컨테이너에 보관한다. #

---------------------------------------------------------------------------------------------------------

* 빈의 범위 설정하기 : 스프링 IoC 컨테이너는 빈을 생성할 때 하나만 생성하고, getBean()을 호출하면 동일한 객체를 반환한다.
  - 설정을 통해서 이런 빈의 생성 방식을 조정할 수 있다!
    > 싱글톤(singleton) : 오직 하나의 빈만 생성한다. (기본 설정)
    > 프로토타입(prototype) : getBean()을 호출할 때마다 빈을 생성한다.
    > 요청(request) : HTTP 요청이 발생할 때마다 생성되며, 웹 애플리케이션에서만 이 범위를 설정할 수 있다.
    > 세션(session) : HTTP 세션이 생성될 때마다 생성되며, 웹 애플리케이션에서만 이 범위를 설정할 수 있다.
    > 전역 세션(globalsession) : 전역 세션이 준비될 때마다 빈이 생성된다. 웹 애플리케이션에서만 이 범위를 설정할 수 있다.
