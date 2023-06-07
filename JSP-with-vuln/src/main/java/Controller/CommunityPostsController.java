package Controller;

import model.CommunityDAO;
import model.CommunityDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 게시글 상세정보 컨트롤러
@WebServlet("/community/posts")
public class CommunityPostsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postsId = req.getParameter("id");
        CommunityDAO dao = new CommunityDAO();
        CommunityDTO content = dao.CommunityContent(postsId);
        req.setAttribute("content", content);
        req.getRequestDispatcher("./content.jsp").forward(req, resp);
    }
}
