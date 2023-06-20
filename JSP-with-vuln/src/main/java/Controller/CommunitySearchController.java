package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CommunityDAO;
import model.CommunityDTO;
import model.SearchDAO;
import model.SearchDTO;
import util.BoardPage;

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
        // filter, order 값 없을 경우 어떻게 처리해야할까?? 아래 코드로 작성하니 에러 발생.
        // else {
        // resp.setCharacterEncoding("UTF-8");
        // resp.setContentType("text/html;charset=UTF-8");
        // PrintWriter out = resp.getWriter();
        // out.println("<script>alert('잘못된 요청입니다.');history.back(-1);</script>");
        // }
    }

    private void daoSearch(HttpServletRequest req, HttpServletResponse resp, SearchDTO dto)
            throws ServletException, IOException {
        SearchDAO searchDAO = new SearchDAO();
        CommunityDAO dao = new CommunityDAO();
        Map<String, Object> map = new HashMap<String, Object>(); // View에 전달할 매개변수 저장용 맵 생성

        String query = searchDAO.CommunitySearchQuery(dto); // CommunityListPage에 전달할 검색 쿼리
        int listCount = searchDAO.CommunitySearchCount(dto); // 검색한 게시물 개수

        // 페이지 처리 시작
        // 전체 페이지 수 계산
        ServletContext application = getServletContext();
        int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
        int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));

        // 현재 페이지 확인
        int page = 1; // 기본값
        String pageTemp = req.getParameter("page");
        if (pageTemp != null && !pageTemp.equals("")) {
            page = Integer.parseInt(pageTemp); // 사용자에게 요청받은 페이지로 수정
        }

        // 목록에 출력할 게시물 범위 계산
        int start = (page - 1) * pageSize + 1; // 첫 게시물 번호
        int end = page * pageSize; // 마지막 게시물 번호
        map.put("start", start);
        map.put("end", end);
        // 페이지 처리 끝

        ArrayList<CommunityDTO> CommunityLists = dao.CommunityListPage(map, query); // 게시물 목록 받기

        // View에 전달할 매개변수 추가
        String paging = BoardPage.pagingStr(listCount, pageSize,
                blockPage, page, "community");
        // 페이징 영역 HTML 문자열
        map.put("paging", paging);
        map.put("listCount", listCount);
        map.put("pageSize", pageSize);
        map.put("page", page);

        req.setAttribute("CommunityLists", CommunityLists);
        req.setAttribute("map", map);
        RequestDispatcher rd = req.getRequestDispatcher("./community.jsp");
        rd.forward(req, resp);
    }
}