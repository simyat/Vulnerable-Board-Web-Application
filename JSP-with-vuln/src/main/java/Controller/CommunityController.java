package Controller;

import model.CommunityDAO;
import model.CommunityDTO;
import util.BoardPage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 커뮤니티 컨트롤러
@WebServlet("/community")
public class CommunityController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("UserId") != null) {
            CommunityDAO dao = new CommunityDAO(); // 세션이 있다면 커뮤니티 페이지 접근 허가
            Map<String, Object> map = new HashMap<String, Object>(); // View에 전달할 매개변수 저장용 맵 생성

            int listCount = dao.CommunityListCount(); // 게시물 개수

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

            ArrayList<CommunityDTO> CommunityLists = dao.CommunityListPage(map); // 게시물 목록 받기

            // View에 전달할 매개변수 추가
            String paging = BoardPage.pagingStr(listCount, pageSize,
                    blockPage, page, "community?");
            // 페이징 영역 HTML 문자열
            map.put("paging", paging);
            map.put("listCount", listCount);
            map.put("pageSize", pageSize);
            map.put("page", page);

            req.setAttribute("CommunityLists", CommunityLists);
            req.setAttribute("map", map);
            RequestDispatcher rd = req.getRequestDispatcher("community/community.jsp");
            rd.forward(req, resp);
        } else {
            resp.sendRedirect("/hackthebox/");
        }
    }
}
