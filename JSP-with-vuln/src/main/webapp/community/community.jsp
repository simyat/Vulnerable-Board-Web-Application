<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ page import="model.CommunityDTO" %> <%@ page import="java.util.ArrayList" %>
<script src="<%= request.getContextPath() %>/js/validate.js" charset="utf-8"></script>
<script src="<%= request.getContextPath() %>/js/community-api.js" charset="utf-8"></script>

<% if (request.getAttribute("Not_Access") != null) { %>
<script>
    writeAcess();
</script>
<% } else if (session.getAttribute("UserId") == null) { %> 
    <!-- community url이 아닌 community.jsp로 직접 접근했을 때를 대비해 세션 검증 -->
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
                    <form action="/hackthebox/community/search" method="get">
                        <input type="date" name="date" value="" id="current_date">
                        <select name="filter" id="">
                            <option value="posts">게시글</option>
                            <option value="title">제목</option>
                            <option value="author">작성자</option>
                        </select>
                        <input type="search" name="search" value="" id="">
                        <button type="submit">검색</button>
                    </form>
                </div>
                <div>
                    <h2>Hello! <%= session.getAttribute("UserName") %></h2>
                    <br />
                    <form action="/hackthebox/logout" method="POST">
                        <input type="submit" value="로그아웃" name="logout" />
                    </form>
                    <button onclick="location_function()">글쓰기</button>
                </div>
                <div>
                    <a href="/hackthebox/community/search?order=recent">최신순</a>
                    <a href="/hackthebox/community/search?order=title">제목순</a>
                    <a href="/hackthebox/community/search?order=author">작성자순</a>
                    <a href="/hackthebox/community/search?order=recommend">좋아요순</a>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>조회</th>
                            <th>좋아요</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (CommunityDTO dto : list){
                        %>
                        <tr>
                            <td><a href="/hackthebox/community/posts?id=<%=dto.getId() %>"><%= dto.getTitle() %></a></td>
                            <td><%= dto.getName() %></td>
                            <td><%= dto.getPostdate() %></td>
                            <td><%= dto.getVisit_count() %></td>
                            <td><%= dto.getLike_count() %></td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </body>
        </html>
<% } %>
