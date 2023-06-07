package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/community/upload")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, 
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 30 
    )
public class FileUpload extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(resp.getStatus());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        // String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileName = filePart.getSubmittedFileName();
        for (Part part : req.getParts()) {
            // part.write("F:\\uploads\\" + fileName);
            part.write("/var/lib/tomcat9/webapps/uploads"+fileName);
        }
    }
    
}
