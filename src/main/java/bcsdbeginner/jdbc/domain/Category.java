package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Integer ctg_id;
    private String ctg_name;
    private Integer board_num;
    private Integer user_num;

}