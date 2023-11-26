package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import bcsdbeginner.jdbc.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class BoardRepository {

    public Board save(Board board) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into board(user_id, category_id, title, content) values(?,?,?,?)";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, board.getUser_id());
            statement.setInt(2, board.getCategory_id());
            statement.setString(3, board.getTitle());
            statement.setString(4, board.getContent());

            statement.executeUpdate();

            return board;
        } catch (SQLException e) {
            log.error("createBoard error={}", e);
            throw e;
        } finally {
            closeResources(connection, statement, null);//사용한 리소스 반환
        }
    }

    public Board findById(Integer userId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where id = ?";

        int user_id = 0;
        int category_id = 0;
        String title = null;
        String content = null;
        LocalDateTime localDateTime = null;
        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            rs = statement.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("id");
                category_id = rs.getInt("category_id");
                title = rs.getString("title");
                content = rs.getString("content");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                localDateTime = LocalDateTime.parse(rs.getString("created_at"), format);
            }

            return new Board(user_id, category_id, title, content);
        } catch (SQLException e) {
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            closeResources(connection, statement, rs);//사용한 리소스 반환
        }
    }

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;     // 기본 제공
        String sql = "delete from board where id = ?";

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

    public Integer update(int id, String column, String data) {
        Connection connection = null;
        PreparedStatement statement = null;     // 기본 제공
        String sql = "";

        try {
            if (column.equals("title")) {
                sql = "update board set title = ? where id = ?";
            } else if (column.equals("content")) {
                sql = "update board set content = ? where id = ?";
            } else throw new Exception();    // user의 id와 created_at은 변경할 수 없다고 가정

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
        String sql = "select * from board";

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
