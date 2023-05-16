<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>글쓰기</title>
</head>
<body>
    <form action="/hackthebox/community/write" method="post">
        <input type="text" name="title" id="">
        <input type="text" name="content" id="">
        <input type="submit" value="submit">
    </form>    
</body>
</html>