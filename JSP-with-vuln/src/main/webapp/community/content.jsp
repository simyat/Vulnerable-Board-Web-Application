<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="model.CommunityDTO" %>
<script src="<%= request.getContextPath() %>/js/community-api.js" charset="utf-8"></script>

<%
    CommunityDTO content = (CommunityDTO) request.getAttribute("content");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= content.getTitle() %></title>
</head>
<body>
    <div>
        <%= content.getTitle() %>
    </div>
    <div>
        <%= content.getName() %>
    </div>
    <div>
        <%= content.getPostdate() %>
    </div> 
    <div>
        <%= content.getVisit_count() %>
    </div>
    <div>
        <%= content.getLike_count() %>
    </div>
    <div>
        <%= content.getContent() %>
    </div>
    <div>
        <a href="/hackthebox/community/download?file=<%= content.getOriginal_file() %>&id=<%= content.getId() %>">파일 다운로드</a>
    </div>
    <div>
        <% 
            String sessionName = (String) session.getAttribute("UserName");
            String dtoName = content.getName();

            if (sessionName.equals(dtoName)){
        %>
            <button onclick="modify('<%= content.getId() %>')">수정</button>
            <button onclick="delete_post('<%= content.getId() %>')">삭제</button>
            <button onclick="community()">목록</button>
        <% } else { %>
            <button onclick="community()">목록</button>
        <% } %>
    </div>
</body>
</html>
