package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import bcsdbeginner.jdbc.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
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
    @BeforeEach
    void clearDB() throws SQLException {
        Helper.clearDB();
        // 테스트를 위한 전처리
        // 게시글을 등록하기 위해선 user와 category와 같은 정보가 추가되어있어야함
    }
    @Test
    void createBoard() throws SQLException {
        // 게시글을 등록하고, 등록한 게시글을 findById를 이용해 받아온 다음 잘 등록 되었는지 확인
        Board board = new Board(1, 1, "제목2", "내용2");
        Integer boardId = boardRepository.createBoard(board);
        Board newBoard = boardRepository.findById(boardId);
        assertThat(newBoard.getUser_id()).isEqualTo(board.getUser_id());
        assertThat(newBoard.getCategory_id()).isEqualTo(board.getCategory_id());
        assertThat(newBoard.getTitle()).isEqualTo(board.getTitle());
        assertThat(newBoard.getContent()).isEqualTo(board.getContent());
    }

    @Test
    void findById() throws SQLException {
        Integer boardId = 1;
        createBoard();
        Board board = boardRepository.findById(boardId);
        assertThat(board.getId()).isEqualTo(boardId);
    }

    @Test
    void updateBoard() throws SQLException {
        // 업데이트하고싶은 값을 넘겨주고 DB에서 변경된 값을 불러와서 제대로 변경되었는지 확인.
        Integer boardId = 1;
        createBoard();
        boardRepository.updateBoard(boardId, null, null, "제1", "내1");
        Board board = boardRepository.findById(boardId);
        assertThat(board.getTitle()).isEqualTo("제1");
        assertThat(board.getContent()).isEqualTo("내1");
    }

    @Test
    void deleteBoard() throws SQLException {
        // 게시글을 삭제하고, 삭제한 게시글을 findById를 통해 조회한다.
        // 조회가 된다면 삭제 X
        // 조회가 안된다면 삭제 O
        Integer boardId = 1;
        createBoard();
        boardRepository.deleteBoard(boardId);
        Board board = boardRepository.findById(boardId);
        assertThat(board.getId()).isNull();
    }
    static class Helper {
        public static void clearDB() throws SQLException {
            UserRepositoryTest.Helper.clearDB();
            Connection connection = null;
            Statement statement = null;
            String sql1 = "delete from board";
            //필요한 데이터 추가 및 AI 초기화
            String sql2 = "alter table board AUTO_INCREMENT = 1";//DB에 넘길 SQL 작성
            String sql3 = "insert into users values(null, '이현수', 'leehy@naver.com', '1234', now())";
            try {
                connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
                statement = connection.createStatement();//SQL실행 하기위한 객체 PrepareStatement 생성

                statement.addBatch(sql1);
                statement.addBatch(sql2);
                statement.addBatch(sql3);

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