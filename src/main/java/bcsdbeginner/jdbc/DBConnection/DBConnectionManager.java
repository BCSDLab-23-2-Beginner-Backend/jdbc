package bcsdbeginner.jdbc.DBConnection;

import lombok.extern.slf4j.Slf4j;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static bcsdbeginner.jdbc.DBConnection.DBConnectionConstant.*;

// Lombok 라이브러리의 SLF4J를 이용하여 로그(log)를 기록하기 위한 어노테이션
@Slf4j
public class DBConnectionManager {

    // 데이터베이스 연결을 위한 Connection 객체 반환 메서드
    public static Connection getConnection() throws SQLException {
        try {
            // DriverManager를 이용하여 URL, 사용자 이름, 비밀번호를 이용하여 Connection 객체를 생성
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // 연결된 Connection 객체를 로그로 기록
            log.info("connection={}", connection);

            // 연결된 Connection 객체 반환
            return connection;
        } catch (SQLException e) {
            // SQLException이 발생하면 RuntimeException으로 감싸서 런타임 예외로 다시 던짐
            throw new RuntimeException(e);
        }
    }
}
