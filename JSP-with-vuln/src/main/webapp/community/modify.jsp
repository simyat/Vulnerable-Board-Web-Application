<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="model.CommunityDTO" %>
<script src="<%= request.getContextPath() %>/js/validate.js" charset="utf-8"></script>

<%
    if (session.getAttribute("UserId") == null) { 
%> 
    <script>
    validateAccess();
    </script>
<% } else {
            CommunityDTO content = (CommunityDTO) request.getAttribute("content");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>글 수정</title>
</head>
<body>
    <form action="/hackthebox/community/modify" method="post" enctype="multipart/form-data">
        <input type="text" name="title" placeholder="<%= content.getTitle() %>"  id="" required>
        <textarea name="content" placeholder="<%= content.getContent() %>" id="" required></textarea>
        <input type="file" name="file" id="attachment"><button type="button" onclick="uploadFiles()">파일 업로드</button>
        <input type="submit" value="submit">
    </form>    
    <button onclick="history.back()">취소</button>
</body>
</html>
<% } %>