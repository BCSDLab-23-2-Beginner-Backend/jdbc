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
        Connection connection = null; //데이터베이스 연결을 나타내는 객체
        PreparedStatement statement = null; //SQL 문을 실행하기 위한 객체
        String sql = "insert into board(user_id, title, content) values(?, ?, ?)";

        try {
            connection = DBConnectionManager.getConnection();   //DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);   //SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, newBoard.getUser_id()); //DB컬럼과 자바 오브젝트 필드 바인딩 // getter는 @Data에서 자동 생성
            statement.setString(2, newBoard.getTitle());
            statement.setString(3, newBoard.getContent());

            statement.executeUpdate();
            return newBoard;

        } catch (SQLException e) {
            log.error("createBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    // 게시물 ID로 검색
    public Board findById(Integer user_Id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, user_Id);

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
            closeResource(connection, statement, rs);//사용한 리소스 반환
        }
    }

    // 게시물 제목으로 검색
    public Board findByTitle(String title) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where title = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, title);

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
            closeResource(connection, statement, rs);//사용한 리소스 반환
        }
    }

    // 게시물 내용 및 제목 업데이트
    public void updateBoard(Integer id, String newTitle, String newContent) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set title = ?, content = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newTitle);
            statement.setString(2, newContent);
            statement.setInt(3, id);


            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    // 게시물 제목으로 삭제
    public void deleteBoard(String title) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where title = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, title);//DB컬럼과 자바 오브젝트 필드 바인딩

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
                log.error("resultset error", e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                log.error("statement error", e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error("connection error", e);
            }
        }
    }
}