package bcsdbeginner.jdbc.DBConnection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static bcsdbeginner.jdbc.DBConnection.DBConnectionConst.*;

@Slf4j
public class DBConnectionManager {
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // DriverManager를 사용해 DB에 연결
            log.info("connection={}", connection);  // mySQL의 드라이브가 들어가는지 동작 확인(기록)
            return connection;  // 연결된 DB 반환
        } catch (SQLException e) {
            throw new RuntimeException(e);  // SQLException 발생 시 RuntimeException을 던짐
        }
    }
}
