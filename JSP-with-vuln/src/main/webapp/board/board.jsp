<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<%= request.getContextPath() %>/js/validate.js"></script>
<%
   if (session.getAttribute("UserId") == null) {
%>
<script>
validateAccess();
</script>
<%
    } else {
%>
<!DOCTYPE html>
<html>
<head>
<title>Hack The Box</title>
</head>
<body>
    <h2>Hello!! <%= session.getAttribute("UserName") %></h2><br/>
    <form action="/hackthebox/logout" method="POST">
        <input type="submit" value="로그아웃" name="logout"/>
    </form>
</body>
</html>
<%
    }
%>