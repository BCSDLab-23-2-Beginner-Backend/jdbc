package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import bcsdbeginner.jdbc.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @BeforeEach
    void clearDB() throws SQLException {
        BoardRepositoryTest.Helper.clearDB();
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void createBoard() throws SQLException {
        Board board1 = new Board(1L, 1L, "게시글 1번 제목","게시글1번 내용입니다.");
        Board newBoard = boardRepository.createBoard(board1);
        newBoard.setCreated_at(LocalDateTime.of(2023, 11, 23, 10, 10, 10));
        assertThat(newBoard.getTitle()).isEqualTo("게시글 1번 제목");
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void findById() throws SQLException{
        Board board1 = new Board(1L, 1L, "게시글 2번 제목","게시글2번 내용입니다.");
        Board newBoard = boardRepository.createBoard(board1);
        Board findBoard = boardRepository.findById(1L);
        assertThat(findBoard.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updateBoard() throws SQLException{
        Board board1 = new Board(1L,1L, "게시글 1번 제목", "게시글1번 내용입니다.");
        boardRepository.createBoard(board1);

        boardRepository.updateBoard(1L, 1L,"게시글 1번 수정 제목", "게시글1번 내용입니다.");
        Board updateBoard = boardRepository.findById(1L);
        log.info("board={}", updateBoard);
        assertThat(updateBoard.getTitle()).isEqualTo("게시글 1번 수정 제목");
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteBoard() throws SQLException{
        Board board1 = new Board(1L,1L, "게시글 1번 제목", "게시글1번 내용입니다.");
        boardRepository.deleteBoard(1L);
        Long deleteId = boardRepository.findById(1L).getId();
        assertThat(deleteId).isNull();
    }

    @Slf4j
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