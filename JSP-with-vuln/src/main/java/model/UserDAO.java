package model;

import util.DB_Driver;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    private Connection conn; // DB Connection 객체
    private Statement stmt; // SQL문 질의 객체
    private ResultSet rs; // DB에서 리턴받는 데이터 객체
    DB_Driver driver = new DB_Driver();

    // 회원가입
    public int UserInsert(UserDTO vo) {
        String query = "INSERT INTO users(user_id, name, password, address) VALUES('" + vo.getUser_id() + "', '"
                + vo.getName() + "', '" + vo.getPassword() + "', + '" + vo.getAddress() + "')"; // Oracle query
        int cnt = -1; // query 실행 결과

        // Connecrtion 객체 생성
        conn = driver.getConnect();

        try {
            stmt = conn.createStatement(); // 불완전한 SQL문을 전송해 미리 컴파일을 시킨다. (미리 컴파일을 시켰기에 속도가 빠르다)
            cnt = stmt.executeUpdate(query); // 전송(SQL 실행) -> // 정상적으로 Insert 되었다면 성공한 행은 1개이므로 1이 리턴된다. Insert 실패하면 0을
                                             // 리턴한다.
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            String message = e.getMessage();
            if (message.contains("ORA-00001: unique constraint")) {
                cnt = 4;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(stmt, conn);
        }
        return cnt; // 1 or 0 or 4
    } // UserInsert()

    // 로그인
    public UserDTO UserSelect(String userId, String userPass) {
        UserDTO vo = new UserDTO();
        String query = "SELECT * FROM users WHERE user_id='" + userId + "'" + " AND password='" + userPass + "'";
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

    // 주소 검색
    public ArrayList<AddressDTO> SearchAddress(String address) {
        ArrayList<AddressDTO> vo_list = new ArrayList<>();
        String sido = null;
        String sigungu = null;
        String doro_name = null;
        String sigungu_building_name = null;
        String query = null;

        ArrayList<String> splitAddress = SplitAddress(address); // " " 기준으로 문자열 자르기

        sigungu = (String) splitAddress.get(0);
        doro_name = (String) splitAddress.get(1);

        if (splitAddress.size() == 3) {
            sigungu_building_name = (String) splitAddress.get(2);
            query = "SELECT SIDO, SIGUNGU, DORO_NAME, SIGUNGU_BUILDING_NAME FROM postcode WHERE SIGUNGU='"
                    + sigungu + "' AND DORO_NAME='" + doro_name + "' AND SIGUNGU_BUILDING_NAME='"
                    + sigungu_building_name + "'";
        } else {
            query = "SELECT SIDO, SIGUNGU, DORO_NAME, SIGUNGU_BUILDING_NAME FROM postcode WHERE SIGUNGU='"
                    + sigungu + "' AND DORO_NAME='" + doro_name + "'";
        }
        conn = driver.getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                AddressDTO vo = new AddressDTO();

                sido = rs.getString("sido");
                sigungu = rs.getString("sigungu");
                doro_name = rs.getString("doro_name");
                sigungu_building_name = rs.getString("sigungu_building_name");

                vo.setSido(sido);
                vo.setSigungu(sigungu);
                vo.setDoro_name(doro_name);
                if (sigungu_building_name == null) {
                    vo.setSigungu_building_name(" ");
                } else {
                    vo.setSigungu_building_name(sigungu_building_name);
                }
                vo_list.add(vo);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.dbClose(rs, stmt, conn);
        }
        return vo_list;
    }

    // 주소 분할 분리 예정
    private ArrayList<String> SplitAddress(String address) {
        String tmp = null;
        String[] result;
        String sigungu = null;
        String doro_name = null;
        String sigungu_building_name = null;
        ArrayList<String> list;

        if (address.contains("로 ")) {
            tmp = address.replace("로 ", "로");
            result = tmp.split(" ");
        } else {
            result = address.split(" ");
        }

        sigungu = result[0];
        doro_name = result[1];
        list = new ArrayList<String>();
        list.add(sigungu);
        list.add(doro_name);

        if (result.length == 3) {
            sigungu_building_name = result[2];
            list.add(sigungu_building_name);
        }
        return list;
    }
}
