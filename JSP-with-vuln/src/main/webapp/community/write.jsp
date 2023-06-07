<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script src="<%= request.getContextPath() %>/js/validate.js" charset="utf-8"></script>
<script src="<%= request.getContextPath() %>/js/file.js" charset="utf-8"></script>

<%
    if (session.getAttribute("UserId") == null) { 
%> 
<script>
    validateAccess();
</script>
<% } else { %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>글쓰기</title>
</head>
<body>
    <form action="/hackthebox/community/write" method="post" enctype="multipart/form-data">
        <input type="text" name="title" id="" required>
        <textarea name="content" id="" required></textarea>
        <input type="file" name="file" id="attachment"><button type="button" onclick="uploadFiles()">파일 업로드</button>
        <input type="submit" value="submit">
    </form>
    <button onclick="history.back()">취소</button>
</body>
</html>
<% } %>