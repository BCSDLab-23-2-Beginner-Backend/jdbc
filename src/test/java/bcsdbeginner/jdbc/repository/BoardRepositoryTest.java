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
class BoardRepositoryTest {

    // 각 테스트 메서드 실행 전 데이터베이스 초기화
    BoardRepository boardRepository = new BoardRepository();

    @BeforeEach
    void clearDB() throws SQLException {
        BoardRepositoryTest.Helper.clearDB();
    }

    // 게시물 게시
    @Test
    void createBoard() throws SQLException {
        Board board1 = new Board(1, "BCSD과제", "JDBC과제해오세용");
        Board newBoard = boardRepository.createBoard(board1);
        newBoard.setCreated_at(LocalDateTime.now());
        assertThat(newBoard.getTitle()).isEqualTo("BCSD과제"); // getTitle의 반환 값이 BCSD과제와 같은지 검사(테스트가 성공적으로 되는지)
    }

    // 게시물을 작성한 user_id로 검색(게시물 id로 찾는 것은 말이 안된다고 생각하기 때문)
    @Test
    void findById() throws SQLException{
        Board board1 = new Board(1, "BCSD과제", "JDBC과제해오세용");
        Board newBoard = boardRepository.createBoard(board1);

        Board findBoard = boardRepository.findById(1);
        assertThat(findBoard.getId()).isEqualTo(1);
    }

    // 게시물 제목으로 검색
    @Test
    void findByTitle() throws SQLException{
        Board board1 = new Board(1, "BCSD과제", "JDBC과제해오세용");
        Board newBoard = boardRepository.createBoard(board1);

        Board findBoard = boardRepository.findByTitle("BCSD과제");
        assertThat(findBoard.getId()).isEqualTo(1);
    }

    // 게시물 제목 및 내용 수정
    @Test
    void updateBoard() throws SQLException{
        Board board1 = new Board(1, "BCSD과제", "JDBC과제해오세용");
        boardRepository.createBoard(board1);

        boardRepository.updateBoard(1, "BCSD과제취소", "과제안해도됩니다!");
        Board updateBoard = boardRepository.findById(1);
        log.info("board={}", updateBoard);
        assertThat(updateBoard.getTitle()).isEqualTo("BCSD과제취소");
    }

    // 게시물 제목으로 삭제
    @Test
    void deleteBoard() throws SQLException{
        Board board1 = new Board(1, "BCSD과제", "JDBC과제해오세용");
        boardRepository.deleteBoard("BCSD과제");

        Integer deleteId = boardRepository.findByTitle("BCSD과제").getId();
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