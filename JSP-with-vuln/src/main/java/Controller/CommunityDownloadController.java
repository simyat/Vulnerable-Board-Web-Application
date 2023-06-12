package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CommunityDAO;
import model.CommunityDTO;
import util.FileDriver;

@WebServlet("/community/download")
public class CommunityDownloadController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String originalFileName = req.getParameter("file");
        String postsId = req.getParameter("id");
        
        CommunityDAO dao = new CommunityDAO();
        CommunityDTO content = dao.CommunityContent(postsId);
        String saveFileName = content.getSave_file();
        FileDriver fileDriver = new FileDriver();
        fileDriver.fileDownload(req, resp, originalFileName, saveFileName);
    }

}
