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

    // 검색 결과 DTO에 담아서 리턴
    public ArrayList<CommunityDTO> CommunitySearch(SearchDTO dto) {
        ArrayList<CommunityDTO> list = new ArrayList<CommunityDTO>();
        conn = driver.getConnect();
        try {
            String query = SearchByConditions(dto);
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

    // 검색 조건
    private String SearchByConditions(SearchDTO dto) {
        String query = null;
        if (dto.getCurrentSearchBy() != null) {
            query = CurrentSearchBy(dto, query);
        } else if (dto.getCurrentSearchOrdeyBy() != null) {
            query = CurrentSearchOrdeyBy(dto, query);
        }
        return query;
    }

    // 정렬(최신, 제목, 작성자, 좋아요) 검색
    private String CurrentSearchOrdeyBy(SearchDTO dto, String query) {
        if (dto.getCurrentSearchOrdeyBy().equals("recent")) {
            query = "SELECT * FROM BOARD ORDER BY POSTDATE DESC";
        } else if (dto.getCurrentSearchOrdeyBy().equals("title")) {
            query = "SELECT * FROM BOARD ORDER BY TITLE ASC";
        } else if (dto.getCurrentSearchOrdeyBy().equals("author")) {
            query = "SELECT * FROM BOARD ORDER BY NAME ASC";
        } else if (dto.getCurrentSearchOrdeyBy().equals("recommend")) {
            query = "SELECT * FROM BOARD ORDER BY LIKE_COUNT DESC";
        }
        return query;
    }

    // 제목, 작성자, 내용 검색
    private String CurrentSearchBy(SearchDTO dto, String query) {
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
                query = "SELECT * FROM BOARD WHERE LOWER(TITLE) LIKE LOWER('%" + dto.getKeywords() + "%')";
            }
        } else if (dto.getCurrentSearchBy().equals("author")) {
            if (!dto.getCurrentSearchDate().isEmpty()) {
                query = "SELECT * FROM BOARD WHERE LOWER(NAME) LIKE LOWER('%" + dto.getKeywords() + "%')" +
                        "AND TRUNC(POSTDATE) = TO_DATE('" + dto.getCurrentSearchDate() + "', 'YYYY-MM-DD')";
            } else {
                query = "SELECT * FROM BOARD WHERE LOWER(NAME) LIKE LOWER('%" + dto.getKeywords() + "%')";
            }
        }
        return query;
    }
}
