package Controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class UserLogoutController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

//        회원인증정보 속성 삭제
//        session.removeAttribute("UserId");
//        session.removeAttribute("UserName");

        // 모든 속성 한꺼번에 삭제
//        서버가 세션 정보를 더 이상 유지할 필요가 없어 부담이 적어지므로 invalidate()가 더 좋다.
        session.invalidate();
        
        // 속성 삭제 후 페이지 이동
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
