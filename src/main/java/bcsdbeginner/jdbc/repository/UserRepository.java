package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
public class UserRepository {
    public User createUser(User newUser) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into users(username, email, password) values(?, ?, ?)";//DB에 넘길 SQL 작성

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newUser.getUsername());//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setString(2, newUser.getEmail());
            statement.setString(3, newUser.getPassword());

            statement.executeUpdate();
            newUser.setId(getUserId(newUser));
            return newUser;

        } catch (SQLException e) {
            log.error("createUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    // 추가된 부분
    // 모든 유저 리스트 반환: Read
    public ArrayList<User> findAll() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // user의 모든 값을 선택하는 쿼리문 작성
        String sql = "select * from users";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            resultset = statement.executeQuery();
            ArrayList<User> resUser = new ArrayList<>(); // user객체를 저장할 arraylist 생성
            while(resultset.next()){
                User hashtag_tmp = new User(); // user 객체 생성
                hashtag_tmp.setId(resultset.getInt("id")); // set id
                hashtag_tmp.setUsername(resultset.getString("username")); // set username
                hashtag_tmp.setEmail(resultset.getString("email")); // set email
                hashtag_tmp.setPassword(resultset.getString("password")); // set passwd
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // formatter 생성
                hashtag_tmp.setCreate_at(LocalDateTime.parse(resultset.getString("created_at"), format)); // set create_at
                resUser.add(hashtag_tmp); // resUser에 add
            }

            return resUser; // resUser 반환
        } catch (SQLException e){
            log.error("Hashtags.findAll={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResource(connection, statement, resultset); // 자원 반환
        }
    }

    public User findById(Integer userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from users where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            rs = statement.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                user.setCreate_at(LocalDateTime.parse(rs.getString("created_at"), format));
            }
            return user;
        } catch (SQLException e) {
            log.error("selectUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs);//사용한 리소스 반환
        }
    }

    public void updateUsername(Integer id, String newUsername) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update users set username = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newUsername);//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public void deleteUser(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from users where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, id);//DB컬럼과 자바 오브젝트 필드 바인딩

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    // 추가 부분
    // private로 user의 id를 반환하는 함수 작성
    // board와 동일한 이유로 private로 선언
    private Integer getUserId(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        // username과 email을 이용하여 id를 선택하는 쿼리 작성
        String sqlBoardId = "SELECT `id` FROM `users` WHERE `username` = ? AND `email` = ?";
        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sqlBoardId);

            statement.setString(1, user.getUsername()); // username에 대응하는 값 입력
            statement.setString(2, user.getEmail()); // email에 대응하는 값 입력

            result = statement.executeQuery(); // 쿼리 실행
            Integer resUserId = null; // 반환 객체 생성
            while(result.next()){
                resUserId = result.getInt("id"); // 반환 객체에 값 저장
            }

            return resUserId; // 결과값 반환

        } catch (SQLException e){

            log.error("getCategoryId error={}", e); // Exception 발생 시 로그
            throw e;

        } finally {
            closeResource(connection, statement, result); // 자원 반환
        }
    }

    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        //반환할 때는 반드시 역순으로 반환해야 함.
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }
}
