* 리프레시 : 작업 결과를 출력한 후, 일정 시간이 지난 후 자동으로 서버에 요청을 보냄! 응답 헤더를 이용하면 된다.
  1. response.addHeader("Refresh", "1;url=list");
  2. out.println("<meta http-equiv='Refresh' content='1; url=list'>");
     1초 후에 url이 list로 자동으로 이동한다.

  +) 작업 결과를 출력하지 않고 다른 페이지로 이동할 때는 [리다이렉트]를 이용한다.
     response.sendRedirect("list");

* 필터 : 서블릿 실행 전후에 어떤 작업을 하고자 할 때 사용하는 기술.
  - 클라이언트가 보낸 암호를 해제한다.
  - 서블릿이 실행되기 전에 필요한 자원을 미리 준비한다.
  - 서블릿이 실행될 때마다 로그를 남긴다.

  Ex) POST 요청의 경우 문자 집합을 설정해주기 위해 request.setCharacterEncoding()을 해둔다.
      근데 이걸 서블릿마다 작성해놓는 건 매우 귀찮다.
      -> 서블릿 필터를 사용하자!

  - 웹 서블릿과 마찬가지로 한 번 생성되면 웹 애플리케이션이 실행되는 동안 계속 유지된다.
  - 서블릿 컨테이너는 웹 애플리케이션을 시작할 때 DD에 등록 된 필터의 인스턴스를 생성한다.

  # init()     : 필터 객체가 생성되고 난 후 준비 작업을 위해 딱 한 번 호출되는 메소드.
  # doFilter() : 필터와 연결된 URL에 요청이 들어오면 언제나 호출된다. 필터가 실제로 할 일이 들어간다.
    - nextFilter는 다음 필터를 가리킨다.
    - nextFilter.doFilter()는 다음 필터를 호출한다. 다음 필터가 없다면 서블릿의 service() 메소드를 호출한다.
      서블릿이 실행되기 [전]에 해야 할 일은 doFilter [호출 전]에 쓰고,
      서블릿이 실행된 [후]에 해야 할 일은 doFilter [호출 후]에 쓰면 된다.
  # destroy()  : 웹 애플리케이션을 종료하기 전에 필터들에 대해 destroy()를 호출해 마무리 작업을 한다.

  - 필터의 배치 방법 또한 서블릿과 유사함!
    1. DD 파일에 추가하는 방법
        <!-- 필터 선언 -->
        <filter>
         <filter-name>CharacterEncodingFilter</filter-name>                      // 필터의 별명 설정
         <filter-class>filters.CharacterEncodingFilter</filter-class>      // 필터의 클래스 이름 설정
         <init-param>                                                            // 필터가 사용할 정적 데이터 정의
           <param-name>encoding</param-name>
           <param-value>UTF-8</param-value>
         </init-param>
        </filter>

        <!-- 필터 URL 매핑 -->
        <filter-mapping>
         <filter-name>CharacterEncodingFilter</filter-name>
         <url-pattern>/*</url-pattern>
        </filter-mapping>

    2. 애노테이션에 추가하는 방법
        @WebFilter(
          urlPattens="/*",
          initParams={
            @WebInitParam(name="encoding", value="UTF-8")
        })

        이렇게 필터 클래스 상단에 추가해주면 된다.
