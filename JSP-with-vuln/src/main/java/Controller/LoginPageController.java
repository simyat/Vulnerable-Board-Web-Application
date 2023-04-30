package Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 로그인 세션 검증 컨트롤러
@WebServlet("/login")
public class LoginPageController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session != null && session.getAttribute("UserId") != null) {
      req.getRequestDispatcher("board/board.jsp").forward(req, resp);
    } else {
      RequestDispatcher dispatcher = req.getRequestDispatcher("api/login.jsp");
      dispatcher.forward(req, resp);
    }
  }
}
