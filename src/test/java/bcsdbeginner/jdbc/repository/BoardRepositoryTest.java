package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class BoardRepositoryTest {
    BoardRepository boardRepository = new BoardRepository();

    @BeforeEach
    void clearDB() throws SQLException {
        UserRepositoryTest.Helper.clearDB();
    }

    @Test
    void createBoard() throws SQLException {
        // board 객체 생성
        Board board1 = new Board(1, 1, "bcsd", "aaa");
        // boardRepository의 createBoard 메소드 호출
        Board newBoard = boardRepository.createBoard(board1);
        newBoard.setCreated_at(LocalDateTime.of(2000, 1, 3, 10, 10, 10));
        assertThat(newBoard.getUser_id()).isEqualTo(1);
    }

    @Test
    void findById() throws  SQLException {
        // board 객체 생성
        Board board1 = new Board(1, 1, "bcsd", "aaa");
        // boardRepository의 createBoard 메소드 호출
        Board newBoard = boardRepository.createBoard(board1);

        Board findBoard = boardRepository.findById(1);

        assertThat(findBoard.getId()).isEqualTo(1);
    }

    @Test
    void updateBoardTitle() throws SQLException {
        Board board1 = new Board(1, 1, "bcsd", "aaa");
        boardRepository.createBoard(board1);

        boardRepository.updateBoardTitle(1, "updatebcsd");
        Board updateBoard = boardRepository.findById(1);
        log.info("board={}", updateBoard);
        assertThat(updateBoard.getTitle()).isEqualTo("updatebcsd");
    }

    @Test
    void deleteBoard() throws SQLException {
        // id가 1인 board 삭제
        boardRepository.deleteBoard(1);

        // id가 1인 board를 찾는다
        Integer deleteID = boardRepository.findById(1).getId();

        assertThat(deleteID).isNull();
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
