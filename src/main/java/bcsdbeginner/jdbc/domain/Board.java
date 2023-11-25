package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private Integer id;
    private String title;
    private String content;
    private Integer user_id;
    private Integer category_id;
    private LocalDateTime created_at;

    public Board(String title, String content, Integer user_id, Integer category_id){
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;
    }
}