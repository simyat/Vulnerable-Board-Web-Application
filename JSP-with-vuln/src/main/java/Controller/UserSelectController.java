package Controller;

import model.UserDAO;
import model.UserVO;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/loginprocess")
public class UserSelectController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId;
        String userPwd;
        JSONObject json;

        UserVO newVo = (UserVO) req.getAttribute("vo");



        if (newVo != null) {
//            회원가입 요청 시 받는 파라미터
            userId = newVo.getUser_id();
            userPwd = newVo.getPassword();
        } else {
            BufferedReader br = req.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

//            로그인 요청 시 받는 파라미터
            json = new JSONObject(sb.toString());
            userId = json.getString("user_id");
            userPwd = json.getString("user_pw");
        }

        // TODO: username과 password를 검증하는 코드 작성
        UserDAO dao = new UserDAO();
        UserVO vo = dao.UserSelect(userId, userPwd); // 식별 & 인증 동시
        // 식별 & 인증 분리

        if (vo.getUser_id() != null) {
            if (newVo != null) {
                // 회원가입 성공
                json = new JSONObject();
                json.put("signup", "success");

                // 세션 생성
                HttpSession session = req.getSession();
                session.setAttribute("UserId", newVo.getUser_id());
                session.setAttribute("UserName", newVo.getName());
                String sessionId = session.getId(); // 세션 ID를 문자열로 변환
                json.put("sessionId", sessionId); // 세션 ID를 JSON 객체에 추가

                // JSON response
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(json.toString());
                out.flush();
            } else {
                // 로그인 성공
                json = new JSONObject();
                json.put("login", "success");

                // 세션 생성
                HttpSession session = req.getSession();
                session.setAttribute("UserId", vo.getUser_id());
                session.setAttribute("UserName", vo.getName());
                String sessionId = session.getId(); // 세션 ID를 문자열로 변환
                json.put("sessionId", sessionId); // 세션 ID를 JSON 객체에 추가

                // JSON response
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(json.toString());
                out.flush();
            }
        } else {
            // 로그인 실패
            json = new JSONObject();
            json.put("login", "fail");

            // JSON response
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(json.toString());
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/hackthebox/login");
    }
}
