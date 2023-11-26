package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class BoardRepository {
    public Integer createBoard(Board newBoard) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "insert into board(id, user_id, category_id, title, content) values(null, ?, ?, ?, ?)";//DB에 넘길 SQL 작성

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, newBoard.getUser_id());//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setInt(2, newBoard.getCategory_id());
            statement.setString(3, newBoard.getTitle());
            statement.setString(4, newBoard.getContent());

            statement.executeUpdate();

            statement = connection.prepareStatement("select max(id) from board");// 새로 입력된 board의 id
            resultSet = statement.executeQuery();

            return resultSet.next()? resultSet.getInt(1): null;
        } catch (SQLException e) {
            log.error("createBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public Board findById(Integer boardId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, boardId);

            resultSet = statement.executeQuery();
            Board board = new Board();
            while (resultSet.next()) {
                board.setId(resultSet.getInt("id"));
                board.setUser_id(resultSet.getInt("user_id"));
                board.setCategory_id(resultSet.getInt("category_id"));
                board.setTitle(resultSet.getString("title"));
                board.setContent(resultSet.getString("content"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime create_at = LocalDateTime.parse(resultSet.getString("created_at"), format);
                board.setCreate_at(create_at);
            }
            return board; // boardId를 이용해서 게시글이 있는지 찾고 해당 게시글에 대한 정보를 리턴
        } catch (SQLException e) {
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, resultSet);//사용한 리소스 반환
        }
    }
    public void updateBoard(Integer boardId, Integer userId, Integer categoriesId, String title, String content) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        // 원래의 값을 유지하고 싶으면 null을 아니라면 변경하고 싶은 값을 전달하여 query문을 작성
        String userIdSql = (userId == null)? " user_id = user_id, ": " user_id = ?, ";
        String categoriesIdSql = (categoriesId == null)? " category_id = category_id, ": " category_id = ?, ";
        String titleSql = (title == null)? " title = title, ": " title = ?, ";
        String contentSql = (content == null)? " content = content ": " content = ? ";
        StringBuilder sql = new StringBuilder();
        sql.append("update board set");
        sql.append(userIdSql);
        sql.append(categoriesIdSql);
        sql.append(titleSql);
        sql.append(contentSql);
        sql.append("where id = ?");

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql.toString());//SQL실행 하기위한 객체 PrepareStatement 생성
            // 바인딩...
            int index = 1;
            if(userId != null) statement.setInt(index++, userId);
            if(categoriesId != null) statement.setInt(index++, categoriesId);
            if(title != null) statement.setString(index++, title);
            if(content != null) statement.setString(index++, content);
            statement.setInt(index, boardId);

            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("updateBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public void deleteBoard(int boardId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, boardId);//DB컬럼과 자바 오브젝트 필드 바인딩

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
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
