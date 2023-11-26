package bcsdbeginner.jdbc.DBConnection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class DBConnectionManagerTest {

    @Test
    void getConnection() throws SQLException {
        Connection connection = DBConnectionManager.getConnection();
        // DB에 연결
        assertThat(connection).isNotNull();
        // 연결된 DB가 없으면(Null이면) 예외 발생
    }
}