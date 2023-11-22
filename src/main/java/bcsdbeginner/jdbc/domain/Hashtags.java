package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Hashtags {
    // community의 hashtags table 그대로 구현
    private Integer id;
    private String name;
    
    // 이름만 입력하는 생성자 작성
    // board와 마찬가지로 id에 AI가 적용되어있어 id입력이 불필요하다고 느낌
    public Hashtags(String newName){
        this.id = null;
        this.name = newName;
    }
}
