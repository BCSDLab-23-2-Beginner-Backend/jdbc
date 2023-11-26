package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class BoardRepository implements Repository<Board> {
    public Board create(Board newBoard) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into board(user_id, category_id, title, content) values(?, ?, ?, ?)";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, newBoard.getUser_id());
            statement.setInt(2, newBoard.getCategory_id());
            statement.setString(3, newBoard.getTitle());
            statement.setString(4, newBoard.getContent());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("createBoard error={}", e);
            throw e;
        } finally {
            DBConnectionManager.close(connection, statement, null);
        }

        return newBoard;
    }

    public Board findById(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            rs = statement.executeQuery();
            Board board = new Board();
            while (rs.next()) {
                board.setId(rs.getInt("id"));
                board.setUser_id(rs.getInt("user_id"));
                board.setCategory_id(rs.getInt("category_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                board.setCreated_at(LocalDateTime.parse(rs.getString("created_at"), format));
            }
            return board;
        } catch (SQLException e) {
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            DBConnectionManager.close(connection, statement, rs);
        }
    }

    public void update(Integer id, Board newBoard) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set category_id = ?, title = ?, content = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, newBoard.getCategory_id());
            statement.setString(2, newBoard.getTitle());
            statement.setString(3, newBoard.getContent());
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateBoard error={}", e);
            throw e;
        } finally {
            DBConnectionManager.close(connection, statement, null);
        }
    }

    public void delete(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteBoard error={}", e);
            throw e;
        } finally {
            DBConnectionManager.close(connection, statement, null);
        }
    }

}
