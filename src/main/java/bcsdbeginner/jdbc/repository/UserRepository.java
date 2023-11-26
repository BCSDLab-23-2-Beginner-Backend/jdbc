package bcsdbeginner.jdbc.repository;


import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import bcsdbeginner.jdbc.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class UserRepository {
    public User createUser(User newUser) throws SQLException {//사용자 생성
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into User values(null, ?, ?, ?, ?)";//DB에 넘길 SQL 작성

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            //DB컬럼과 자바 오브젝트 필드 바인딩
            //statement.setInt(1, newUser.getUser_num());
            statement.setString(1, newUser.getUser_id());//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setString(2, newUser.getUser_name());
            statement.setString(3, newUser.getUser_pw());
            statement.setString(4, newUser.getUser_email());
            statement.executeUpdate();
            return newUser;

        } catch (SQLException e) {
            log.error("createUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public void updateUser(User updateUser) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update User set user_name = ? , user_pw = ?, user_email = ? WHERE user_id = ?";//DB에 넘길 SQL 작성
        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            //DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setString(1, updateUser.getUser_name());
            statement.setString(2, updateUser.getUser_pw());
            statement.setString(3, updateUser.getUser_email());
            statement.setString(4, updateUser.getUser_id());

            statement.executeUpdate();


        } catch (SQLException e) {
            log.error("updateUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public User findById(String userId) throws SQLException { //아이디 조회
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from User where user_id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, userId);

            rs = statement.executeQuery();
            User user = new User();
            while (rs.next()) {
                user.setUser_num(rs.getInt("user_num"));
                user.setUser_id(rs.getString("user_id"));
                user.setUser_name(rs.getString("user_name"));
                user.setUser_pw(rs.getString("user_pw"));
                user.setUser_email(rs.getString("user_email"));
            }
            return user;
        } catch (SQLException e) {
            log.error("selectUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs);
        }
    }

    public void deleteUser(Integer userNum) throws SQLException {//회원 탈퇴
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from User where user_num = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, userNum);//DB컬럼과 자바 오브젝트 필드 바인딩

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
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