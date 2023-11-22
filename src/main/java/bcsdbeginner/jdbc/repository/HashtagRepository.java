package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Hashtags;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Slf4j
public class HashtagRepository {

    // 해시태그 아이디를 반환하는 함수
    // board와 같은 이유로 private로 선언
    private Integer getHashtagId(Hashtags hashtag) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        // name으로 검색하여 id를 반환하는 쿼리문 작성
        String sqlBoardId = "SELECT `id` FROM `hashtags` WHERE `name` = ?";
        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sqlBoardId);

            statement.setString(1, hashtag.getName()); // name에 대응하는 값 입력

            result = statement.executeQuery();
            Integer resHashtagId = null; // 결과 객체 생성
            while(result.next()){
                resHashtagId = result.getInt("id"); // 결과 객체에 값 저장
            }

            return resHashtagId; // 결과객체 반환

        } catch (SQLException e){

            log.error("getHashtagId error={}", e); // Exception 발생 시 로그
            throw e;

        } finally {
            closeResources(connection, statement, result); // 자원 반환
        }
    }

    // 새로운 해시태그 생성하는 함수: Create
    public Hashtags CreateHashtags(Hashtags newHashtags) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // Category와 마찬가지로 이미 있는 해시태그의 경우 추가하지 않고, 존재하지 않는 해시태그만 추가.
        // id는 AI가 적용되어 있기 때문에 따로 입력 x
        String sqlHashtags = "INSERT INTO `hashtags`(`name`) SELECT ? FROM DUAL WHERE NOT EXISTS(SELECT `name` FROM `hashtags` WHERE `name` = ?)";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sqlHashtags);

            statement.setString(1, newHashtags.getName()); // name값 입력
            statement.setString(2, newHashtags.getName()); // name값 입력

            statement.executeUpdate(); // 쿼리 실행
            newHashtags.setId(getHashtagId(newHashtags)); // newHashtags에 id값 set하고
            return newHashtags; // 반환

        } catch (SQLException e) {

            log.error("CreateHashtags error={}", e); // Exception 발생 시 로그
            throw e;

        } finally {
            closeResources(connection, statement, null); // 자원 반환
        }
    }

    // 모든 해시태그를 반환하는 함수: Read
    public ArrayList<Hashtags> findAll() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // 모든 해시태그를 선택하는 쿼리문 작성
        String sql = "select * from hashtags";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            resultset = statement.executeQuery(); // 쿼리 실행
            ArrayList<Hashtags> resHashtags = new ArrayList<>(); // 여러개의 값을 리턴해야하기 때문에 ArrayList 활용
            while(resultset.next()){
                Hashtags hashtag_tmp = new Hashtags(); // 임시 객체 생성
                hashtag_tmp.setName(resultset.getString("name")); // set name
                hashtag_tmp.setId(resultset.getInt("id")); // set id
                resHashtags.add(hashtag_tmp); // resHashtags에 add
            }

            return resHashtags; // resHashtags 반환
        } catch (SQLException e){
            log.error("Hashtags.findAll={}", e); // Exception 발생 시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환
        }
    }

    // 특정 id를 가진 해시태그 반환: Read
    public Hashtags findbyId(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // 특정 id를 조건으로 준 쿼리문 작성
        String sql = "select * from hashtags where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id); // id에 대응하는 값 입력

            resultset = statement.executeQuery(); // 쿼리 실행
            Hashtags resHashtags = new Hashtags(); // 결과 객체 생성
            while(resultset.next()){
                resHashtags.setName(resultset.getString("name")); // set name
                resHashtags.setId(resultset.getInt("id")); // set id
            }

            return resHashtags; // 결과 객체 반환
        } catch (SQLException e){
            log.error("Hashtags.findbyId={}", e); // Exception 발생 시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환
        }
    }

    // 특정 id를 가진 해시태그 이름 변경: Update
    public void updateHashtagname(Integer id, String newHashtagname) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // 특정 id를 가진 해시태그 name변경 쿼리 작성
        String sql = "update hashtags set name = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, newHashtagname); // name에 대응하는 값 입력
            statement.setInt(2, id); // id에 대응하는 값 입력

            statement.executeUpdate(); // 쿼리 실행
        } catch (SQLException e) {
            log.error("updateHashtag error={}", e); // Exception 발생 시 로그
            throw e;
        } finally {
            closeResources(connection, statement, null); // 자원 반환
        }
    }

    // 특정 id를 가진 해시태그 제거: Delete
    public void DeleteHashtag(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        // delete문 작성
        String sql = "delete from hashtags where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id); // id에 대응하는 값 입력

            statement.executeUpdate(); // 쿼리 실행
        } catch (SQLException e) {
            log.error("deleteHashtag error={}", e); // Exception 발생 시 로그
            throw e;
        } finally {
            closeResources(connection, statement, null); // 자원 반환
        }
    }

    // 자원 반환하는 함수
    // 반드시 ResultSet, PreparedStatement, Connection 순서대로 반환해야한다!
    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultset) {
        if (resultset != null){ // resultset 객체가 있는 경우
            try{
                resultset.close();
            } catch (SQLException e) {
                log.error("resultset close error");
            }
        }

        if (statement != null){ // statement 객체가 있는 경우
            try{
                statement.close();
            } catch (SQLException e) {
                log.error("statement close error");
            }
        }

        if (connection != null){ // connection 객체가 있는 경우
            try{
                connection.close();
            } catch (SQLException e) {
                log.error("connection close error");
            }
        }

    }
}
