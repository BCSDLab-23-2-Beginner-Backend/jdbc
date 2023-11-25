package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Board;
import bcsdbeginner.jdbc.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.function.BiPredicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
class BoardRepositoryTest {

    BoardRepository boardRepository = new BoardRepository();
    @Test
    void save() {
        Board board = new Board(1, 1, 1, "대박", "어렵네^^", LocalDateTime.now());
        Integer newId = boardRepository.save(board);
        assertThat(newId).isEqualTo(1);
    }

    @Test
    void findById() {
        Board findBoard = boardRepository.findById(1);
        log.info("findBoard={}", findBoard);
        assertThat(findBoard.getId()).isEqualTo(1);
    }

    @Test
    void updatecontent(){
        boardRepository.updateContent(1, "ㅅ..,.,쉽네^^");
        Board updateBoard = boardRepository.findById(1);
        assertThat(updateBoard.getContent()).isEqualTo("ㅅ..,.,쉽네^^");
    }

    @Test
    void deleteUser(){
        boardRepository.deleteBoard(1);
        Board deleteBoard = boardRepository.findById(1);
        assertThat(deleteBoard.getId()).isNull();
    }
}