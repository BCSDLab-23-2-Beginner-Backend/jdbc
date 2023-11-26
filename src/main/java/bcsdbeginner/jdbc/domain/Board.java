package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// lombok 라이브러리에서 제공하는 어노테이션. 클래스의 필드의 getterm setter 등 각종 메서드를 제공
@Data
// lombok 라이브러리에서 제공하는 어노테이션. 파라미터가 없는 기본 생성자를 자동 생성
@NoArgsConstructor

public class Board {
    // sql로 작성된 코드를 보고 데이터 타입 설정
    // null이 될 수 있으므로 Wrapper 클래스 사용
    private Integer id;
    private Integer user_id;
    private Integer category_id;
    private String title;
    private String content;
    private LocalDateTime created_at;

    // id, created_at을 제외하고 생성.
    public Board(Integer user_id, Integer category_id, String title, String content) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;
    }
}


