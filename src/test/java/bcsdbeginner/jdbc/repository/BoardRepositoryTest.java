package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Board;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BoardRepositoryTest {

    BoardRepository boardRepository = new BoardRepository();

    @Test
    void createBoard() throws SQLException {
        Board board1 = new Board(1, 1, 1, "7주차 과제", "킨더 먹으며 하는 중.", LocalDateTime.now());
        Board createdBoard1 = boardRepository.createBoard(board1);
        assertNotNull(createdBoard1.getId());
        assertEquals(board1.getUser_id(), createdBoard1.getUser_id());
        assertEquals(board1.getTitle(), createdBoard1.getTitle());
        assertEquals(board1.getContent(), createdBoard1.getContent());

        Board board2 = new Board(2, 2, 1, "전공선택 희망과목", "데이터베이스설계(3), 머신러닝및실습(3), 데이터베이스시스템(3), 딥러닝및실습(3), 데이터마이닝(3), 빅데이터처리및실습(3)", LocalDateTime.now());
        Board createdBoard2 = boardRepository.createBoard(board2);
        assertNotNull(createdBoard2.getId());
        assertEquals(board2.getUser_id(), createdBoard2.getUser_id());
        assertEquals(board2.getTitle(), createdBoard2.getTitle());
        assertEquals(board2.getContent(), createdBoard2.getContent());
    }

    @Test
    void findById() throws SQLException {
        int boardId = 1;
        Board foundBoard = boardRepository.findById(boardId);
        System.out.println(foundBoard);
    }

    @Test
    void findAllBoard() throws SQLException {
        var allBoards = boardRepository.findAllBoard();
        System.out.println(allBoards);
    }

    @Test
    void updateTitle() throws SQLException {
        int boardId = 1;
        String newTitle = "6주차도 8주차도 아닌 7주차 과제";
        boardRepository.updateTitle(boardId, newTitle);
        Board updatedBoard = boardRepository.findById(boardId);
        assertNotNull(updatedBoard);
        assertEquals(newTitle, updatedBoard.getTitle());
    }

    @Test
    void updateContent() throws SQLException {
        int boardId = 1;
        String newContent = "킨더 다 먹어서 가나 먹는 중.";
        boardRepository.updateContent(boardId, newContent);
        Board updatedBoard = boardRepository.findById(boardId);
        assertNotNull(updatedBoard);
        assertEquals(newContent, updatedBoard.getContent());
    }

    @Test
    void deleteBoard() throws SQLException {
        int boardId = 2;
        boardRepository.deleteBoard(boardId);
        Board deletedBoard = boardRepository.findById(boardId);
    }

}