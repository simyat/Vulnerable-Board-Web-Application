package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CommunityDAO;

@WebServlet("/community/write")
public class CommunityWriteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        HttpSession session = req.getSession();
        String userId = (String) session.getAttribute("UserId");
        String userName = (String) session.getAttribute("UserName");
        System.out.println(userId);
        System.out.println(userName);
        CommunityDAO dao = new CommunityDAO();
        dao.CommunityWrite(userId, userName, title, content);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET");
        HttpSession session = req.getSession();

        if (session != null && session.getAttribute("UserId") != null) {
            req.getRequestDispatcher("./write.jsp").forward(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("api/login.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
