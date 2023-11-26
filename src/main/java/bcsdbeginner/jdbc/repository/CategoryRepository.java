package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Category;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class CategoryRepository implements Repository<Category> {
    @Override
    public Category create(Category newCategory) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into categories(name) values (?)";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, newCategory.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("createCategory error={}", e);
            throw e;
        } finally {
            DBConnectionManager.close(connection, statement, null);
        }

        return newCategory;
    }

    @Override
    public Category findById(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from categories where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            rs = statement.executeQuery();
            Category category = new Category();
            while (rs.next()) {
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
            }
            return category;
        } catch (SQLException e) {
            log.error("selectCategory error={}", e);
            throw e;
        } finally {
            DBConnectionManager.close(connection, statement, rs);
        }
    }

    @Override
    public void update(Integer id, Category newCategory) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update categories set name = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, newCategory.getName());
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateCategory error={}", e);
            throw e;
        } finally {
            DBConnectionManager.close(connection, statement, null);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from categories where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteCategory error={}", e);
            throw e;
        } finally {
            DBConnectionManager.close(connection, statement, null);
        }
    }
}
