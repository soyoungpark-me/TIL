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
<p><a href='create.do'>신규 회원</a></p>
<jsp:useBean id="users"
  scope="request"
  class="java.util.ArrayList" 
  type="java.util.ArrayList<vo.User>"/>
<table border="1">
<tr>
  <th><c:choose>
    <c:when test="${orderCond == 'MNO_ASC'}">
      <a href="list.do?orderCond=ID_DESC">번호↑</a>
    </c:when>
    <c:when test="${orderCond == 'MNO_DESC'}">
      <a href="list.do?orderCond=ID_ASC">번호↓</a>
    </c:when>
    <c:otherwise>
      <a href="list.do?orderCond=ID_ASC">번호︎</a>
    </c:otherwise>
  </c:choose></th>
  <th><c:choose>
    <c:when test="${orderCond == 'NAME_ASC'}">
      <a href="list.do?orderCond=NAME_DESC">이름↑</a>
    </c:when>
    <c:when test="${orderCond == 'NAME_DESC'}">
      <a href="list.do?orderCond=NAME_ASC">이름↓</a>
    </c:when>
    <c:otherwise>
      <a href="list.do?orderCond=NAME_ASC">이름</a>
    </c:otherwise>
  </c:choose></th>  
  <th><c:choose>
    <c:when test="${orderCond == 'EMAIL_ASC'}">
      <a href="list.do?orderCond=EMAIL_DESC">이메일↑</a>
    </c:when>
    <c:when test="${orderCond == 'EMAIL_DESC'}">
      <a href="list.do?orderCond=EMAIL_ASC">이메일↓</a>
    </c:when>
    <c:otherwise>
      <a href="list.do?orderCond=EMAIL_ASC">이메일</a>
    </c:otherwise>
  </c:choose></th>
  <th><c:choose>
    <c:when test="${orderCond == 'CREDATE_ASC'}">
      <a href="list.do?orderCond=CREDATE_DESC">등록일↑</a>
    </c:when>
    <c:when test="${orderCond == 'CREDATE_DESC'}">
      <a href="list.do?orderCond=CREDATE_ASC">등록일↓</a>
    </c:when>
    <c:otherwise>
      <a href="list.do?orderCond=CREDATE_ASC">등록일</a>
    </c:otherwise>
  </c:choose></th>
  <th></th>
</tr>
<%
for(User user : users) {
%>
<tr>
<td><%=user.getId()%></td>
<td><a href='update.do?no=<%=user.getId()%>'><%=user.getName()%></a></td>
<td><%=user.getEmail()%></td>
<td><%=user.getCreatedAt()%></td>
<td><a href='delete.do?no=<%=user.getId()%>'>[삭제]</a><br></td>
<%} %>
<jsp:include page="/Footer.jsp"/>
</body>
</html>