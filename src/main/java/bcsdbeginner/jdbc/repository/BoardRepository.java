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
        String sql = "insert into board(user_id, category_id, title, content) values(?, ?, ?, ?)";//board에 새로운 값 저장 SQL문

        try {
            connection = DBConnectionManager.getConnection();//DBConnectionManager를 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, newBoard.getUser_id());  //user_id에 해당하는 값 설정
            statement.setInt(2, newBoard.getCategory_id());  //category_id에 해당하는 값 설정
            statement.setString(3, newBoard.getTitle());  //title에 해당하는 값 설정
            statement.setString(4, newBoard.getContent());  //content에 해당하는 값 설정

            statement.executeUpdate(); //SQL 실행

            return newBoard; //newBoard라는 Board 객채를 반환

        } catch (SQLException e) { //에러 확인
            log.error("createBoard error={}", e); //error 메시지 출력
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public Board findById(Integer boardId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where id = ?"; //id값으로 board 조회 SQL문

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, boardId); //id값에 해당하는 값 설정

            rs = statement.executeQuery(); //ResultSet에 SQL 실행 값 설정
            Board board = new Board(); //return 해줄 새로운 Board 객체 설정
            while (rs.next()) {
                board.setId(rs.getInt("id")); //board의 id 설정
                board.setTitle(rs.getString("title")); //board의 title 설정
                board.setContent(rs.getString("content")); //board의 content 설정
                board.setUser_id(rs.getInt("user_id")); //board의 user_id 설정
                board.setCategory_id(rs.getInt("category_id")); //board의 category_id 설정
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //board의 Created_at에 넣어줄 값의 형식 지정
                board.setCreated_at(LocalDateTime.parse(rs.getString("created_at"), format)); //board의 created_at 설정
            }
            return board; //조회한 값 Board 객체로 반환
        } catch (SQLException e) {
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs);//사용한 리소스 반환
        }
    }

    public void updateBoard(Integer id, String newtitle, String newcontent) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set title = ?, content = ? where id = ?"; //board의 한 데이터 값 update를 위한 SQL문

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newtitle);//title 값에 해당하는 값 설정
            statement.setString(2, newcontent);//content 값에 해당하는 값 설정
            statement.setInt(3, id);//id값에 해당하는 값 설정

            statement.executeUpdate(); //SQL 실행
        } catch (SQLException e) {
            log.error("updateBaord error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }

    public void deleteBoard(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?"; //id값으로 불러온 board의 한 데이터 삭제 SQL문

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id);//id값에 해당하는 값 설정

            statement.executeUpdate(); //SQL 실행

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
