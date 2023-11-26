package bcsdbeginner.jdbc.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
    private Integer id;
    private Integer user_id;
    private Integer category_id;
    private String title;
    private String content;
    private LocalDateTime created_at;

    public Board(Integer user_id, Integer category_id, String title, String content) {
        this.user_id = user_id;
        this.category_id = category_id;
        this.title = title;
        this.content = content;

        this.created_at = LocalDateTime.now();
    }

    //public Integer getId() {
    //    return id;
    //}
//
    //public Integer getUser_id() {
    //    return user_id;
    //}
//
    //public Integer getCategory_id() {
    //    return category_id;
    //}
//
    //public String getTitle() {
    //    return title;
    //}
//
    //public String getContent() {
    //    return content;
    //}
//
    //public LocalDateTime getCreated_at() {
    //    return created_at;
    //}
    //
    //public void setId(int user_id){
    //    this.user_id = user_id;
    //}
}
