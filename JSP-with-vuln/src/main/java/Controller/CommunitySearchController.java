package Controller;

import java.io.IOException;
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
        String search = req.getParameter("search");
        String date = req.getParameter("date");
        String filter = req.getParameter("filter");
        String order = req.getParameter("order");

        SearchDTO dto = new SearchDTO();
        dto.setKeywords(search);
        dto.setCurrentSearchBy(filter);     
        dto.setCurrentSearchDate(date);
        dto.setCurrentSearchOrdeyBy(order);  

        SearchDAO dao = new SearchDAO();
        ArrayList<CommunityDTO> communitySearch = dao.CommunitySearch(dto);              
        req.setAttribute("CommunityList", communitySearch);
        RequestDispatcher rd = req.getRequestDispatcher("./community.jsp");
        rd.forward(req, resp);       
    }
}