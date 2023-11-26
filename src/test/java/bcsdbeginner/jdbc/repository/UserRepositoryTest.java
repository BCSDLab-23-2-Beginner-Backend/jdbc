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

    @BeforeEach
    void clearDB() throws SQLException {
        Helper.clearDB();
    }

    @Test
    void createUser() throws SQLException {
        User user1 = new User("userA", "bcsd@koreatech.ac.kr", "1111");
        User newUser = userRepository.createUser(user1);
        newUser.setCreate_at(LocalDateTime.of(2000, 1, 3, 10, 10, 10));
        assertThat(newUser.getUsername()).isEqualTo("userA");
    }

    @Test
    void findById() throws SQLException {
        User user1 = new User("userA", "bcsdlab@koreatech.ac.kr", "1111");
        User newUser = userRepository.createUser(user1);

        User findUser = userRepository.findById(1);

        assertThat(findUser.getId()).isEqualTo(1);
    }

    @Test
    void updateUsername() throws SQLException {
        User user1 = new User("userA", "bcsdlab@koreatech.ac.kr", "1111");
        userRepository.createUser(user1);

        userRepository.updateUsername(1, "updateA");
        User updateUser = userRepository.findById(1);
        log.info("user={}", updateUser);
        assertThat(updateUser.getUsername()).isEqualTo("updateA");
    }

    @Test
    void deleteUser() throws SQLException {
        userRepository.deleteUser(1);

        Integer deleteId = userRepository.findById(1).getId();

        assertThat(deleteId).isNull();
    }

    static class Helper {
        public static void clearDB() throws SQLException {
            Connection connection = null;
            Statement statement = null;
            String sql1 = "delete from users";
            String sql2 = "alter table users AUTO_INCREMENT = 1";//DB에 넘길 SQL 작성

            try {
                connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
                statement = connection.createStatement();//SQL실행 하기위한 객체 PrepareStatement 생성

                statement.addBatch(sql1);
                statement.addBatch(sql2);

             /*   statement.setString(1, table);//DB컬럼과 자바 오브젝트 필드 바인딩
                statement.setString(2, table);*/

                statement.executeBatch();
            } catch (SQLException e) {
                log.error("clearDB error={}", e);
                throw e;
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception e) {
                        log.error("error", e);
                    }
                }

                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        log.error("error", e);
                    }
                }
            }
        }
    }
}