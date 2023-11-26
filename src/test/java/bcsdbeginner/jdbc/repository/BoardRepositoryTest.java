package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import bcsdbeginner.jdbc.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class BoardRepositoryTest {
    BoardRepository boardRepository = new BoardRepository();
    UserRepository userRepository = new UserRepository();

    @BeforeEach
    void clearDB() throws SQLException {
        Helper.clearDB();
    }

    @Test
    void save() throws SQLException {
        User user1 = new User("허준기", "gjwnsrl1012@koreatech.ac.kr", "1234");
        userRepository.createUser(user1);

        Board boardA = new Board(1, 1, "안녕하세요", "내 이름은 허준기!");

        Board newBoard = boardRepository.save(boardA);

        Assertions.assertThat("안녕하세요").isEqualTo(newBoard.getTitle());
    }

    @Test
    void delete() throws SQLException {
        User user1 = new User("허준기", "gjwnsrl1012@koreatech.ac.kr", "1234");
        userRepository.createUser(user1);

        Board boardA = new Board(1, 1, "안녕하세요", "내 이름은 허준기!");
        boardRepository.save(boardA);

        boardRepository.delete(1);
        //Assertions.assertThat(id).isEqualTo(1);
    }

    @Test
    void update() throws SQLException {
        User user1 = new User("허준기", "gjwnsrl1012@koreatech.ac.kr", "1234");
        userRepository.createUser(user1);

        Board boardA = new Board(1, 1, "안녕하세요", "내 이름은 허준기!");
        boardRepository.save(boardA);

        boardRepository.update(1, "title", "안녕!!");
        //Assertions.assertThat(id).isEqualTo(1);
    }

    static class Helper {
        public static void clearDB() throws SQLException {
            Connection connection = null;
            Statement statement = null;
            String sql1 = "delete from board";
            String sql2 = "alter table board AUTO_INCREMENT = 1";//DB에 넘길 SQL 작성
            String sql3 = "delete from users";
            String sql4 = "alter table users AUTO_INCREMENT = 1";
            try {
                connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
                statement = connection.createStatement();//SQL실행 하기위한 객체 PrepareStatement 생성

                statement.addBatch(sql1);
                statement.addBatch(sql2);
                statement.addBatch(sql3);
                statement.addBatch(sql4);

             /*   statement.setString(1, table);//DB컬럼과 자바 오브젝트 필드 바인딩
                statement.setString(2, table);*/

                statement.executeBatch();
            } catch (SQLException e) {
                //log.error("clearDB error={}", e);
                throw e;
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception e) {
                        //log.error("error", e);
                    }
                }

                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        //log.error("error", e);
                    }
                }
            }
        }
    }
}
