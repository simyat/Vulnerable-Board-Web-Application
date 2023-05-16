package Controller;

import model.CommunityDAO;
import model.CommunityDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/community")
public class CommunityController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CommunityDAO dao = new CommunityDAO();
        ArrayList<CommunityDTO> CommunityList = dao.CommunityList();
        req.setAttribute("CommunityList", CommunityList);
        RequestDispatcher rd = req.getRequestDispatcher("community/community.jsp");
        rd.forward(req, resp);
    }
}
