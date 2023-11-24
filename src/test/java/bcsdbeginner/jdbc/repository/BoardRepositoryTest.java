package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class BoardRepositoryTest {
    BoardRepository boardRepository = new BoardRepository();

    @BeforeEach
    void clearDB() throws SQLException {
        Helper.clearDB();
    }

//    @Test
//    void createBoard() throws SQLException {
//        Board board1 = new Board("Juyun", "ForReal"); // Board 제목, 내용 설정
//        Board newBoard = boardRepository.createBoard(board1); // 앞서 설정한 토대로 새로운 Board 생성
//        newBoard.setCreate_at(LocalDateTime.of(2000, 1, 3, 10, 10, 10));
//        assertThat(newBoard.getTitle()).isEqualTo("Juyun"); // 넣어야 하는 값이 맞는지 비교 확인
//    }

//    @Test
//    void findById() throws SQLException {
//        Board board1 = new Board("Juyun", "ForReal");
//        Board newBoard = boardRepository.createBoard(board1);
//
//        Board findBoard = boardRepository.findById(1);
//
//        assertThat(findBoard.getId()).isEqualTo(1);
//    }
//    @Test
//    void readBoard() throws SQLException{
//        Board board1 = new Board("Juyun", "ForReal");
//        Board newBoard = boardRepository.createBoard(board1);
//        boardRepository.readBoard(1);
//    }
//
//    @Test
//    void updateBoard() throws SQLException {
//        Board board1 = new Board("Juyun", "ForReal");
//        boardRepository.createBoard(board1);
//
//        boardRepository.updateTitle(1, "FAKE");
//        Board updateBoard = boardRepository.findById(1);
//        log.info("board={}", updateBoard);
//        assertThat(updateBoard.getTitle()).isEqualTo("FAKE");
//    }

    @Test
    void deleteBoard() throws SQLException {
        boardRepository.deleteBoard(1);

        Integer deleteId = boardRepository.findById(1).getId();

        assertThat(deleteId).isNull();
    }

    static class Helper { // 실행 때 초기화 해주기 위한 도우미
        public static void clearDB() throws SQLException {
            Connection connection = null;
            Statement statement = null;
            String sql1 = "delete from board";
            String sql2 = "alter table board AUTO_INCREMENT = 1"; // DB에 넘길 SQL 작성

            try {
                connection = DBConnectionManager.getConnection(); // DriverManger 통해서 DB커넥션 생성
                statement = connection.createStatement(); // SQL실행 하기위한 객체 PrepareStatement 생성

                statement.addBatch(sql1);
                statement.addBatch(sql2);

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