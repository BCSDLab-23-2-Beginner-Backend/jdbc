package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

// #1 테이블 생성 시 categories의 외래키가 없으면 작동이 안되어서
// categories를 만들어서 진행했습니다
public class Category {
    private Integer id;
    private String name;
}
