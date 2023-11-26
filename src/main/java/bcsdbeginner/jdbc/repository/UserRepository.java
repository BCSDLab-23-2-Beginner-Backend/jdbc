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
        // sql 쿼리문 정의

        try {
            connection = DBConnectionManager.getConnection();
            //DriverManger 통해서 DB커넥션 생성(DB 연결)
            statement = connection.prepareStatement(sql);
            //SQL 실행하기 위한 객체 PrepareStatement 객체 생성

            // 유저의 데이터(id, name, email, password, create_at)를 statement에 바인딩
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getCreate_at().toString());
            statement.executeUpdate();  // SQL 쿼리 실행

            return user.getId();    // 유저의 id 반환
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    public User findById(Integer id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from users where id = ?";
        // SQL 쿼리문

        try {
            connection = DBConnectionManager.getConnection();
            //DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);
            //SQL 실행하기 위한 객체 PrepareStatement 생성

            statement.setInt(1, id);
            // 첫번째 인자에 id 값 설정
            resultSet = statement.executeQuery();
            // SQL 쿼리 실행 결과를 resultSet 객체에 저장

            User findUser = new User();
            while (resultSet.next()){   // resultSet의 결과를 하나씩 넘어가며 반복
                // resultSet의 각 열에 해당하는 값을 가져와서 findUser 객체에 저장
                findUser.setId(resultSet.getInt("id"));
                findUser.setUsername(resultSet.getString("username"));
                findUser.setEmail(resultSet.getString("email"));
                findUser.setPassword(resultSet.getString("password"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 날짜 형식 지정을 위한 객체 생성
                findUser.setCreate_at(LocalDateTime.parse(resultSet.getString("created_at"), formatter));
                // create_at 열의 값을 가져와 LocalDateTime 객체로 변환 후 findUser 객체에 저장
            }
            return findUser;    // 생성된 findUser 객체 반환
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    public void updateUsername(Integer id, String username){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update users set username = ? where id = ?";
        // SQL 쿼리문

        try {
            connection = DBConnectionManager.getConnection();
            //DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);
            //SQL 실행하기 위한 객체 PrepareStatement 생성

            // statement 객체의 첫번째 인자와 두번째 인자에 username과 id 설정
            statement.setString(1, username);
            statement.setInt(2, id);

            statement.executeUpdate();  // 쿼리문 실행
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    public void deleteUser(Integer id){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from users where id = ?";
        // SQL 쿼리문

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1,id);
            // statement 첫번째 인자에 id 설정

            statement.executeUpdate();
            // 쿼리문 실행

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        //반환할 때는 반드시 역순으로 반환해야 함.

        // resultSet, statement, connection 순서로 리소스 반환
        // 각 리소스 반환 시 오류가 발생하면 로그에 error를 던짐
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
