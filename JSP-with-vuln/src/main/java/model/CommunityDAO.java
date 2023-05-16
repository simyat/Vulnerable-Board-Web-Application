package model;

import util.DB_Driver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CommunityDAO {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    DB_Driver driver = new DB_Driver();

    // 게시글 목록 가져오기
    public ArrayList<CommunityDTO> CommunityList() {
        String query = "SELECT * FROM BOARD ORDER BY ID ASC";
        ArrayList<CommunityDTO> list = new ArrayList<CommunityDTO>();
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                CommunityDTO dto = new CommunityDTO();
                dto.setId(rs.getInt("id"));
                dto.setUser_id(rs.getString("user_id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setPostdate(rs.getString("postdate"));
                dto.setOriginal_file(rs.getString("original_file"));
                dto.setSave_file(rs.getString("save_file"));
                dto.setVisit_count(rs.getInt("visit_count"));
                dto.setLike_count(rs.getInt("like_count"));
                list.add(dto);
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
        return list;
    }

    // 게시글 상세정보 가져오기
    public CommunityDTO CommunityContent(String contentNumber) {
        String query = "SELECT * FROM BOARD WHERE ID='" + contentNumber + "'";
        CommunityDTO dto = null;
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                dto = new CommunityDTO();
                dto.setTitle(rs.getString("title"));
                dto.setName(rs.getString("name"));
                dto.setPostdate(rs.getString("postdate"));
                dto.setVisit_count(rs.getInt("visit_count"));
                dto.setLike_count(rs.getInt("like_count"));
                dto.setContent(rs.getString("content"));
                dto.setSave_file(rs.getString("save_file"));
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

    public void CommunityWrite(String user_id, String name, String title, String content){
        String query = "INSERT INTO BOARD(id, user_id, name, title, content, postdate, original_file, save_file, visit_count, like_count) VALUES ()";     
    }
}
