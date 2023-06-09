package util;

import java.sql.*;

public class DB_Driver {
    private Connection conn; // DB Connection 객체
    private ResultSet rs; // DB에서 리턴받는 데이터 객체

    public Connection getConnect() {
        // 데이터베이스 접속 URL
        String URL = "jdbc:oracle:thin:@localhost:1521:xe?serverTimezone=UTC&characterEncoding=UTF-8";
        String user = "inmo";
        String password = "inmo";

        // Driver Loading
        try {
            // 동적로딩 (실행지점에서 객체를 생성하는 방법)
            // 동적로딩 방법 -> 드라이버 클래스를 메모리에 로딩한다.
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, user, password); // 커넥션 연결
            /*
             * 1. 빌드 후 실행하면 com.mysql.jdbc.Driver or oracle.jdbc.driver.OracleDriver class
             * 찾아 메모리에 클래스를 로딩한다.
             * 2. DriverManager와 Driver가 내부적으로 연결된다.
             * 3. getConnection() 실행하여 DB에 접속을 시도한다.
             * 4. 접속이 완료되면 Connection 객체를 conn (Connection Type) 변수에 담는다.
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    } // getConnect()

    public void dbClose(Statement stmt, Connection conn) {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dbClose(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // dbClose()
}
