package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Board {
    private Integer id;
    private Integer user_id;
    private Integer category_id;
    private String title;
    private String content;
    private LocalDateTime create_at;

    public Board(Integer user_id, Integer category_id, String title, String content){
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;
    }
}