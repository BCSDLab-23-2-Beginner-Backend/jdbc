package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer user_num;
    private String user_id;
    private String user_name;
    private String user_pw;
    private String user_email;

    public User(String userid, String username, String password, String email){
        this.user_id =  userid;
        this.user_name = username;
        this.user_pw = password;
        this.user_email = email;
    }
}
