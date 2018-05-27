<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:useBean id="project"
	scope="request"
	class="vo.Project"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>프로젝트 수정</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>프로젝트 수정</h1>
<form action='update.do' method='post'>
번호: <input type='text' name='no' value='<%=project.getId()%>' readonly><br>
제목: <input type='text' name='title' value="<%=project.getTitle()%>"><br>
내용: <textarea name='contents'><%=project.getContents()%></textarea><br>
시작일: <input type='date' name='start' value="<%=project.getStart()%>"><br>
종료일: <input type='date' name='end' value="<%=project.getEnd()%>"><br>
상태: <input type='number' name='state' value="<%=project.getState()%>"><br>
태그: <input type='text' name='tags' value="<%=project.getTags()%>"><br>
<input type='submit' value='추가'>
<input type='reset' value='취소'>
</form>
<jsp:include page="/Footer.jsp"/>
</body>
</html>
