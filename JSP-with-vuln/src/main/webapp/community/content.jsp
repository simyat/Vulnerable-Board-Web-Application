<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="model.CommunityDTO" %>
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
        <%= content.getSave_file() %>
    </div>
</body>
</html>
