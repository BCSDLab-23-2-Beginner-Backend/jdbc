package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Categories {
    private Integer id;
    private String name;
    private LocalDateTime create_at;

    public Categories(String name) {
        this.name = name;
    }
}
