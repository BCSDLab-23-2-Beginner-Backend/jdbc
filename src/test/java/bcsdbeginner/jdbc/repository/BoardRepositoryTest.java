package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class BoardRepositoryTest {
    BoardRepository boardRepository = new BoardRepository();

    @Test
    void save() {   // 게시글 생성(Create)
        Board board = new Board(1, 1, 1, "Hello~!", "my name is Hwang.", LocalDateTime.now());
        // 게시글 객체 생성
        Integer newId = boardRepository.save(board);  // 생성한 게시글 객체를 데이터베이스에 저장
        assertThat(newId).isEqualTo(1);     // 저장된 게시글의 id가 1인지 확인(1이 아니면 예외 발생)
    }

    @Test
    void findByBoardId() {   // 게시글 탐색(Read)
        Board findBoard = boardRepository.findByBoardId(1); // id가 1(여기서는 board_id가 1)인 게시글 탐색
        log.info("findBoard={}", findBoard);          // 찾은 게시글 객체에 대한 정보를 로그로 출력
        assertThat(findBoard.getBoard_id()).isEqualTo(1);  // 찾은 게시글의 id가 1인지 확인(1이 아니면 예외 발생)
    }

    @Test
    void updateBoardTitle(){  // 게시글 제목 수정(Update)
        boardRepository.updateBoardTitle(1, "HaHa");
        // id가 1인 게시글의 제목을 "HaHa"로 수정
        Board updateBoard = boardRepository.findByBoardId(1);   // id가 1인 게시글 탐색
        log.info("updateBoard={}", updateBoard);          // 찾은 게시글 객체에 대한 정보를 로그로 출력
        assertThat(updateBoard.getTitle()).isEqualTo("HaHa");
        // 찾은 게시글의 제목이 HaHa인지 확인(HaHa가 아니면 에외 발생)
    }

    @Test
    void updateBoardContent(){  // 게시글 내용 수정(Update)
        boardRepository.updateBoardContent(1, "update clear~!");
        // id가 1인 게시글의 내용을 "update clear~!"로 수정
        Board updateBoard = boardRepository.findByBoardId(1);   // id가 1인 게시글 탐색
        log.info("updateBoard={}", updateBoard);          // 찾은 게시글 객체에 대한 정보를 로그로 출력
        assertThat(updateBoard.getContent()).isEqualTo("update clear~!");
        // 찾은 게시글의 내용이 위에서 적은 내용과 같은지 확인(위에서 적은 내용이 아니면 에외 발생)
    }

    @Test
    void deleteBoard(){  // 게시글 삭제(Delete)
        boardRepository.deleteBoard(1);   // id가 1인 게시글 삭제
        Board deleteBoard = boardRepository.findByBoardId(1);   // id가 1인 게시글 탐색
        assertThat(deleteBoard.getBoard_id()).isNull();    // 찾은 게시글의 id가 Null인지 확인(Null이 아니면 예외 발생)
    }
}