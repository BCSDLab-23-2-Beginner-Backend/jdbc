package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hashtag {
    private Integer hst_id;
    private String hst_name;
    private Integer board_num;
    private Integer user_num;

}