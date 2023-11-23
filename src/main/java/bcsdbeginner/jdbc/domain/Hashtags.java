package bcsdbeginner.jdbc.domain;

import lombok.Data;

@Data
public class Hashtags {
    private Integer id;
    private String name;

    // id는 직접 기입 하지 않음
    public Hashtags(String names) {
        this.name = names;
    }
}

