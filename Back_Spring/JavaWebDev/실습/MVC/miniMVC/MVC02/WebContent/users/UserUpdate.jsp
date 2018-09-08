<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user"
	scope="request"
	class="vo.User"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보</title>
</head>
<body>
<h1>회원정보</h1>
<form action='update.do' method='post'>
번호: <input type='text' name='no' value='<%=user.getId()%>' readonly><br>
이름: <input type='text' name='name' value='<%=user.getName()%>'><br>
이메일: <input type='text' name='email' value='<%=user.getEmail()%>'><br>
가입일: <%=user.getCreatedAt()%><br>
<input type='submit' value='저장'>
<input type='button' value='삭제' 
	onclick='location.href="delete?no=<%=user.getId()%>";'>
<input type='button' value='취소' onclick='location.href="list.do"'>
</form>
</body>
</html>