package bcsdbeginner.jdbc.DBConnection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection; // JDBC에서 데이터베이스와의 연결을 나타내는 인터페이스
import java.sql.DriverManager; // JDBC드라이버를 관리하고, 데이터베이스와의 연결을 제공하는 인터페이스
import java.sql.SQLException; // JDBC에서 데이터베이스와의 작업 시 발생할 수 있는 예외를 나타내는 클래스

import static bcsdbeginner.jdbc.DBConnection.DBConnectionConstant.*; //DBConnectionConstant의 멤버를 가져옴
@Slf4j // 자동으로 로거를 만들어줌
public class DBConnectionManager {
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // 데이터베이스에 연결된 Connection 개체 생성
            log.info("connection={}", connection); // 연결이 성공하면 로그에 연결된 Connection 객체를 출력
            return connection; // 연결된 Connection 객체 반환
        }
        catch (SQLException e) { // 연결에 실패했을 때 발생한 SQLException 예외를 catch
            throw new RuntimeException(e); // RuntimeException으로 감싸서 다시 던짐
        }
    }
}