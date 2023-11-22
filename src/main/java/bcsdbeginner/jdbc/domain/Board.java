package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class Board {
    // community의 board table을 그대로 구현
    private Integer id;
    private Integer user_id;
    private Integer category_id;
    private String title;
    private String content;
    private LocalDateTime timestamp;

    // 제목, 글 내용, 사용자 아이디, 카테고리 아이디만으로 생성하는 생성자 작성
    // 게시글 아이디는 AI가 적용되어있어 따로 입력하지 않아도 된다고 생각했음.
    public Board(String newTitle, String newContent, Integer newUserId, Integer newCategoryId){
        this.title = newTitle;
        this.content = newContent;
        this.user_id = newUserId;
        this.category_id = newCategoryId;

        // timestamp는 현재시간 불러오기
        this.timestamp = LocalDateTime.now();
    }

}
