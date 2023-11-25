package bcsdbeginner.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class BoardHashtags {
    private Integer board_id;
    private Integer hashtag_id;
}
