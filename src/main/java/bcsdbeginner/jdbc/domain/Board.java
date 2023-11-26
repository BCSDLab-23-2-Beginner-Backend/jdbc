package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data // #1 각 Getter, Setter를 자동으로 생성해준다
@NoArgsConstructor
@AllArgsConstructor

public class Board { // #2 각 속성에 맞게 Board 내의 변수 정의
    private Integer id;
    private Integer user_id;
    private Integer category_id;
    private String title;
    private String content;
    private LocalDateTime create_at;
}








