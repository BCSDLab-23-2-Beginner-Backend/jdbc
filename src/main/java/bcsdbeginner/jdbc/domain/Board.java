package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private Integer user_num;
    private String title;
    private String content;
    private Integer board_num;
    private Timestamp board_day;

    // public User(String username, String email, String password){
    //     this.username = username;
    //     this.email = email;
    //    this.password = password;
    //}
}