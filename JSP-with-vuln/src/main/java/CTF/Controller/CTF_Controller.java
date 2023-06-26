package CTF.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CTF.model.CTF_DAO;
import model.UserDTO;

// 웹해킹 과제 컨트롤러
@WebServlet(urlPatterns = { "/ctf", "/ctflogout" })
public class CTF_Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("Password");

        CTF_DAO dao = new CTF_DAO();
        // UserDTO vo = dao.IdentifyAuth(userId, password);
        UserDTO vo = dao.SeparateIdentifyAuth(userId, password);
        if (vo.getUser_id() != null) {
            HttpSession session = req.getSession();
            session.setAttribute("sessionId", vo.getUser_id());
            session.setAttribute("userName", vo.getName());
            req.getRequestDispatcher("CTF/CTF_flag.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("CTF/CTF.jsp").forward(req, resp);
        }

        HttpSession loguoutsession = req.getSession();
        String id = loguoutsession.getId();
        if (id != null) {
            loguoutsession.invalidate();
            req.getRequestDispatcher("CTF/CTF.jsp").forward(req, resp);
        }
    }
}
