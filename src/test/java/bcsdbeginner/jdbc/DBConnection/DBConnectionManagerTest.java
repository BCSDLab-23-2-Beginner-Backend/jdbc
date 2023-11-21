package bcsdbeginner.jdbc.DBConnection;

import bcsdbeginner.jdbc.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionManagerTest {

    @Test
    void getConnection() throws SQLException {
        Connection connection = DBConnectionManager.getConnection();
        Assertions.assertThat(connection).isNotNull();

    }
}