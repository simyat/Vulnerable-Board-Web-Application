package Controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CommunityDAO;
import model.CommunityDTO;
import util.FileDriver;

@WebServlet("/community/write")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 30)
public class CommunityWriteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        ArrayList<String> fileList = null;

        String userId = (String) session.getAttribute("UserId");
        String userName = (String) session.getAttribute("UserName");
        String title = req.getParameter(req.getPart("title").getName());
        String content = req.getParameter(req.getPart("content").getName());

        FileDriver fileDriver = new FileDriver();
        fileList = fileDriver.fileUpload(req, resp);

        CommunityDTO dto = new CommunityDTO();
        dto.setUser_id(userId);
        dto.setName(userName);
        dto.setTitle(title);
        dto.setOriginal_file(fileList.get(0)); // 기존 파일명 저장
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
            req.getRequestDispatcher("./write.jsp").forward(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("../api/login.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
