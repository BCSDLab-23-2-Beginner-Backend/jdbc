package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BoardRepositoryTest {
    BoardRepository boardRepository = new BoardRepository();

    @Test
    void createBoard() throws SQLException {
        Board board1 = new Board(1, 1, "Hello Beginner", "This is bcsdLab.");
        Board newboard = boardRepository.createBoard(board1);

        assertThat(newboard.getTitle()).isEqualTo("Hello Beginner");
    }

    @Test
    void findById() throws SQLException {
        Board findBoard = boardRepository.findById(1);

        assertThat(findBoard.getTitle()).isEqualTo("Hello Beginner");
    }

    @Test
    void updateBoard() throws SQLException {
        Board findBoard = boardRepository.findById(1);
        boardRepository.updateBoard(1,
                findBoard.getCategory_id(),
                findBoard.getTitle(),
                "I'm at home.");

        findBoard = boardRepository.findById(1);
        assertThat(findBoard.getContent()).isEqualTo("I'm at home.");
    }

    @Test
    void deleteBoard() throws SQLException {
        Board board1 = new Board(1, 1, "Hello Beginner", "This is bcsdLab.");
        Board newBoard = boardRepository.createBoard(board1);

        boardRepository.deleteBoard(newBoard.getId());

        assertThat(boardRepository.findById(newBoard.getId()).getId()).isNull();
    }
}