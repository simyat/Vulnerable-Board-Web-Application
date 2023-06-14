package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CommunityDTO;
import model.SearchDAO;
import model.SearchDTO;

@WebServlet("/community/search")
public class CommunitySearchController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SearchDTO dto = new SearchDTO();

        String filter = req.getParameter("filter");
        String order = req.getParameter("order");

        if (filter != null) {
            dto.setCurrentSearchBy(filter);
            dto.setKeywords(req.getParameter("search"));
            dto.setCurrentSearchDate(req.getParameter("date"));
            daoSearch(req, resp, dto);
        } else if (order != null) {
            dto.setCurrentSearchOrdeyBy(order);
            daoSearch(req, resp, dto);
        } 
        // else {
        //     resp.setCharacterEncoding("UTF-8");
        //     resp.setContentType("text/html;charset=UTF-8");
        //     PrintWriter out = resp.getWriter();
        //     out.println("<script>alert('잘못된 요청입니다.');history.back(-1);</script>");
        // }
    }

    private void daoSearch(HttpServletRequest req, HttpServletResponse resp, SearchDTO dto)
            throws ServletException, IOException {
        SearchDAO dao = new SearchDAO();
        ArrayList<CommunityDTO> communitySearch = dao.CommunitySearch(dto);
        req.setAttribute("CommunityList", communitySearch);
        RequestDispatcher rd = req.getRequestDispatcher("./community.jsp");
        rd.forward(req, resp);
    }
}