package Controller;

import model.UserDAO;
import model.UserDTO;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/loginprocess")
public class UserLoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId;
        String userPwd;
        JSONObject json;

        UserDTO newVo = (UserDTO) req.getAttribute("vo");

        if (newVo != null) {
            // 회원가입 요청 시 받는 파라미터
            userId = newVo.getUser_id();
            userPwd = newVo.getPassword();
        } else {
            BufferedReader br = req.getReader();
            StringBuilder sb = new StringBuilder(); // append 하기위해 StringBuilder 사용
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            // 로그인 요청 시 받는 파라미터
            json = new JSONObject(sb.toString());
            userId = json.getString("user_id");
            userPwd = json.getString("user_pw");
        }

        // TODO: username과 password를 검증하는 코드 작성
        UserDAO dao = new UserDAO();
        UserDTO vo = dao.UserSelect(userId, userPwd);

        if (vo.getUser_id() != null) {
            if (newVo != null) {
                // 회원가입 성공
                json = new JSONObject();
                json.put("signup", "success");

                // 쿠키 및 세션 생성
                HttpSession session = req.getSession();
                session.setAttribute("UserId", newVo.getUser_id());
                session.setAttribute("UserName", newVo.getName());
                String sessionId = session.getId(); // 세션 ID를 문자열로 변환
                String cookieName = vo.getUser_id(); // 회원 아이디를 쿠키 이름으로 지정
                Cookie Cookie = new Cookie(cookieName, sessionId);
                Cookie.setPath("/");
                Cookie.setMaxAge(3600);
                // json.put("sessionId", sessionId); // 세션 ID를 JSON 객체에 추가

                // response
                resp.addCookie(Cookie);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                // JSON
                PrintWriter out = resp.getWriter();
                out.print(json.toString());
                out.flush();
            } else {
                // 로그인 성공
                json = new JSONObject();
                json.put("login", "success");

                // 쿠키 및 세션 생성
                HttpSession session = req.getSession();
                session.setAttribute("UserId", vo.getUser_id());
                session.setAttribute("UserName", vo.getName());
                String sessionId = session.getId(); // 세션 ID를 문자열로 변환
                String cookieName = vo.getUser_id(); // 회원 아이디를 쿠키 이름으로 지정
                Cookie Cookie = new Cookie(cookieName, sessionId);
                Cookie.setPath("/");
                Cookie.setMaxAge(3600);
                // json.put("sessionId", sessionId); // 세션 ID를 JSON 객체에 추가

                // response
                resp.addCookie(Cookie);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                // JSON
                PrintWriter out = resp.getWriter();
                out.print(json.toString());
                out.flush();
            }
        } else {
            // 로그인 실패
            json = new JSONObject();
            json.put("login", "fail");

            // JSON response
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(json.toString());
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/hackthebox/login");
    }
}
