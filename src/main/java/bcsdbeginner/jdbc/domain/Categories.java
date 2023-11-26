package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Categories {
    private Integer id;
    private String name;

    public Categories(String name) {
        this.name = name;
    }
}
