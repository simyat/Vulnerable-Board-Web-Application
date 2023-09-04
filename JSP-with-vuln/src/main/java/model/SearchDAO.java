package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.DB_Driver;

// 게시판 검색 DAO
public class SearchDAO {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private DB_Driver driver = new DB_Driver();

    public String CommunitySearchQuery(SearchDTO dto) {
        String query = SearchByConditions(dto);
        return query;
    }

    // 검색 조건에 맞는 게시물 개수 반환
    public int CommunitySearchCount(SearchDTO dto) {
        int searchCount = 0;
        conn = driver.getConnect();
        ArrayList<Integer> size = new ArrayList<>();

        try {
            String query = SearchByConditions(dto);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                size.add(rs.getInt(1));
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
        searchCount = size.size(); // 리스트에 담은 게시물 개수 반환
        return searchCount; // 게시물 개수 서블릿으로 반환
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
            if (!dto.getSearchFromDate().isEmpty()) {
                query = "SELECT * FROM BOARD WHERE LOWER(CONTENT) LIKE LOWER('%" + dto.getKeywords() + "%')" +
                        "AND POSTDATE >= TO_DATE('" + dto.getSearchFromDate()
                        + "', 'YYYY-MM-DD') AND POSTDATE <= TO_DATE('" + dto.getSearchToDate() + "', 'YYYY-MM-DD') + 1";
            } else {
                query = "SELECT * FROM BOARD WHERE LOWER(CONTENT) LIKE LOWER('%" + dto.getKeywords() + "%')";
            }
        } else if (dto.getCurrentSearchBy().equals("title")) {
            if (!dto.getSearchFromDate().isEmpty()) {
                query = "SELECT * FROM BOARD WHERE LOWER(TITLE) LIKE LOWER('%" + dto.getKeywords() + "%')" +
                        "AND POSTDATE >= TO_DATE('" + dto.getSearchFromDate()
                        + "', 'YYYY-MM-DD') AND POSTDATE <= TO_DATE('" + dto.getSearchToDate() + "', 'YYYY-MM-DD') + 1";
            } else {
                query = "SELECT * FROM BOARD WHERE LOWER(TITLE) LIKE LOWER('%" + dto.getKeywords() + "%')";
            }
        } else if (dto.getCurrentSearchBy().equals("author")) {
            if (!dto.getSearchFromDate().isEmpty()) {
                query = "SELECT * FROM BOARD WHERE LOWER(NAME) LIKE LOWER('%" + dto.getKeywords() + "%')" +
                        "AND POSTDATE >= TO_DATE('" + dto.getSearchFromDate()
                        + "', 'YYYY-MM-DD') AND POSTDATE <= TO_DATE('" + dto.getSearchToDate() + "', 'YYYY-MM-DD') + 1";
            } else {
                query = "SELECT * FROM BOARD WHERE LOWER(NAME) LIKE LOWER('%" + dto.getKeywords() + "%')";
            }
        }
        return query;
    }
}
