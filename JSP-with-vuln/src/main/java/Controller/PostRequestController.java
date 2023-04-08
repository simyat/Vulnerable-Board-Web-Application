package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/post.jsp")
public class PostRequestController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String postParameter = req.getParameter("postParameter");

        resp.setContentType("text/html;charset=utf-8");

        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Result</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Post Method</h2>");
        out.println("<p>response data : " + postParameter + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
