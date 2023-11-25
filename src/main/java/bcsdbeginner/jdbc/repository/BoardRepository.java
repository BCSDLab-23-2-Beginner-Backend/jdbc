package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class BoardRepository {
    public Board createBoard(Board newBoard) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "insert into board(user_id, category_id, title, content) values(?, ?, ?, ?)";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, newBoard.getUser_id());
            statement.setInt(2, newBoard.getCategory_id());
            statement.setString(3, newBoard.getTitle());
            statement.setString(4, newBoard.getContent());

            statement.executeUpdate();

            rs = statement.getGeneratedKeys();
            if(rs.next()){
                newBoard.setId(rs.getInt(1));
            }

            return newBoard;
        }catch (SQLException e){
            log.error("createBoard error={}", e);
            throw e;
        }finally {
            closeResource(connection, statement, rs);
        }
    }

    public Board findById(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            rs = statement.executeQuery();
            Board board = new Board();
            if(rs.next()){
                board.setId(rs.getInt("id"));
                board.setUser_id(rs.getInt("user_id"));
                board.setCategory_id(rs.getInt("category_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                board.setCreated_at(LocalDateTime.parse(rs.getString("created_at"), formatter));
            }
            return board;
        }catch (SQLException e){
            log.error("findById error={}", e);
            throw e;
        }finally{
            closeResource(connection, statement, rs);
        }
    }

    public void updateBoard(Integer id, Integer newCategoryId, String newTitle, String newContent) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set category_id = ?, title = ?, content = ? where id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, newCategoryId);
            statement.setString(2, newTitle);
            statement.setString(3, newContent);
            statement.setInt(4, id);

            statement.executeUpdate();
        }catch (SQLException e){
            log.error("updateBoard error={}", e);
            throw e;
        }finally{
            closeResource(connection, statement, null);
        }
    }

    public void deleteBoard(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            statement.executeUpdate();
        }catch (SQLException e){
            log.error("deleteBoard error={}", e);
            throw e;
        }finally {
            closeResource(connection, statement, null);
        }
    }

    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if(resultSet != null){
            try{
                resultSet.close();
            } catch (Exception e) {
                log.error("closeResource resultSet error", e);
            }
        }

        if(statement != null){
            try{
                statement.close();
            }catch (Exception e){
                log.error("closeResource statement error", e);
            }
        }

        if(connection != null){
            try{
                connection.close();
            }catch (Exception e){
                log.error("closeResource connection error", e);
            }
        }
    }

}
