package bcsdbeginner.jdbc.DBConnection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionManagerTest {

    @Test
    void getConnection() throws SQLException {
        Connection connection = DBConnectionManager.getConnection();
        Assertions.assertThat(connection).isNotNull();
    }
}