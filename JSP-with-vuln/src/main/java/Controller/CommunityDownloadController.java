package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FileDAO;
import model.FileDTO;
import util.FileDriver;

@WebServlet("/community/download")
public class CommunityDownloadController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String originalFileName = req.getParameter("file");
        
        FileDAO dao = new FileDAO();
        FileDTO dto = dao.getSaveFileNameFromDatabase(originalFileName);
        String saveFileName = dto.getDownloadsString();
        FileDriver fileDriver = new FileDriver();
        fileDriver.fileDownload(req, resp, originalFileName, saveFileName);
    }

}
