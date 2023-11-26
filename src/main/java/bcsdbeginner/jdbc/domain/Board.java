package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board { // 커뮤니티의 게시판 객체를 만들기 위한 클래스
    private Integer id;
    private Integer user_id;
    private Integer category_id;
    private String title;
    private String content;
    private LocalDateTime created_at;

    public Board(Integer user_id, Integer category_id, String title, String content) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;
    }
}
