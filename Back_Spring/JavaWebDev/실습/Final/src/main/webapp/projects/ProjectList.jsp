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
  <table border="1">
	<tr>
	  <th><c:choose>
   		 <c:when test="${orderCond == 'PNO_ASC'}">
    	   <a href="list.do?orderCond=PNO_DESC">번호↑</a>
    	 </c:when>
    	 <c:when test="${orderCond == 'PNO_DESC'}">
         <a href="list.do?orderCond=PNO_ASC">번호↓</a>
       </c:when>
       <c:otherwise>
         <a href="list.do?orderCond=PNO_ASC">번호︎</a>
       </c:otherwise>
       </c:choose>
     </th>
     <th><c:choose>
       <c:when test="${orderCond == 'TITLE_ASC'}">
         <a href="list.do?orderCond=TITLE_DESC">제목↑</a>
       </c:when>
       <c:when test="${orderCond == 'TITLE_DESC'}">
         <a href="list.do?orderCond=TITLE_ASC">제목↓</a>
       </c:when>
       <c:otherwise>
         <a href="list.do?orderCond=TITLE_ASC">제목︎</a>
       </c:otherwise>
     </c:choose></th>
     <th><c:choose>
       <c:when test="${orderCond == 'STARTDATE_ASC'}">
         <a href="list.do?orderCond=STARTDATE_DESC">시작일↑</a>
       </c:when>
       <c:when test="${orderCond == 'STARTDATE_DESC'}">
         <a href="list.do?orderCond=STARTDATE_ASC">시작일↓</a>
       </c:when>
       <c:otherwise>
         <a href="list.do?orderCond=STARTDATE_ASC">시작일</a>
       </c:otherwise>
     </c:choose></th>
     <th><c:choose>
       <c:when test="${orderCond == 'ENDDATE_ASC'}">
         <a href="list.do?orderCond=ENDDATE_DESC">종료일↑</a>
       </c:when>
       <c:when test="${orderCond == 'ENDDATE_DESC'}">
         <a href="list.do?orderCond=ENDDATE_ASC">종료일↓</a>
       </c:when>
       <c:otherwise>
         <a href="list.do?orderCond=ENDDATE_ASC">종료일</a>
       </c:otherwise>
     </c:choose></th>
     <th><c:choose>
       <c:when test="${orderCond == 'STATE_ASC'}">
         <a href="list.do?orderCond=STATE_DESC">상태↑</a>
       </c:when>
       <c:when test="${orderCond == 'STATE_DESC'}">
         <a href="list.do?orderCond=STATE_ASC">상태↓</a>
       </c:when>
       <c:otherwise>
         <a href="list.do?orderCond=STATE_ASC">상태</a>
       </c:otherwise>
     </c:choose></th>
     <th></th>
   </tr>
   <%
	for(Project project : projects) {
	%>
   <tr> 
     <td><%=project.getId()%></td>
     <td><a href='update.do?no=<%=project.getId()%>'><%=project.getTitle()%></a></td>
     <td><%=project.getStart()%></td>
     <td><%=project.getEnd()%></td>
     <td><%=project.getState()%></td>
     <td><a href='delete.do?no=<%=project.getId()%>'>[삭제]</a></td>
   </tr>
   <%} %>
 </table>
<jsp:include page="/Footer.jsp"/>
</body>
</html>