<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="model.CommunityDTO" %> <%@ page import="java.util.ArrayList" %>
<script src="<%= request.getContextPath() %>/js/validate.js" charset="utf-8"></script>
<script src="<%= request.getContextPath() %>/js/write.js" charset="utf-8"></script>

<% if (session.getAttribute("UserId") == null) { %>
<script>
    validateAccess();
</script>
<% } else { 
        ArrayList<CommunityDTO> list = (ArrayList<CommunityDTO>) request.getAttribute("CommunityList"); 
%>
        <!DOCTYPE html>
        <html>
            <head>
                <title>Hack The Box Community</title>
            </head>
            <body>
                <h1>Hack The Box Community</h1>
                <div>
                    <h2>Hello! <%= session.getAttribute("UserName") %></h2>
                    <br />
                    <form action="/hackthebox/logout" method="POST">
                        <input type="submit" value="로그아웃" name="logout" />
                    </form>
                    <button onclick="location_function()">글쓰기</button>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th> </th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (CommunityDTO dto : list){
                        %>
                        <tr>
                            <td><%= dto.getId() %></td>
                            <td><a href="/hackthebox/community/posts?id=<%=dto.getId() %>"><%= dto.getTitle() %></a></td>
                            <td><%= dto.getName() %></td>
                            <td><%= dto.getPostdate() %></td>
                            <td><%= dto.getVisit_count() %></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </body>
        </html>
<% } %>
