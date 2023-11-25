package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // 날짜와 시간을 형식하고 파싱하기 위한 클래스

@Slf4j
public class BoardRepository {
    public Board createBoard(Board newBoard) throws SQLException { //"C"
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "INSERT INTO board(user_id, category_id, title, content) values(?, ?, ?, ?)"; // DB에 넘길 SQL작성
        
        try {
            connection = DBConnectionManager.getConnection(); // DriverManager 통해서 DBconnection 생성
            statement = connection.prepareStatement(sql); // SQL 실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, newBoard.getUser_id()); // 쿼리의 매개변수에 값을 바인딩
            statement.setInt(2, newBoard.getCategory_id());
            statement.setString(3, newBoard.getTitle());
            statement.setString(4, newBoard.getContent());

            statement.executeUpdate(); // 주어진 SQL문을 실행하여 데이터베이스를 업데이트
            return newBoard; // 생성된 레코드의 정보 반환
        }
        catch (SQLException e) { // 예외처리
            log.error("createBoard error={}", e); //로깅
            throw e; // 예외를 다시 던짐
        }
        finally { // 항상 실행됨
            closeResource(connection, statement, null); // 사용한 리소스 반환
        }
    }

    public Board findById(Integer BoardId) throws SQLException { //"R"
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM board WHERE id = ?"; // DB에 넘길 SQL작성

        try {
            connection = DBConnectionManager.getConnection(); // DriverManager 통해서 DBconnection 생성
            statement = connection.prepareStatement(sql); // SQL 실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, BoardId); // 쿼리의 매개변수에 값을 바인딩

            rs = statement.executeQuery(); // 객체를 통해 데이터베이스에서 쿼리를 실행하고 결과를 받아오는 코드
            Board board = new Board(); // 데이터베이스에서 가져온 결과를 담을 객체 생성

            while(rs.next()) { // rs.next() → ResultSet 객체에서 다음 행으로 이동하는 메서드, 이동 가능시 true 아니면 false 반환
                board.setId(rs.getInt("id")); // rs객체에서 id열의 값을 가져와 board객체에 저장
                board.setUser_id(rs.getInt("user_id")); // rs객체에서 user_id열의 값을 가져와 board객체에 저장
                board.setCategory_id(rs.getInt("category_id")); // rs객체에서 category_id열의 값을 가져와 board객체에 저장
                board.setTitle(rs.getString("title")); // rs객체에서 title열의 값을 가져와 board객체에 저장
                board.setContent(rs.getString("content")); // rs객체에서 content열의 값을 가져와 board객체에 저장
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 특정한 패턴으로 format 생성
                board.setCreated_at(LocalDateTime.parse(rs.getString("created_at"), format));
                // rs객체에서 가져온 문자열을 format객체를 사용하여 LocalDateTime 객체로 파싱하여 board객체에 저장
            }
            return board; // 읽은 board 정보 반환
        }
        catch (SQLException e) { // 예외처리
            log.error("selectBoard error={}", e); // 로깅
            throw e; // 예외를 다시 던짐
        }
        finally { // 항상 실행됨
            closeResource(connection, statement, rs); // 사용한 리소스 반환
        }
    }

    public void updateContent(Integer id, String newContent) throws SQLException { //"U"
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "UPDATE board SET content = ? where id = ?"; // DB에 넘길 SQL작성

        try {
            connection = DBConnectionManager.getConnection(); // DriverManager 통해서 DBconnection 생성
            statement = connection.prepareStatement(sql); // SQL 실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newContent); // 쿼리의 매개변수에 값을 바인딩
            statement.setInt(2, id);

            statement.executeUpdate(); // 주어진 SQL문을 실행하여 데이터베이스를 업데이트
        }
        catch (SQLException e) { //예외처리
            log.error("updateContent error={}", e); //로깅
            throw e; // 예외를 다시 던짐
        }
        finally { // 항상 실행됨
            closeResource(connection, statement, null); // 사용한 리소스 반환
        }
    }

    public void deleteBoard(int id) throws SQLException { // "D"
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "DELETE FROM board WHERE id = ?"; // DB에 넘길 SQL작성

        try {
            connection = DBConnectionManager.getConnection(); // DriverManager 통해서 DBconnection 생성
            statement = connection.prepareStatement(sql); // SQL 실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, id); // 쿼리의 매개변수에 값을 바인딩
            statement.executeUpdate(); // 주어진 SQL문을 실행하여 데이터베이스를 업데이트
        }
        catch (SQLException e) { //예외처리
            log.error("deleteBoard error={}", e); //로깅
            throw e; // 예외를 다시 던짐
        }
        finally {
            closeResource(connection, statement, null); // 사용한 리소스 반환
        }
    }

    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet){
        // 반드시 역순으로 반환
        if (resultSet != null) { // null이 아닌경우에만 반환
            try {
                resultSet.close();
            }
            catch (Exception e) {
                log.error("error", e);
            }
        }

        if (statement != null) { // null이 아닌경우에만 반환
            try {
                statement.close();
            }
            catch (Exception e) {
                log.error("error", e);
            }
        }

        if (connection != null) { // null이 아닌경우에만 반환
            try {
                connection.close();
            }
            catch (Exception e) {
                log.error("error", e);
            }
        }
    }
}