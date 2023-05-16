package CTF.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import util.DB_Driver;
import model.*;

// 로그인 페이지 SQL Injection Bypass 테스트
public class CTF_DAO {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    DB_Driver driver = new DB_Driver();

    public UserDTO IdentifyAuth(String userId, String userPass) {
        UserDTO vo = new UserDTO();
        // String query = "SELECT * FROM users WHERE user_id='" + userId + "'" + " AND password='" + userPass + "'";
        // String query = "SELECT * FROM users WHERE user_id='" + userId + "'" +"\n AND password='" + userPass + "'";
        // String query = "SELECT * FROM users WHERE user_id=('" + userId + "')" + " AND password=('" + userPass + "')";
        String query = "SELECT * FROM users WHERE user_id=('" + userId + "')" +"\n AND password=('" + userPass + "')";
        // String query = "SELECT * FROM users WHERE user_id='" + userId + "'" +"\n AND password=('" + userPass + "')";
        // String query = "SELECT * FROM users WHERE password=('" + userPass + "')" +"\n AND password=('" + userId + "')";
        // String query = "SELECT * FROM users WHERE password='" + userPass + "'" +"\n AND password='" + userId + "'";
        // String query = "SELECT * FROM users WHERE password='" + userPass + "'" +"AND password='" + userId + "'";



        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                vo.setUser_id(rs.getString("user_id"));
                vo.setName(rs.getString("name"));
                vo.setPassword(rs.getString("password"));
                vo.setAddress(rs.getString("address"));
                vo.setRegidate(rs.getString("regidate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(rs, stmt, conn);
        }
        return vo;
    }

    public UserDTO SeparateIdentifyAuth(String userId, String userPass) {
        UserDTO vo = new UserDTO();
        String query = "SELECT * FROM users WHERE user_id='" + userId + "'";
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                String pw = rs.getString("password");
                if (userPass.equals(pw)) {
                    vo.setUser_id(rs.getString("user_id"));
                    vo.setName(rs.getString("name"));
                    vo.setPassword(rs.getString("password"));
                    vo.setAddress(rs.getString("address"));
                    vo.setRegidate(rs.getString("regidate"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(rs, stmt, conn);
        }
        return vo;
    }
}
