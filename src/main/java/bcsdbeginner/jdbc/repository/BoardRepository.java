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
    public Board Createboard(Board newboard) throws SQLException
    {
        Connection connection = null; // #1 DB에 연결하는 Connection 객체 생성
        PreparedStatement statement = null; // #2 쿼리 실행을 위한 Statement 객체 생성
        String sql = "insert into board values (?, ?, ?, ?, ?, ?)"; // #3 Insert 쿼리 생성
        try
        {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            log.info("statement = {}", statement);

            statement.setInt(1, newboard.getId()); // #4 각 객체에 값을 get을 이용하여 설정
            statement.setInt(2, newboard.getUser_id());
            statement.setInt(3, newboard.getCategory_id());
            statement.setString(4, newboard.getTitle());
            statement.setString(5, newboard.getContent());
            statement.setString(6, newboard.getCreate_at().toString()); // #5 LocalDatetime을 toString 형태로 저장

            statement.executeUpdate(); // #6 DB에 영향을 미치는 행 수 반환, insert 쿼리 실행

            return newboard; // #7 저장된 newboard를 반환
        }
        catch(Exception e)
        {
            throw new RuntimeException(e); // #8 예외처리 시행
        }
        finally
        {
            closeResourse(connection, statement, null); // #9 종료는 역순으로 진행
        }
    }

    public Board findById(Integer boardId) throws SQLException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String sql = "select * from board where id = ?"; // #1 select 쿼리 생성
        try
        {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            log.info("statement = {}", statement);

            statement.setInt(1, boardId);
            resultSet = statement.executeQuery(); // #2 쿼리 실행하고 결과 행 얻어냄

            Board findBoard = new Board();
            while(resultSet.next()) // #3 조회 결과를 담아낼 객체들을 생성함
            {
                findBoard.setId(resultSet.getInt("id")); // #4 각 객체에 맞는 속성으로 값을 저장한다
                findBoard.setUser_id(resultSet.getInt("user_id"));
                findBoard.setCategory_id(resultSet.getInt("category_id"));
                findBoard.setTitle(resultSet.getString("title"));
                findBoard.setContent(resultSet.getString("content"));
                // #5 날짜, 시간에 관한 경우는 LocalDateTime으로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                findBoard.setCreate_at(LocalDateTime.parse(resultSet.getString("created_at"), formatter));
            }
            return findBoard; // #6 찾아서 저장한 findBoard를 반환함
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            closeResourse(connection, statement, null);
        }
    }

    public void updateTitle(Integer boardid, String title)
    {
        Connection connection = null;
        PreparedStatement statement = null;

        String sql = "update board set title = ? where id = ?"; // #1 update 쿼리 생성
        try
        {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            log.info("statement = {}", statement);

            statement.setString(1, title); // #2 위 sql의 순서가 title, id이므로 title부터
            statement.setInt(2, boardid); // #3 2번째로는 boardid를 설정

            statement.executeUpdate(); // #4 update 쿼리 실행
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            closeResourse(connection, statement, null);
        }
    }

    public void deleteBoard(Integer boardid)
    {
        Connection connection = null;
        PreparedStatement statement = null;

        String sql = "delete from board where id = ?"; // #1 delete 쿼리 생성
        try
        {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            log.info("statement = {}", statement);

            statement.setInt(1, boardid); // #2 Board 삭제를 위해 id 설정

            statement.executeUpdate(); // #3 delete 쿼리 실행
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            closeResourse(connection, statement, null);
        }
    }

    private void closeResourse(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch(Exception e)
            {
                log.error("Error closing ResultSet", e);
            }
        }
        if (statement != null)
        {
            try{
                statement.close();
            }catch(Exception e)
            {
                log.error("Error closing Statement", e);
            }
        }
        if (connection != null)
        {
            try{
                connection.close();
            }catch(Exception e)
            {
                log.error("Error closing Connection", e);
            }
        }
    }
}


