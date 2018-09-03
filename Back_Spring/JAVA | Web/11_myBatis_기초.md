* myBatis란?
  - 퍼시스턴트 프레임워크이자 SQL 맵퍼의 일종.
  - 개발과 유지보수가 편하도록 SQL을 소스 코드에서 별도의 파일로 분리하도록 한다.
  - 단순하고 반복적인 JDBC 코드를 캡슐화해, 데이터베이스 프로그래밍을 간결하게 만들어준다.

  > 핵심 컴포넌트들
    - SqlSession : 실제로 SQL을 실행하는 객체로, JDBC 드라이버를 사용한다.
    - SqlSessionFactory : SqlSession을 생성한다.
    - SqlSessionFactoryBuilder : mysql 설정 파일의 내용을 토대로 SqlSessionFactory를 생성한다.
    - mybatis 설정 파일 : DB 연결 정보, 트랜잭션 정보, mybatis 제어 정보 등의 설정 내용을 포함함.
    - SQL 맵퍼 파일 : SQL 문을 담고 있는 파일으로, SqlSession이 이걸 참조한다.

  > SqlSession의 주요 메소드는?
    - selectList() : SELECT 문을 실행하며, 값 객체 목록을 반환한다.
        List<E> selectList(String sqlId, [Object parameter])
    - selectOne()  : SELECT 문을 실행하며, 하나의 값 객체를 반환한다.
    - insert() : INSERT 문을 실행한다. 반환값은 입력한 데이터의 개수임.
    - update() : UPDATE 문을 실행한다. 반환값은 변경한 데이터의 개수임.
    - delete() : DELETE 문을 실행한다. 반환값은 삭제한 데이터의 개수임.

    +) sqlId : SQL 아이디로, SQL 맵퍼의 네임스페이스 이름과 SQL 문의 아이디 결합. 필요하다면 파라미터도 준다.
       Ex) "dao.UserDao.selectList.selectList"

  > 객체의 프로퍼티란? 겟터/셋터를 가리키는 용어로, 겟터/셋터 메소드의 이름에서 추출한다.
    void setTitle(), String getTitle() -> 프로퍼티 이름은 title
  > Wrapper (랩퍼) 객체에는 겟터/셋터 메서드가 없기 때문에 아무 이름이나 사용해도 된다.

  > commit()과 rollback() 메소드 알아보기
    - DBMS는 INSERT, UPDATE, DELETE 문을 실행하면 그 작업 결과를 임시 데이터베이스에 보관함.
      클라이언트의 요청이 있어야 해당 작업물을 실제 운영 데이터베이스에 반영함.
    - commit() : 임시 데이터베이스에 보관된 작업 결과를 운영 데이터베이스에 적용하라고 요청할 때 사용.
      SELECT 문은 값을 변경하는 게 아니니까 commit 해 줄 필요가 없다.
      +) 자동 커밋 시키기 : SqlSession sqlSession = sqlSessionFactory.openSession(true);
         자동 커밋을 시키면 편하지만 [트랜잭션을 다룰 수 없다]
    - rollback() : 임시 데이터베이스의 작업 결과를 운영 데이터베이스에 적용하지 않고 취소.
