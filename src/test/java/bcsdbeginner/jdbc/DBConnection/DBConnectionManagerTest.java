package bcsdbeginner.jdbc.DBConnection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class DBConnectionManagerTest {

    @Test
    void getConnection() throws SQLException {
        Connection connection = DBConnectionManager.getConnection();
        assertThat(connection).isNotNull();
    }
}