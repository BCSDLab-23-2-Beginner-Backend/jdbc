package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoardRepositoryTest {

    BoardRepository boardRepository = new BoardRepository();

//    @BeforeEach
//    void clearDB() throws SQLException {
//        BoardRepositoryTest.Helper.clearDB();
//    }

    @Test
    void createBoard() throws SQLException {
        Board board1 = new Board(3, 3, "board12 title", "board12 content");
        Board newBoard = boardRepository.createBoard(board1);
        newBoard.setCreate_at(LocalDateTime.of(2000, 1, 3, 10, 10, 10));
        assertThat(newBoard.getTitle()).isEqualTo("board12 title");
    }

    @Test
    void findBoardById() throws SQLException {
        Board findBoard = boardRepository.findBoardById(12);
        assertThat(findBoard.getId()).isEqualTo(12);
    }

    @Test
    void findBoardByUserId() throws SQLException {
        Board findBoard = boardRepository.findBoardByUserId(3);
        assertThat(findBoard.getUser_id()).isEqualTo(3);
    }

    @Test
    void findBoardByCategoryId() throws SQLException {
        Board findBoard = boardRepository.findBoardByCategoryId(2);
        assertThat(findBoard.getCategory_id()).isEqualTo(2);
    }

    @Test
    void updateBoardTitle() throws SQLException {
        boardRepository.updateBoardTitle(4, "updated board4 title");
        Board updateBoard = boardRepository.findBoardById(4);
        assertThat(updateBoard.getTitle()).isEqualTo("updated board4 title");
    }

    @Test
    void updateBoardContent() throws SQLException {
        boardRepository.updateBoardContent(7, "updated board7 content");
        Board updateBoard = boardRepository.findBoardById(7);
        assertThat(updateBoard.getContent()).isEqualTo("updated board7 content");
    }

    @Test
    void deleteBoard() throws SQLException {
        boardRepository.deleteBoard(12);
        Integer deletId = boardRepository.findBoardById(12).getId();
        assertThat(deletId).isNull();
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
//                log.error("clearDB error={}", e);
                throw e;
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception e) {
//                        log.error("error", e);
                    }
                }

                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
//                        log.error("error", e);
                    }
                }
            }
        }
    }
}