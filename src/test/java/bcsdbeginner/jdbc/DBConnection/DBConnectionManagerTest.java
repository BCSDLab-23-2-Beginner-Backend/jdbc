package bcsdbeginner.jdbc.DBConnection;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DBConnectionManagerTest {

    @SneakyThrows
    @Test
    void getConnection() {
        Connection connection = DBConnectionManager.getConnection();
        assertThat(connection).isNotNull();
    }
}