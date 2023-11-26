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

@Slf4j
public class UserRepository {
    public Integer save(User user){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into users values(?,?,?,?,?)";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getCreate_at().toString());
            statement.executeUpdate();

            return user.getId();
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public User findById(Integer id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from users where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            User findUser = new User();
            while (resultSet.next()){
                findUser.setId(resultSet.getInt("id"));
                findUser.setUsername(resultSet.getString("username"));
                findUser.setEmail(resultSet.getString("email"));
                findUser.setPassword(resultSet.getString("password"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                findUser.setCreate_at(LocalDateTime.parse(resultSet.getString("created_at"), formatter));
            }
            return findUser;
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public void updateUsername(Integer id, String username){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update users set username = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, username);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public void deleteUser(Integer id){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from users where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1,id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
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
                log.error("Error closing resultSet", e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                log.error("Error closing PrepareStatement", e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error("Error closing Connection", e);
            }
        }
    }
}
