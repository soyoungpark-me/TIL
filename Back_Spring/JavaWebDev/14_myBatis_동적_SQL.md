* 언제 필요할까?
  - 정렬을 최신순, 댓글순, 작성순 등등으로 여러 종류로 해야할 때, 기존 방법이라면 같은 SQL문을 여러 개 준비해야 한다.
  - 이런 귀찮음을 덜어주기 위해 동적 SQL 기능을 이용한다.

* 동적 SQL을 만들 때 사용할 수 있는 태그 리스트
  - <if test="조건">SQL문</if>

  - <choose> : 지정한 조건이 참이면 그 SQL 실행하고, 참인 게 없으면 otherwise 실행
      <when test="조건1">SQL문</when>
      <when test="조건2">SQL문</when>
      <otherwise>SQL문</otherwise>
    </choose>

  - <where> : where 안의 하위 태그를 실행하고 나서 반환 값이 있으면 where 절을 만들어 반환하고, 없으면 반환하지X
      <if test="조건1">SQL문</when>
      <if test="조건2">SQL문</when>
    </where>

  - <trim prefix="단어" prefixOverrides="문자열|문자열"> : 특정 단어로 시작하는 SQL 문을 반환하고 싶을 때.
      trim의 반환값이 있다면, 그 값의 앞 부분이 prefixOverrides에 지정된 문자열과 일치할 경우 그 문자열을 제거하고
      그 값의 앞부분에 prefix로 지정한 접두어를 붙여 반환함.
      <if test="조건1">SQL문</when>
      <if test="조건2">SQL문</when>
    </trim>

  - <set> : UPDATE 문의 SET 절을 만들 때 사용. 조건이 참은 if의 내용은 SET 절에 포함됨. 여러 개 포함 시 자동으로 콤마 붙음.
      <if test="조건1">SQL문</when>
      <if test="조건2">SQL문</when>
    </set>

  - <foreach> : 목록의 값을 가지고 SQL 문을 만들 때 사용. IN 조건을 만들 때 좋다. 속성들은 다음과 같다
    item : 항목을 가리킬 때 사용할 변수의 이름 지정.
    index : 항목의 인덱스 값을 꺼낼 때 사용할 변수 이름 지정.
    collection : java.util.List 구현체나 배열 객체가 온다.
    open : 최종 반환값의 접두어 지정.
    close : 최종 반환값의 접미어 지정.
    separator : 반복으로 생성하는 값을 구분하기 위해 붙이는 문자열 지정.
  - <bind name="변수명" value="값"> : 변수를 생성할 때 사용.
