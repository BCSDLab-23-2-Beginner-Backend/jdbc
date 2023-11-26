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

    // 일단 카테고리를 만드는게 목적이라서 create만 만들었다
    public Categories createCategories(Categories newCategories) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into categories(name) values(?)";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            // newCategories에서 name을 가져와 ?에 바인딩
            statement.setString(1, newCategories.getName());
            statement.executeUpdate();
            return newCategories;
        } catch (SQLException e) {
            log.error("createCategories error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);
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
