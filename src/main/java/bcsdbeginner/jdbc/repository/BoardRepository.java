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
    public Integer save(Board board){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into board values(?,?,?,?,?,?)";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, board.getId());
            statement.setInt(2, board.getUser_id());
            statement.setInt(3, board.getCategory_id());
            statement.setString(4, board.getTitle());
            statement.setString(5, board.getContent());
            statement.setString(6, board.getCreated_at().toString());
            statement.executeUpdate();
            return board.getId();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            closeResources(connection, statement, null);
        }
    }

    public Board findById(Integer id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from board where id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            Board findBoard = new Board();
            while(resultSet.next()){
                findBoard.setId(resultSet.getInt("id"));
                findBoard.setUser_id(resultSet.getInt("user_id"));
                findBoard.setCategory_id(resultSet.getInt("category_id"));
                findBoard.setTitle(resultSet.getString("title"));
                findBoard.setContent(resultSet.getString("content"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                findBoard.setCreated_at(LocalDateTime.parse(resultSet.getString("created_at"), formatter));
            }
            return findBoard;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            closeResources(connection, statement, null);
        }
    }

    public void updateContent(Integer id, String content){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "update board set content = ? where id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, content);
            statement.setInt(2, id);

            statement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            closeResources(connection, statement, null);
        }
    }

    public void deleteBoard(Integer id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "delete from board where id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            statement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            closeResources(connection, statement, null);
        }
    }
    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if(resultSet != null){
            try{
                resultSet.close();
            }catch(Exception e){
                log.error("Error closing ResultSet", e);
            }
        }
        if(statement != null){
            try{
                statement.close();
            }catch(Exception e){
                log.error("Error closing PreparedStatement", e);
            }
        }
        if(connection != null){
            try{
                connection.close();
            }catch(Exception e){
                log.error("Error closing Connection", e);
            }
        }
    }
}
