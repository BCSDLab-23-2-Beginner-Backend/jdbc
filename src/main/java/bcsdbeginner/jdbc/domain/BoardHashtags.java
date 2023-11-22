package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardHashtags {
    // community의 board_hashtags table 그대로 구현
    private Integer board_id;
    private Integer hashtag_id;

}
