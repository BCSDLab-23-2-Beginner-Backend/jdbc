package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class BoardRespositoryTest {

    BoardRepository boardRepository = new BoardRepository();

    @Test
    void createBoard() throws SQLException {
        Board board1 = new Board("안녕하세요.", "안녕하세요. 첫 게시글을 작성했습니다.", 6,1);
        Board newBoard = boardRepository.createBoard(board1);
        newBoard.setCreated_at(LocalDateTime.of(2000, 1, 3, 10, 10, 10));
        assertThat(newBoard.getTitle()).isEqualTo("안녕하세요.");
    }

    @Test
    void findById() throws SQLException {

        Board findUser = boardRepository.findById(2);

        assertThat(findUser.getId()).isEqualTo(2);
    }

    @Test
    void updateBoard() throws SQLException {
        boardRepository.updateBoard(2, "반갑습니다.","반갑습니다. 첫 게시글을 수정했습니다.");
        Board updateBoard = boardRepository.findById(1);
        //log.info("user={}", updateBoard);
        assertThat(updateBoard.getTitle()).isEqualTo("반갑습니다.");
    }

    @Test
    void deleteBoard() throws SQLException {
        boardRepository.deleteBoard(3);

        Integer deleteId = boardRepository.findById(3).getId();

        assertThat(deleteId).isNull();
    }

}
