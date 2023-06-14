package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CommunityDAO;
import model.CommunityDTO;

@WebServlet("/community/modify")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 30)
public class CommunityModifyController extends CommunityWriteController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
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
