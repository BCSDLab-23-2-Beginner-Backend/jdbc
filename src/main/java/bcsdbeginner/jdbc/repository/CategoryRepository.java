package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Category;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Slf4j
public class CategoryRepository {
    public Category createCategory(Category newCategory) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into categories(name) values(?)";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, newCategory.getName());

            statement.executeUpdate();

            return newCategory;
        } catch (SQLException e){
            log.error("createCategory error={}", e);
            throw e;
        } finally{
            closeResource(connection, statement);
        }
    }

    private void closeResource(Connection connection, PreparedStatement statement) {
        if(statement != null){
            try{
                statement.close();
            }catch (Exception e){
                log.error("error", e);
            }
        }

        if(connection != null){
            try{
                connection.close();
            }catch (Exception e){
                log.error("error", e);
            }
        }
    }
}
