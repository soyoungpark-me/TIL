<%@page import="vo.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user"
  scope="session"
  class="vo.User"/>
<div style="background-color:#00008b;color:#ffffff;height:20px;padding: 5px;">
SPMS(Simple Project Management System)
<span style="float:rignt;">
<a style="color:white;" href="<%=request.getContextPath()%>/projects/list.do">프로젝트</a>
<a style="color:white;" href="<%=request.getContextPath()%>/users/list.do">회원</a>
<% if (user.getEmail() != null) { %>
<%=user.getName()%>
<a style="color:white;" 
  href="<%=request.getContextPath()%>/auth/logout.do">로그아웃</a>
</span>
<% }else{ %>
<span style="float:right;">
<a style="color:white;" 
  href="<%=request.getContextPath()%>/auth/login.do">로그인</a>
<% } %>
</span>
</div>