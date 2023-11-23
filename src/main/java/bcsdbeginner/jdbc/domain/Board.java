package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Getter, Setter, ToString 등을 자동 으로 제공
@NoArgsConstructor // parameter 가 없는 기본 생성자 를 자동 으로 생성
public class Board {
    private Integer id;
    private Integer user_id;
    private Integer category_id;
    private String title;
    private String content;
    private LocalDateTime timestamp;

    // 유저 id를 제외한 다른 id는 직접 입력 하는 형식이 아님
    public Board(Integer userid, String titles, String contents, LocalDateTime timestamps) {
        this.user_id = userid;
        this.title = titles;
        this.content = contents;
        this.timestamp = timestamps;

    }
}
