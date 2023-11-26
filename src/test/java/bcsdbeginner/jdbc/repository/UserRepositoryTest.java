package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class UserRepositoryTest {

    UserRepository userRepository = new UserRepository();

    //@BeforeEach
    //void clearDB() throws SQLException {
    //    Helper.clearDB();
    //}

    @Test
    void createUser() throws SQLException {
        User user1 = new User("abc1234", "userA", "1111", "bcsd@koreatech.ac.kr");
        User newUser = userRepository.createUser(user1);
        assertThat(newUser.getUser_name()).isEqualTo("userA");
    }

    @Test
    void findById() throws SQLException {
        User user1 = new User("abc1234", "userA", "1111", "bcsd@koreatech.ac.kr");
        User newUser = userRepository.createUser(user1);

        User findUser = userRepository.findById("abc1234");

        assertThat(findUser.getUser_id()).isEqualTo("abc1234");
    }

    @Test
    void updateUser() throws SQLException {
        User user1 = new User("abc1234", "userA", "1111", "bcsd@koreatech.ac.kr");

        userRepository.updateUser(user1);
    }

    @Test
    void deleteUser() throws SQLException {
        userRepository.deleteUser(1);

        String deleteId = userRepository.findById("abc1234").getUser_id();

        assertThat(deleteId).isNull();
    }


}