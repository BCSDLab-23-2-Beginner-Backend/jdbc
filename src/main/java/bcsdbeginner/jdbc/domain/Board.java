package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Board {
    private Long id;
    private Long user_id;
    private Long category_id;
    private String title;
    private String content;
    private LocalDateTime created_at;

    public Board(Long user_id, Long category_id, String title, String content){
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;
    }
}