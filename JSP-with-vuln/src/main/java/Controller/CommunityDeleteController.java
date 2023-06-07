package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CommunityDAO;
import model.CommunityDTO;

@WebServlet("/community/delete")
public class CommunityDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("UserId") != null) {
            String userId = (String) session.getAttribute("UserId");
            String deleteId = req.getParameter("id");
            CommunityDTO dto = new CommunityDTO();
            dto.setUser_id(userId);
            CommunityDAO dao = new CommunityDAO();
            int result = dao.CommunityDelete(deleteId, dto);
            if (result > 0) {
                resp.sendRedirect("/hackthebox/community");
            } else {
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("text/html;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<script>alert('잘못된 요청입니다.');history.back(-1);</script>");
            }
        }
    }
}
