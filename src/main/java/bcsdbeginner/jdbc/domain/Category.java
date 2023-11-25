package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {
    private Integer id;
    private String name;

    public Category(String name){
        this.name = name;
    }
}
