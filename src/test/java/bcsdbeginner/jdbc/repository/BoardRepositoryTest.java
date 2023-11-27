package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import bcsdbeginner.jdbc.domain.User;
import bcsdbeginner.jdbc.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BoardRepositoryTest {
    BoardRepository boardRepository = new BoardRepository();
    //@BeforeEach
    void clearDB() throws SQLException {
        Helper.clearDB(); //테스트하기 전에 다 지우기
    }
    @Test
    void createBoard() throws SQLException {
        Board board1 = new Board(1, 1, "Tired", "Exam");
        Board newBoard = boardRepository.createBoard(board1);
        newBoard.setCreate_at(LocalDateTime.of(2000, 1, 3, 10, 10, 10));
        assertThat(newBoard.getContent()).isEqualTo("Exam"); // 받아오는 값이랑 기대되는 값이랑 확인 !
    }

    @Test
    void findBoardId() throws SQLException {
        Board findBoard = boardRepository.findBoardId(1);
        assertThat(findBoard.getId()).isEqualTo(1);
    }

    @Test
    void updateBoardname() throws SQLException {
        boardRepository.updateBoardname(1, "Funny", "Assignment");
        Board updateBoardname = boardRepository.findBoardId(1);
        log.info("board={}", updateBoardname);
        assertThat(updateBoardname.getId()).isEqualTo(1);
    }

    @Test
    void deleteBoard() throws SQLException {
        boardRepository.deleteBoard(1);
        Integer deleteId = boardRepository.findBoardId(1).getId();

        assertThat(deleteId).isNull();
    }
    static class Helper {
        public static void clearDB() throws SQLException {
            Connection connection = null;
            Statement statement = null;
            String sql1 = "delete from board";
            String sql2 = "alter table board AUTO_INCREMENT = 1";//DB에 넘길 SQL 작성

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