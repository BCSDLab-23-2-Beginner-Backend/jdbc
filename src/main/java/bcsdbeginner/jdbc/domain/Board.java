package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private Integer board_id;   // 게시글 번호(아이디)
    private Integer user_id;    // 작성자 아이디
    private Integer category_id;       // 게시글 카테고리
    private String title;       // 게시글 제목
    private String content;     // 게시글 내용
    private LocalDateTime create_at;    // 만들어진 시간
}
