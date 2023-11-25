package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class BoardRepositoryTest {
    BoardRepository boardRepository = new BoardRepository(); // 테스트를 위한 객체생성

    @BeforeEach // 각 테스트 메서드가 실행되기 전에 해당 메서드가 실행되도록 지정
    void clearDB() throws SQLException {
        Helper.clearDB();
    }

    @Test
    void createBoard() throws SQLException {
        Board board1 = new Board(1, 1, "첫게시글입니다", "반갑습니다");
        Board newBoard = boardRepository.createBoard(board1); // create를 실행하고 테스트용 객체를 반환받음
        assertThat(newBoard.getTitle()).isEqualTo("첫게시글입니다"); // 올바르게 생성됐는지 확인
    }

    @Test
    void findById() throws SQLException {
        Board board1 = new Board(1, 1, "첫게시글입니다", "반갑습니다");
        boardRepository.createBoard(board1); // create를 실행하여 테스트용 객체를 생성

        Board findBoard = boardRepository.findById(1); //find를 실행하고 find결과 리턴

        assertThat(findBoard.getId()).isEqualTo(1); // 제대로 조회됐는지 확인
    }

    @Test
    void updateContent() throws SQLException {
        Board board1 = new Board(1, 1, "첫게시글입니다", "반갑습니다");
        boardRepository.createBoard(board1); // create를 실행하여 테스트용 객체를 생성

        boardRepository.updateContent(1, "새로운내용"); // 업데이트 실행
        Board updateBoard = boardRepository.findById(1); // 업데이트 된 데이터 받아오기
        log.info("board={}", updateBoard); // 업데이트 데이터 로깅
        assertThat(updateBoard.getContent()).isEqualTo("새로운내용"); // 제대로 업데이트 됐는지 확인
    }

    @Test
    void deleteBoard() throws SQLException {
        Board board1 = new Board(1, 1, "첫게시글입니다", "반갑습니다");
        boardRepository.createBoard(board1); // create를 실행하여 테스트용 객체를 생성

        boardRepository.deleteBoard(1); // 삭제 실행

        Integer deleteId = boardRepository.findById(1).getId(); // 제대로 삭제됐는지 확인
        assertThat(deleteId).isNull();
    }

    static class Helper {
        public static void clearDB() throws SQLException {
            Connection connection = null;
            Statement statement = null;
            String sql1 = "DELETE FROM board"; // DB에 넘길 sql 작성
            String sql2 = "ALTER TABLE board AUTO_INCREMENT = 1";

            try {
                connection = DBConnectionManager.getConnection(); // DriverManger 통해서 DB커넥션 생성
                statement = connection.createStatement(); //SQL실행하기위한 객체 statement 생성(정적이므로 create)

                statement.addBatch(sql1); // 여러 개의 SQL문을 배치로 처리하여 한번에 전달
                statement.addBatch(sql2); // 동일

                statement.executeBatch(); // statement 객체에 추가한 sql문을 한번에 실행
            }
            catch (SQLException e) {
                log.error("clearDB error={}", e);
                throw e;
            }
            finally { // 역순으로 닫음
                
                if(statement != null)
                {
                    try {
                        statement.close();
                    }
                    catch (Exception e) {
                        log.error("error", e);
                    }
                }

                if(connection != null)
                {
                    try {
                        connection.close();
                    }
                    catch (Exception e) {
                        log.error("error", e);
                    }
                }

            }
        }
    }
}

//re