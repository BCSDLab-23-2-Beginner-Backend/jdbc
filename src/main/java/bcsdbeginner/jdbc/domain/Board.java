package bcsdbeginner.jdbc.domain;

import lombok.NoArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data // 클래스에 대한 여러 메서드를 한 번에 생성해주는 어노테이션 (toString, equals, hashCode, getter, setter)
@NoArgsConstructor // 매개변수가 없는 기본생성자를 생성해주는 어노테이션
public class Board { // DB의 board테이블
    private Integer id;
    private int user_id;
    private int category_id;
    private String title;
    private String content;
    private LocalDateTime created_at;

    public Board(int user_id, int category_id, String title, String content) { //Board 테이블 생성자
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;
    }
}