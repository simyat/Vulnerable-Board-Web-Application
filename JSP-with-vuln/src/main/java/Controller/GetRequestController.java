package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@WebServlet("/get.jsp")
public class GetRequestController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String getParameter = req.getParameter("getParameter");

        // 응답
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Result</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Get Method</h2>");
        out.println("<p>response data : " + getParameter + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}