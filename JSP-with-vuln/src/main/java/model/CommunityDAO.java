package model;

import util.DB_Driver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class CommunityDAO {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private DB_Driver driver = new DB_Driver();

    // 게시글 목록 개수 반환
    public int CommunityListCount() {
        int communityListCount = 0;
        String query = "SELECT COUNT(*) FROM BOARD";
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs.next(); 
            communityListCount = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(rs, stmt, conn);
        }
        return communityListCount;
    }

    // 검색 조건 없을 시 페이징
    public ArrayList<CommunityDTO> CommunityListPage(Map<String, Object> map) {
        ArrayList<CommunityDTO> list = new ArrayList<CommunityDTO>();
        String query = " SELECT * FROM ( " +
                " SELECT Tb.*, ROWNUM rNum FROM ( " +
                " SELECT * FROM BOARD ORDER BY POSTDATE DESC " +
                " ) Tb " +
                " ) " +
                " WHERE rNum BETWEEN '" + map.get("start").toString() + "' and '" + map.get("end").toString() + "'";

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

    //검색 조건이 있다면 SearchDAO Query 추가해 검색한 뒤, 페이징
    public ArrayList<CommunityDTO> CommunityListPage(Map<String, Object> map, String searchQuery) {
        ArrayList<CommunityDTO> list = new ArrayList<CommunityDTO>();
        String query = " SELECT * FROM ( " +
                " SELECT Tb.*, ROWNUM rNum FROM ( "
                + searchQuery +
                " ) Tb " +
                " ) " +
                " WHERE rNum BETWEEN '" + map.get("start").toString() + "' and '" + map.get("end").toString() + "'";

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

    // 글 읽기
    public CommunityDTO CommunityContent(String postId) {
        String query = "SELECT * FROM BOARD WHERE ID='" + postId + "'";
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

    // 조회 수 업데이트
    public void UpdateVisitCount(String contentNumber) {
        String query = "UPDATE BOARD SET " + " visit_count=visit_count+1 " + " WHERE id='" + contentNumber + "'";
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(stmt, conn);
        }
    }

    //  글 수정
    public int CommunityModify(CommunityDTO dto) {
        String query = "UPDATE BOARD SET user_id='"+ dto.getUser_id() +"', name='" + dto.getName() + "', title='" 
        + escapeSingleQuotes(dto.getTitle()) + "', content='" + escapeSingleQuotes(dto.getContent())
                + "', postdate=SYSDATE, original_file='" + dto.getOriginal_file() + "' WHERE id='" + dto.getId() + "'";
        int result = -1;
        int postId = -1;
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(query);
            if (result > 0) {
                // 작성한 글 ID 가져오기
                postId = dto.getId();
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
        return postId;
    }

    //  글 작성
    public int CommunityWrite(CommunityDTO dto) {
        String query = "INSERT INTO BOARD (id, user_id, name, title, content, postdate, original_file) VALUES (SEQ_BOARD_ID.NEXTVAL, '"
                + dto.getUser_id() + "', '" + dto.getName() + "', '" + escapeSingleQuotes(dto.getTitle()) + "', '"
                + escapeSingleQuotes(dto.getContent()) + "', SYSDATE, '"
                + dto.getOriginal_file() + "')";
        int result = -1;
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(query);
            if (result > 0) {
                // 작성한 최신 글 ID 가져오기
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

    // 글 삭제
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