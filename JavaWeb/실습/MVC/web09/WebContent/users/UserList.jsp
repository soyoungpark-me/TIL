<%@page import="vo.User"%>
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
<title>회원 목록</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>회원목록</h1>
<p><a href='create'>신규 회원</a></p>
<jsp:useBean id="users"
  scope="request"
  class="java.util.ArrayList" 
  type="java.util.ArrayList<vo.User>"/>
<%
for(User user : users) {
%>
<%=user.getId()%>,
<a href='update?no=<%=user.getId()%>'><%=user.getName()%></a>,
<%=user.getEmail()%>,
<%=user.getCreatedAt()%>
<a href='delete?no=<%=user.getId()%>'>[삭제]</a><br>
<%} %>
<jsp:include page="/Footer.jsp"/>
</body>
</html>