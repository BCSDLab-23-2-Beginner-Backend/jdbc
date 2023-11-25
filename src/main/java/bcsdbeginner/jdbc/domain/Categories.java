package bcsdbeginner.jdbc.domain;
import lombok.Data;

@Data
public class Categories {
    private Integer id;
    private String name;

    // id는 직접 기입 하지 않음
    public Categories(String names) {
        this.name = names;
    }
}
