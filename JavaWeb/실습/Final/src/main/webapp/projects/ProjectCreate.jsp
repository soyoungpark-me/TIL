<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>프로젝트 등록</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>프로젝트 등록</h1>
<form action='create.do' method='post'>
제목: <input type='text' name='title'><br>
내용: <textarea name='contents'></textarea><br>
시작일: <input type='date' name='start'><br>
종료일: <input type='date' name='end'><br>
태그: <input type='text' name='tags'><br>
<input type='submit' value='추가'>
<input type='reset' value='취소'>
</form>
<jsp:include page="/Footer.jsp"/>
</body>
</html>
