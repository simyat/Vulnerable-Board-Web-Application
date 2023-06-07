package Controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CommunityDAO;
import model.CommunityDTO;

@WebServlet("/community/modify")
public class CommunityModifyController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        HttpSession session = req.getSession();
        String userId = (String) session.getAttribute("UserId");
        String userName = (String) session.getAttribute("UserName");

        CommunityDTO dto = new CommunityDTO();
        dto.setUser_id(userId);
        dto.setName(userName);
        dto.setTitle(title);
        dto.setContent(content);
        CommunityDAO dao = new CommunityDAO();
        int id = dao.CommunityWrite(dto);
        if (id > 0) {
            resp.sendRedirect("/hackthebox/community/posts?id=" + id);
        } else {
            try {
                // 권한없는 사용자가 작성 시 예외 발생
                throw new SQLIntegrityConstraintViolationException();
            } catch (SQLIntegrityConstraintViolationException e) {
                req.setAttribute("Not_Access", "parent key not found");
                req.getRequestDispatcher("./community.jsp").forward(req, resp);
                e.printStackTrace();
            } // 작성 실패 시 어떻게 처리할지 고민중..
        }
    }

    // 작성 권한 없는 사용자가 접근 시 로그인 페이지로 이동한다.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("UserId") != null) {
            // 작성한 게시물 가져오기
            String modifyId = req.getParameter("id");
            CommunityDAO dao = new CommunityDAO();
            CommunityDTO modifyContent = dao.CommunityContent(modifyId);
            req.setAttribute("content", modifyContent);
            req.getRequestDispatcher("./modify.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("../api/login.jsp").forward(req, resp);

        }

    }
}
