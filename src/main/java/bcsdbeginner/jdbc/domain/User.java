package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;         // 아이디
    private String username;    // 사용자 이름
    private String email;       // 사용자 이메일
    private String password;    // 사용자 비밀번호
    private LocalDateTime create_at;    // 만들어진 시간
}
