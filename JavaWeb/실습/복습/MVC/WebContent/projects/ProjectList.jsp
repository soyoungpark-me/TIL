<%@ page import="spms.vo.Project" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>프로젝트 목록</title>
</head>
<body>
	<jsp:include page="/Header.jsp"/>
	<h1>프로젝트 목록</h1>
	<p><a href="create.do">신규 프로젝트</a></p>
	
	<jsp:useBean id="projects"
		scope="request"
		class="java.util.ArrayList"
		type="java.util.ArrayList<spms.vo.Project>"/>
	<table border="1">
	<tr>
	  <th>번호</th>
	  <th>제목</th>
	  <th>시작일</th>
	  <th>종료일</th>
	  <th>상태</th>
	  <th></th>
	</tr>		
	<%
	for (Project project : projects) {
	%>
	<tr> 
	  <td>${project.id}</td>
	  <td><a href='update.do?no=${project.id}'>${project.title}</a></td>
	  <td>${project.start}</td>
	  <td>${project.end}</td>
	  <td>${project.state}</td>
	  <td><a href='delete.do?no=${project.id}'>[삭제]</a></td>
	</tr>
	<% 
	} 
	%>
	<jsp:include page="/Footer.jsp"/>
</body>
</html>