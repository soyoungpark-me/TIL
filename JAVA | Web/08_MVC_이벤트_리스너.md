* 리스너 : 특정 사건 (이벤트)가 발생했을 때 알림을 받는 객체.
  - 서블릿 컨테이너는 웹 애플리케이션의 상태를 감시하고, 특정 사건이 인지되면 리스너를 호출한다.
  - 리스너는 구현된 사건 별 호출 규칙에 따라 이벤트를 처리한다.

  Ex) ServletContextListener : 웹 애플리케이션이 시작될 때
      javax.servlet.http.HttpSessionListener : 세션이 생성되거나 소멸될 때
      javax.servlet.ServletRequestListener : 요청을 받고 응답할 때

  - 예를 들어, ServletContextListener의 경우!
    요청을 처리하기 위해 매번 DAO 객체를 만들면, 많은 가비지가 생기고 실행 시간이 길어진다.
    > 웹 애플리케이션이 시작될 때 'DAO 공유 객체를 하나 만들어 두자!'

    웹 애플리케이션이 시작되면 ServletContextListener 구현체에 대해 contextInitialized() 메소드가 호출됨.
    반대로 웹 애플리케이션이 끝나면 contextDestroyed() 메소드가 호출된다.

* 리스너 등록 방법
  1. 애노테이션 사용 : 클래스 선언 위에 @WebListen 붙이기
  2. DD 파일에 등록
     <listener>
       <listener-class>클래스 이름(Qname)</listener-class>
     </listener>
