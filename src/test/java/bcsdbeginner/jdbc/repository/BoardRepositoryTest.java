package bcsdbeginner.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import bcsdbeginner.jdbc.domain.Board;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class BoardRepositoryTest {

    BoardRepository boardRepository = new BoardRepository();

    @Test
    void Createboard() throws SQLException {
        // #1 테스트 코드 작성을 위해 카테고리를 미리 생성하여 객체 값 부여
        Board board1 = new Board(1, 1, 1,
                "New Title", "abcde", LocalDateTime.now());
        Board newBoard = boardRepository.Createboard(board1); // #2 새로운 Board를 생성
        assertThat(newBoard.getUser_id()).isEqualTo(1); // #3 테스트 코드 작성하여, 만들어진 boardid가 1이면 통과
    }

    @Test
    void findById() throws SQLException
    {
        Board findBoard = boardRepository.findById(1); // #1 boardid가 1인 보드를 findBoard로 설정
        log.info("findBoard = {}", findBoard); // #2 로그 출력용
        assertThat(findBoard.getId()).isEqualTo(1); // #3 테스트 코드 작성하여, boardId가 1이면 통과
    }

    @Test
    void updateBoard() throws SQLException
    {
        boardRepository.updateTitle(1, "update Title"); // #1 boardid가 1인 board의 title을 "update Title"로 변경
        Board updateBoard = boardRepository.findById(1); // #2 바뀐 보드에 findById 실행
        log.info("updateTitle = {}", updateBoard); // #2 로그 출력용
        assertThat(updateBoard.getTitle()).isEqualTo("update Title"); // #4 테스트 코드 작성하여, 바뀌었다면 통과
    }

    @Test
    void deleteBoard() throws SQLException {
        boardRepository.deleteBoard(1); // #1 boardid가 1인 board 삭제
        Board deleteBoard = boardRepository.findById(1); // #2 boardid가 1인 board에 findById 실행
        log.info("deleteBoard = {}", deleteBoard); // #3 로그 출력용
        assertThat(deleteBoard.getId()).isNull(); // $4 테스트 코드 작성하여, 비어있으면 통과
    }
}