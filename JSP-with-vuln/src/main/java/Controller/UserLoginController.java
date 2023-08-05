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

        UserDTO singupDTO = (UserDTO) req.getAttribute("vo"); // 회원가입 DTO

        if (singupDTO != null) {
            // 회원가입 요청 시 받는 파라미터
            userId = singupDTO.getUser_id();
            userPwd = singupDTO.getPassword();
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

        UserDTO loginDTO = new UserDTO();
        loginDTO.setUser_id(userId);
        loginDTO.setPassword(userPwd);

        // TODO: username과 password를 검증하는 코드 작성
        UserDAO dao = new UserDAO();
        UserDTO dto = dao.UserSelect(loginDTO);

        if (dto.getUser_id() != null) {
            if (singupDTO != null) {
                // 회원가입 성공
                json = new JSONObject();
                json.put("signup", "success");

                // 세션 생성
                HttpSession session = req.getSession();
                session.setAttribute("UserId", dto.getUser_id());
                session.setAttribute("UserName", dto.getName());

                // 쿠키 생성
                // String sessionId = session.getId(); // 세션 ID를 문자열로 변환
                // String cookieName = vo.getUser_id(); // 회원 아이디를 쿠키 이름으로 지정
                // Cookie Cookie = new Cookie(cookieName, sessionId);
                // Cookie.setPath("/");
                // Cookie.setMaxAge(3600);
                // json.put("sessionId", sessionId); // 세션 ID를 JSON 객체에 추가

                // resp.addCookie(Cookie);
                // response
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

                // 세션 생성
                HttpSession session = req.getSession();
                session.setAttribute("UserId", dto.getUser_id());
                session.setAttribute("UserName", dto.getName());
                // String sessionId = session.getId(); // 세션 ID를 문자열로 변환
                // String cookieName = vo.getUser_id(); // 회원 아이디를 쿠키 이름으로 지정
                // Cookie Cookie = new Cookie(cookieName, sessionId);
                // Cookie.setPath("/");
                // Cookie.setMaxAge(3600);
                // json.put("sessionId", session); // 세션 ID를 JSON 객체에 추가
                // resp.addCookie(Cookie);

                // response
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
