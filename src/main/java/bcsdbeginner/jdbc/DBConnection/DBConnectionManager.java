package bcsdbeginner.jdbc.DBConnection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static bcsdbeginner.jdbc.DBConnection.DBConnectionConstant.*;

@Slf4j
public class DBConnectionManager { // 데이터베이스와 커넥션을 얻는 메소드를 얻기 위한 클래스
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // DriverManager: 사용할 애플리케이션에 대해 사용 가능한 JDBC 드라이버 세트를 관리하는 클래스
            log.info("connection={}", connection); // 실제로 MySQL JDBC 드라이버가 들어오는지 확인하기 위한 코드.
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
