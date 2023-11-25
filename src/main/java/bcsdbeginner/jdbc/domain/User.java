package bcsdbeginner.jdbc.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // 클래스에 대한 여러 메서드를 한 번에 생성해주는 어노테이션 (toString, equals, hashCode, getter, setter)
@NoArgsConstructor // 매개변수가 없는 기본생성자를 생성해주는 어노테이션
public class User { // DB의 user테이블
    private Integer id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime create_at;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
