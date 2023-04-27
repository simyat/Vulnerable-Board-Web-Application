package model;

import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.UserVO;

public class UserDAO {
        private Connection conn; // DB Connection 객체

        private Statement stmt;
        private PreparedStatement ps; // SQL문 질의 객체
        private ResultSet rs; // DB에서 리턴받는 데이터 객체

        // 데이터베이스 연결 객체 생성 (Connection)
        public void getConnect() {
//        데이터베이스 접속 URL
            String URL = "jdbc:oracle:thin:@localhost:1521:xe?serverTimezone=UTC&characterEncoding=UTF-8";
            String user = "inmo";
            String password = "inmo";

            // Driver Loading
            try {
//            동적로딩 (실행지점에서 객체를 생성하는 방법)
//            동적로딩 방법 -> 드라이버 클래스를 메모리에 로딩한다.
                Class.forName("oracle.jdbc.driver.OracleDriver");
//            Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, user, password); // 커넥션 연결
            /*
            1. 빌드 후 실행하면 com.mysql.jdbc.Driver or oracle.jdbc.driver.OracleDriver class 찾아 메모리에 클래스를 로딩한다.
            2. DriverManager와 Driver가 내부적으로 연결된다.
            3. getConnection() 실행하여 DB에 접속을 시도한다.
            4. 접속이 완료되면 Connection 객체를 conn (Connection Type) 변수에 담는다.
             */
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // getConnect()

        //    회원저장 동작 (Insert)
        public int UserInsert(UserVO vo) {
            String query = "INSERT INTO users(user_id, name, password, address) VALUES('" + vo.getUser_id() + "', '" + vo.getName() + "', '" + vo.getPassword() + "', + '" + vo.getAddress() + "')"; // Oracle query
            getConnect(); // Connecrtion 객체 생성

            int cnt = -1;

            try {
                stmt = conn.createStatement(); // 불완전한 SQL문을 전송해 미리 컴파일을 시킨다. (미리 컴파일을 시켰기에 속도가 빠르다)
                cnt = stmt.executeUpdate(query); // 전송(SQL 실행) -> // 정상적으로 Insert 되었다면 성공한 행은 1개이므로 1이 리턴된다. Insert 실패하면 0을 리턴한다.
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
                dbClose();
            }
            return cnt; // 1 or 0 or 4
        } // UserInsert()

        public UserVO UserSelect(String userId, String userPass) {
            UserVO vo = new UserVO();
            String query = "SELECT * FROM users WHERE user_id='" + userId + "'" + " AND password='" + userPass + "'";
            getConnect();
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
                dbClose();
            }

            return vo;
        }

    public ArrayList<AddressVO> SearchAddress(String address) {
        ArrayList<AddressVO> vo_list = new ArrayList<>();
        String sido = null;
        String sigungu = null;
        String doro_name = null;
        String sigungu_building_name = null;
        String query = null;


        ArrayList splitAddress = SplitAddress(address); // " " 기준으로 문자열 자르기

        sigungu = (String) splitAddress.get(0);
        doro_name = (String) splitAddress.get(1);

        if (splitAddress.size() == 3) {
            sigungu_building_name = (String) splitAddress.get(2);
            query = "SELECT SIDO, SIGUNGU, DORO_NAME, SIGUNGU_BUILDING_NAME FROM postcode WHERE SIGUNGU='"
                    + sigungu + "' AND DORO_NAME='" + doro_name + "' AND SIGUNGU_BUILDING_NAME='" + sigungu_building_name + "'";
        } else {
            query = "SELECT SIDO, SIGUNGU, DORO_NAME, SIGUNGU_BUILDING_NAME FROM postcode WHERE SIGUNGU='"
                    + sigungu + "' AND DORO_NAME='" + doro_name + "'";
        }
        getConnect();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                AddressVO vo = new AddressVO();

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
            dbClose();
        }
        return vo_list;
    }

    private ArrayList SplitAddress(String address) {
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

    public void dbClose() {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
