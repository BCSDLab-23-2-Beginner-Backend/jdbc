package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Getter, Setter, ToString 등을 자동으로 제공하는 Lombok 어노테이션
@NoArgsConstructor // parameter가 없는 기본 생성자를 자동으로 생성하는 Lombok 어노테이션
public class Board {
    private Integer id; // 게시글 ID
    private Integer user_id; // 사용자 ID
    private Integer category_id; // 카테고리 ID
    private String title; // 제목
    private String content; // 내용
    private LocalDateTime created_at; // 작성 시간

    // 생성자: 사용자 ID를 제외한 다른 ID는 직접 입력하는 형식이 아님
    public Board(Integer userid, String titles, String contents) {
        this.user_id = userid; // 사용자 ID 설정
        this.title = titles; // 제목 설정
        this.content = contents; // 내용 설정
    }
}
