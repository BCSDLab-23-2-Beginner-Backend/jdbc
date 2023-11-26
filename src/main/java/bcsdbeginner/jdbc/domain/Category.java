package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {
    Integer id;
    String name;

    public Category(String name) {
        this.name = name;
    }
}
