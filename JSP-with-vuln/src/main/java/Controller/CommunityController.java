package Controller;

import model.CommunityDAO;
import model.CommunityDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

// 커뮤니티 컨트롤러
@WebServlet("/community")
public class CommunityController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("UserId") != null) {
            // 세션이 있다면 커뮤니티 페이지 접근 허가
            CommunityDAO dao = new CommunityDAO();
            ArrayList<CommunityDTO> CommunityList = dao.CommunityList();
            req.setAttribute("CommunityList", CommunityList);
            RequestDispatcher rd = req.getRequestDispatcher("community/community.jsp");
            rd.forward(req, resp);
        } else {
            resp.sendRedirect("/hackthebox/");
        }
    }
}
