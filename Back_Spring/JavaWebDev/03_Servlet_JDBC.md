* JAVA 웹 프로그래밍에서 데이터베이스 사용하기!
  - JDBC를 통해 데이터베이스와 통신한다.
  - SQL 데이터베이스가 할 일을 작성하고, 그걸 JDBC에게 보내서 처리하고 결과를 받아옴!

  1. DriverManager를 이용해서 java.sql.Driver 인터페이스 구현체 등록!
     MySQL의 경우 com.mysql.jdbc.Driver 클래스이다.
  2. DriverManager의 getConnection()을 호출해 MySQL 서버에 연결한다.
     - 인자 : MySQL 서버의 접속 정보 (JDBC URL), DB 사용자 아이디, DB 사용자 비밀번호.
     - JDBC URL의 형태 : jdbc:mysql://localhost:3306/mydb
     - 연결에 성공하면 DB 접속 정보를 다루는 객체를 반환함!
  3. Connection 구현체를 이용해 SQL문을 실행할 객체를 준비한다.
     stmt = conn.createStatement();
     -> createStatement() 메소드는 java.sql.Statement 구현체를 반환하는데, 이 객체를 통해 DB에 쿼리문을 보낼 수 있다.
  4. 가져온 결과물을 java.sql.ResultSet 인터페이스에 담는다.
     - ResultSet에서 쓸 수 있는 여러 메소드들
       > first()     : 첫 번째 레코드 가져오기
       > last()      : 마지막 레코드 가져오기
       > previous()  : 이전 레코드 가져오기
       > next()      : 다음 레코드 가져오기. 레코드를 받으면 true, 없으면 false 반환!
       > getXXX()    : 특정 칼럼의 값 꺼내기. XXX에는 칼럼의 타입이 들어감 (integer, string, date...)
       > updateXXX() : 레코드에서 특정 칼럼의 값 변경
       > deleteRow() : 현재 레코드 지움!
  5. 정상적으로 수행되든 오류가 나든 자원을 해제해줘야 한다. 그래서 finally 블록에 넣는다.
     # 자원을 해제할 때는 역순으로! #

     }finally{
       try{if (rs != null) rs.close();}     catch(Exception e){}
       try{if (stmt != null) stmt.close();} catch(Exception e){}
       try{if (conn != null) conn.close();} catch(Exception e){}
     }


* HttpServlet 클래스 : GenericServlet 클래스의 하위 클래스이다.
  - 따라서 service() 함수를 구현해줘야 돌아간다.
  - 하지만 여기선 클라이언트의 요청 방식에 따라 doGet(), doPost(), doPut(), doDelete() 등의 메소드를 호출해 오버라이딩한다.
  > HttpServlet 클래스의 service() 메소드는 클라이언트로부터 들어온 요청에 따라
    GET이면 doGet을 호출하고, POST이면 doPost를 호출한다. 이에 맞춰서 구현해줘야 한다.

  - PreparedStatement : SQL 문을 미리 준비해 컴파일해 둔다. 실행 속도가 빠르다.
    SQL문과 입력 매개변수가 분리되어 있어 코드 작성이 편리하다.
    반복적인 질의를 하거나, 입력에 매개변수가 많을 경우 좋다.
    특히 이미지와 같은 바이너리 데이터를 저장하거나 변경할 땐 이것만 쓸 수 있다.
  - 입력 매개 변수가 있을 경우 setXXX() 메소드를 호출해 설정한다. (XXX에는 integer, string 등 자료형이 들어감)
    세팅이 끝나면 executeUpdate()를 호출해 SQL문을 실행한다.
  + select와 같이 결과 레코드가 필요할 경우 executeQuery, insert같이 결과 레코드를 만들지 않으면 executeUpdate()

* DB 에서 데이터를 꺼낼 때 데이터가 깨지는 경우
  - 서블릿에서 getParameter()를 호출하면 이 메소드는 매개변수의 값이 ISO-8859-1로 인코딩되어 있다고 가정하고
    각 바이트를 유니코드로 바꾸고 나서 반환한다.
  ->  문자가 깨진다!!!
  ->  getParameter()를 호출하기 전에 클라이언트가 보낸 매개변수의 값이 어떤 인코딩으로 되어 있는지 지정해야 한다.
  Ex) request.setCharacterEncoding("UTF-8");

  # 여기서 말한 대로 했는데도 깨진다면....
    1. 데이터베이스와 테이블의 collation과 charset을 체크한다. => show variables like '%c'
    2. 그래도 안되면 db에 접근하는 URL에 설정을 추가한다.
       jdbc:mysql://localhost/mydb?useUnicode=true&characterEncoding=utf8
    3. 아니면 톰캣의 server.xml에 <Connectior connectionTimeout... URIEncoding="UTF-8"을 추가한다.
