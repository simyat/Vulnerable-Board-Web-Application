package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.DB_Driver;

public class SearchDAO {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private DB_Driver driver = new DB_Driver();

    public ArrayList<CommunityDTO> CommunitySearch(SearchDTO dto) {
        ArrayList<CommunityDTO> list = new ArrayList<CommunityDTO>();
        conn = driver.getConnect();
        try {
            String query = searchBy(dto);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                CommunityDTO communityDTO = new CommunityDTO();
                communityDTO.setId(rs.getInt("id"));
                communityDTO.setName(rs.getString("name"));
                communityDTO.setTitle(rs.getString("title"));
                communityDTO.setPostdate(rs.getString("postdate"));
                communityDTO.setVisit_count(rs.getInt("visit_count"));
                communityDTO.setLike_count(rs.getInt("like_count"));
                list.add(communityDTO);
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

    private String searchBy(SearchDTO dto) {
        String query = null;
        if (dto.getCurrentSearchBy().equals("posts")) {
            if (!dto.getCurrentSearchDate().isEmpty()) {
                query = "SELECT * FROM BOARD WHERE LOWER(CONTENT) LIKE LOWER('%" + dto.getKeywords() + "%')" +
                        "AND TRUNC(POSTDATE) = TO_DATE('" + dto.getCurrentSearchDate() + "', 'YYYY-MM-DD')";
            } else {
                query = "SELECT * FROM BOARD WHERE LOWER(CONTENT) LIKE LOWER('%" + dto.getKeywords() + "%')";
            }
        } else if (dto.getCurrentSearchBy().equals("title")) {
            if (!dto.getCurrentSearchDate().isEmpty()) {
                query = "SELECT * FROM BOARD WHERE LOWER(TITLE) LIKE LOWER('%" + dto.getKeywords() + "%')" +
                        "AND TRUNC(POSTDATE) = TO_DATE('" + dto.getCurrentSearchDate() + "', 'YYYY-MM-DD')";
            } else {
                query = "SELECT * FROM BOARD WHERE LOWER(TITLE) = LOWER('" + dto.getKeywords() + "')";
            }
        } else if (dto.getCurrentSearchBy().equals("author")) {
            if (!dto.getCurrentSearchDate().isEmpty()) {
                query = "SELECT * FROM BOARD WHERE LOWER(NAME) LIKE LOWER('%" + dto.getKeywords() + "%')" +
                        "AND TRUNC(POSTDATE) = TO_DATE('" + dto.getCurrentSearchDate() + "', 'YYYY-MM-DD')";
            } else {
                query = "SELECT * FROM BOARD WHERE LOWER(NAME) = '" + dto.getKeywords() + "'";
            }
        }
        return query;
    }
}
