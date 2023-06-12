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
    private DB_Driver driver = new DB_Driver();

    // 게시글 목록 가져오기
    public ArrayList<CommunityDTO> CommunityList() {
        String query = "SELECT * FROM BOARD ORDER BY ID DESC";
        ArrayList<CommunityDTO> list = new ArrayList<CommunityDTO>();
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                CommunityDTO dto = new CommunityDTO();
                dto.setId(rs.getInt("id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setPostdate(rs.getString("postdate"));
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
                dto.setId(rs.getInt("id"));
                dto.setTitle(rs.getString("title"));
                dto.setName(rs.getString("name"));
                dto.setPostdate(rs.getString("postdate"));
                dto.setVisit_count(rs.getInt("visit_count"));
                dto.setLike_count(rs.getInt("like_count"));
                dto.setContent(rs.getString("content"));
                dto.setOriginal_file(rs.getString("original_file"));
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

    public int CommunityWrite(CommunityDTO dto) {
        String query = "INSERT INTO BOARD (id, user_id, name, title, content, postdate, original_file, save_file) VALUES (SEQ_BOARD_ID.NEXTVAL, '"
                + dto.getUser_id() + "', '" + dto.getName() + "', '" + escapeSingleQuotes(dto.getTitle()) + "', '"
                + escapeSingleQuotes(dto.getContent())
                + "', SYSDATE, '"
                + dto.getOriginal_file() + "', '" + dto.getSave_file() + "')";
        int result = -1;
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(query);
            if (result > 0) {
                // 작성한 글 ID 가져오기
                query = "SELECT ID FROM (SELECT id FROM BOARD WHERE USER_ID='" + dto.getUser_id()
                        + "' ORDER BY ID DESC) WHERE ROWNUM=1";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    result = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(stmt, conn);
        }
        return result;
    }

    private String escapeSingleQuotes(String value) {
        return value.replace("'", "''");
    }

    public int CommunityDelete(String contentNumber, CommunityDTO dto) {
        String query = "DELETE FROM BOARD WHERE ID = '" + contentNumber + "' and USER_ID= '" + dto.getUser_id() + "'";
        int result = -1;
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(stmt, conn);
        }
        return result;
    }

    
}