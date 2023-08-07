package Controller;

import java.io.IOException;
import java.io.PrintWriter;
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

        String userId = (String) session.getAttribute("UserId");
        String userName = (String) session.getAttribute("UserName");
        String title = req.getParameter(req.getPart("title").getName());
        String content = req.getParameter(req.getPart("content").getName());
        
        CommunityDTO dto = new CommunityDTO();
        dto.setUser_id(userId);
        dto.setName(userName);
        dto.setTitle(title);
        dto.setContent(content);

        FileDriver fileDriver = new FileDriver();
        ArrayList<String> fileList = fileDriver.fileUpload(req, resp);
        if (fileList.size() > 0) {
            dto.setOriginal_file(fileList.get(0));
            daoWrite(req, resp, dto); // 글 작성
        } else {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>alert('업로드 할 수 없는 확장자입니다.');history.back(-1);</script>");
        }
    }

    private void daoWrite(HttpServletRequest req, HttpServletResponse resp, CommunityDTO dto)
            throws IOException, ServletException {
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
