package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class FileDriver {
    // "/var/lib/tomcat9/webapps/uploads/" -> linux dir
    private static final String UPLOAD_DIR = "uploads";

    public ArrayList<String> fileUpload(HttpServletRequest req)
            throws IOException, ServletException {
        String applicationPath = req.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIR;
        File fileSaveDir = new File(uploadFilePath);

        // 파일 경로 없으면 생성
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        Part part = req.getPart("file");
        String fileName = part.getSubmittedFileName();

        part.write(uploadFilePath + File.separator + fileName);
        String now = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = now + ext;

        // 파일명 변경
        File oldFile = new File(uploadFilePath + File.separator + fileName);
        File newFile = new File(uploadFilePath + File.separator + newFileName);
        oldFile.renameTo(newFile);

        ArrayList<String> fileList = new ArrayList<String>();
        fileList.add(fileName);
        fileList.add(newFileName);

        return fileList;
    }

    public void fileDownload(HttpServletRequest req, HttpServletResponse resp, String originalFileName, String saveFileName) throws IOException {
        String applicationPath = req.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + UPLOAD_DIR;

        try {
            // 파일을 찾아 입력 스트림 생성
            File file = new File(uploadFilePath, saveFileName);
            InputStream inputStream = new FileInputStream(file);

            // 한글 파일명 깨짐 방지
            originalFileName = new String(originalFileName.getBytes("UTF-8"), "ISO-8859-1");

            // 파일 다운로드용 응답 헤더 설정
            resp.reset();
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileName + "\"");
            resp.setHeader("Content-Length", "" + file.length());

            OutputStream outputStream = resp.getOutputStream();
            // 출력 스트림에 파일 내용 출력
            byte b[] = new byte[(int) file.length()];
            int readBuffer = 0;
            while ((readBuffer = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, readBuffer);
            }
            inputStream.close();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>alert('파일을 찾을 수 없습니다.');history.back(-1);</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
