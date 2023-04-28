package Controller;

import model.UserVO;
import model.UserDAO;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/signupprocess")
public class UserInsertController extends HttpServlet {
    // 현재 form에서 바로 POST 보내지만 fetch() API로 변경 예정
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId;
        String name;
        String userPwd;
        String address;
        JSONObject json;

        // JSON 파싱
        BufferedReader br = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        json = new JSONObject(sb.toString());
        userId = json.getString("user_id");
        name = json.getString("name");
        userPwd = json.getString("user_pw");
        address = json.getString("address");

        // 입력 값 없을 경우 에러 발생
        try {
            if (!userId.isEmpty() && !name.isEmpty() && !userPwd.isEmpty() && !address.isEmpty()) {
                UserVO uservo = new UserVO();

                uservo.setUser_id(userId);
                uservo.setName(name);
                uservo.setPassword(userPwd);
                uservo.setAddress(address);

                UserDAO userdao = new UserDAO();
                int result = userdao.UserInsert(uservo);
                if (result == 1) {
                    // 회원가입 성공 -> 로그인 처리 컨트롤러에서 세션 생성 후 board.jsp 리다이렉트
                    req.setAttribute("vo", uservo);
                    req.getRequestDispatcher("loginprocess").forward(req, resp);
                } else if (result == 4) {
                    // 아이디 중복
                    json = new JSONObject();
                    json.put("signup", "duplication");
                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    out.print(json.toString());
                    out.flush();
                } else {
                    throw new ServletException("Insert Error");
                }
            }
        } catch (NullPointerException e) {
            resp.sendRedirect("api/signup.jsp");
            e.printStackTrace();
        }
    }
}