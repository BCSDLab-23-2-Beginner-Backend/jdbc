package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Categories;
import bcsdbeginner.jdbc.domain.Hashtags;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class HashtagRepository {
    public Integer save(Hashtags hashtag) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;     // 기본 제공
        String sql = "insert into hashtags(name) values(?)";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            log.info("statement = {}", statement);

            statement.setString(1, hashtag.getName());

            statement.executeUpdate();

            return hashtag.getId();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, null);
        }
    }

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;     // 기본 제공
        String sql = "delete from hashtags where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            log.info("statement = {}", statement);

            statement.setInt(1, id);

            statement.executeUpdate();


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, null);
        }
    }

    public Integer update(Integer id, String column, String data) {
        Connection connection = null;
        PreparedStatement statement = null;     // 기본 제공
        String sql = "";

        try {
            if (column.equals("name")) {
                sql = "update hashtags set name = ? where id = ?";
            } else throw new Exception();

            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            log.info("statement = {}", statement);

            statement.setString(1, data);
            statement.setInt(2, id);

            statement.executeUpdate();

            return id;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, null);
        }
    }

    public void read(){
        Connection connection = null;
        PreparedStatement statement = null;     // 기본 제공
        String sql = "select * from hashtag";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            log.info("statement = {}", statement);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResources(connection, statement, null);
        }
    }
    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error("resultSet close error", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                log.error("statement close error", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("connection close error", e);
            }
        }
    }
}
