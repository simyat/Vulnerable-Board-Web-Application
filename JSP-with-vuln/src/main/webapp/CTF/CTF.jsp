<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>CTF Page</title>
    </head>
    <body>
        <h1>Hello CTF!!</h1>
        <form action="/hackthebox/ctf" method="post">
            <input type="text" name="userId" placeholder="아이디를 입력하세요" />
            <input type="password" name="Password" placeholder="비밀번호를 입력하세요" />
            <input type="submit" value="submit" />
        </form>
    </body>
</html>
