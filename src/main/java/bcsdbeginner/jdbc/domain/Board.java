package bcsdbeginner.jdbc.domain;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
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

    public Board(String title, String content) { // Title과 Content만 CRUD 할 예정.
        this.title = title;
        this.content = content;
    }

}
