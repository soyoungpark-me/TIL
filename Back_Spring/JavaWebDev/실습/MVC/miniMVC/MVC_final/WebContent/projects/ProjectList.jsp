<%@page import="vo.Project"%>
<%@page import="java.util.ArrayList"%>
<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>프로젝트 목록</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>프로젝트목록</h1>
<p><a href='create.do'>신규 프로젝트</a></p>
<jsp:useBean id="projects"
  scope="request"
  class="java.util.ArrayList" 
  type="java.util.ArrayList<vo.Project>"/>
<%
for(Project project : projects) {
%>
<%=project.getId()%>,
<a href='update.do?no=<%=project.getId()%>'><%=project.getTitle()%></a>,
<%=project.getStart()%>,
<%=project.getEnd()%>,
<%=project.getState() %>
<a href='delete.do?no=<%=project.getId()%>'>[삭제]</a><br>
<%} %>
<jsp:include page="/Footer.jsp"/>
</body>
</html>