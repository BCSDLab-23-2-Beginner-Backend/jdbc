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
public class BoardRepository {

    //게시물 만들기
    public Board createBoard(Board newBoard) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into board(user_id, category_id, title, content) values(?, ? ,?, ?)";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, newBoard.getUser_id());
            statement.setInt(2, newBoard.getCategory_id());
            statement.setString(3, newBoard.getTitle());
            statement.setString(4, newBoard.getContent());

            statement.executeUpdate();
            return newBoard;
        }catch (SQLException e){
            log.error("createBoard error={}", e);
            throw e;
        }finally {
            closeResource(connection, statement, null);
        }
    }

    //게시물 id로 게시물 찾기
    public Board findBoardById(Integer boardId) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, boardId);

            rs = statement.executeQuery();
            Board board = new Board();
            while(rs.next()){
                board.setId(rs.getInt("id"));
                board.setUser_id(rs.getInt("user_id"));
                board.setCategory_id(rs.getInt("category_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                board.setCreate_at(LocalDateTime.parse(rs.getString("created_at"),format));
            }
            return board;
        }catch (SQLException e){
            log.error("createBoard error={}", e);
            throw e;
        }finally {
            closeResource(connection, statement, null);
        }
    }

    //사용자 id로 게시물 찾기
    public Board findBoardByUserId(Integer userId) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where user_id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, userId);

            rs = statement.executeQuery();
            Board board = new Board();
            while(rs.next()){
                board.setId(rs.getInt("id"));
                board.setUser_id(rs.getInt("user_id"));
                board.setCategory_id(rs.getInt("category_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                board.setCreate_at(LocalDateTime.parse(rs.getString("created_at"),format));
            }
            return board;
        }catch (SQLException e){
            log.error("createBoard error={}", e);
            throw e;
        }finally {
            closeResource(connection, statement, null);
        }
    }

    //카테고리 id로 게시물 찾기
    public Board findBoardByCategoryId(Integer categoryId) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where category_id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, categoryId);

            rs = statement.executeQuery();
            Board board = new Board();
            while(rs.next()){
                board.setId(rs.getInt("id"));
                board.setUser_id(rs.getInt("user_id"));
                board.setCategory_id(rs.getInt("category_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                board.setCreate_at(LocalDateTime.parse(rs.getString("created_at"),format));
            }
            return board;
        }catch (SQLException e){
            log.error("createBoard error={}", e);
            throw e;
        }finally {
            closeResource(connection, statement, null);
        }
    }

    //게시물 제목 수정
    public void updateBoardTitle(Integer id, String newTitle) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set title = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, newTitle);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    //게시물 내용 수정
    public void updateBoardContent(Integer id, String newContent) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set content = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, newContent);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    //게시물 삭제
    public void deleteBoard(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, id);//DB컬럼과 자바 오브젝트 필드 바인딩

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet) {
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