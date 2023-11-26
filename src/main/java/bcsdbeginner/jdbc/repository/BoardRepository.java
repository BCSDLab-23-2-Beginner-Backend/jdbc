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
    public Board createBoard(Board newBoard) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into board(user_id, category_id,title,content) values(?, ?, ?, ?)";//DB에 넘길 SQL 작성

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, newBoard.getUser_id());//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setInt(2, newBoard.getCategory_id());
            statement.setString(3, newBoard.getTitle());
            statement.setString(4, newBoard.getContent());

            statement.executeUpdate();
            return newBoard;

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
        ResultSet rs = null;
        String sql = "select * from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, boardId);

            rs = statement.executeQuery();
            Board board = new Board();

            while (rs.next()) {
                board.setId(rs.getInt("id"));
                board.setUser_id(rs.getInt("user_id"));
                board.setCategory_id(rs.getInt("category_id"));
                board.setTitle(rs.getString("title"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                board.setCreated_at(LocalDateTime.parse(rs.getString("created_at"), format));
            }
            return board;
        } catch (SQLException e) {
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs);//사용한 리소스 반환
        }
    }
    public void updateBoardTitle(Integer id, String newTitle) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set title = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newTitle);//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }
    public void deleteBoard(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, id);//DB컬럼과 자바 오브젝트 필드 바인딩

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
