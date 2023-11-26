package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Category;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
// category 데이터 삽입을 위해서 createCategory 메소드만 생성했습니다
public class CategoryRepository {
    public Category createCategory(Category newCategory) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into categories values(?, ?)"; // #1 카테고리 내에서 사용할 것은 id와 name으로 value는 2개

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, newCategory.getId()); // #2 각 ID와 name을 저장
            statement.setString(2, newCategory.getName());

            statement.executeUpdate();
            return newCategory;
        }
        catch (SQLException e)
        {
            log.error("createCategory error = {}", e);
            throw e;
        }
        finally
        {
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
