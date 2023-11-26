package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Categories;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class CategoriesRepository {
    public Categories createCategories(Categories newCategories) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into categories(name) values(?)";//DB에 넘길 SQL 작성

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체

            statement.setString(1, newCategories.getName());//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.executeUpdate();
            return newCategories;
        } catch (SQLException e) {
            log.error("createCategories error={}", e);
            throw e;
        } finally {
            closeResources(connection, statement, null);//사용한 리소스 반환
        }
    }

    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        //반환할 때는 반드시 역순으로 반환해야 함.
        if(resultSet != null){
            try{
                resultSet.close();
            } catch (Exception e){
                log.error("Error closing ResultSet", e);
            }
        }
        if(statement != null){
            try{
                statement.close();
            } catch (Exception e){
                log.error("Error closing PreparedStatement", e);
            }
        }
        if(connection != null){
            try{
                connection.close();
            } catch (Exception e){
                log.error("Error closing Connection", e);
            }
        }
    }
}
