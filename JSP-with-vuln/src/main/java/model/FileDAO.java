package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DB_Driver;

public class FileDAO {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private DB_Driver driver = new DB_Driver();

    public FileDTO getSaveFileNameFromDatabase(String originalFileName) {
        String query = "SELECT SAVE_FILE FROM BOARD WHERE ORIGINAL_FILE ='" + originalFileName + "'";
        FileDTO dto = new FileDTO();
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                dto.setDownloadsString(rs.getString("save_file"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(rs, stmt, conn);
        }
        return dto;
    }
}
