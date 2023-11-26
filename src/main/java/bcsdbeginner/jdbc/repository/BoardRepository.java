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
    public Integer save(Board board){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into board values(?,?,?,?,?,?)";
        // sql 쿼리문 정의

        try {
            connection = DBConnectionManager.getConnection();
            //DriverManger 통해서 DB커넥션 생성(DB 연결)
            statement = connection.prepareStatement(sql);
            //SQL 실행하기 위한 객체 PrepareStatement 객체 생성

            // 게시글의 데이터(board_id, id, category_id, title, content, create_at)를 statement에 바인딩
            statement.setInt(1, board.getBoard_id());
            statement.setInt(2, board.getUser_id());
            statement.setInt(3, board.getCategory_id());

            // user_id와 category_id는 외래키이므로, 외래키가 검색되지 않을 경우 null값 입력
//            if (?? != null) {
//                statement.setInt(2, board.getUser_id());
//            } else{
//                statement.setString(2, "null");
//            }
//            if (?? != null) {
//                statement.setInt(3, board.getCategory_id());
//            } else{
//                statement.setString(3, "null");
//            }
            statement.setString(4, board.getTitle());
            statement.setString(5, board.getContent());
            statement.setString(6, board.getCreate_at().toString());
            statement.executeUpdate();  // SQL 쿼리 실행

            return board.getBoard_id();    // 게시글의 id 반환
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    public Board findByBoardId(Integer board_id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from board where id = ?";
        // SQL 쿼리문

        try {
            connection = DBConnectionManager.getConnection();
            //DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);
            //SQL 실행하기 위한 객체 PrepareStatement 생성

            statement.setInt(1, board_id);
            // 첫번째 인자에 board_id 값 설정
            resultSet = statement.executeQuery();
            // SQL 쿼리 실행 결과를 resultSet 객체에 저장

            Board findBoard = new Board();
            while (resultSet.next()){   // resultSet의 결과를 하나씩 넘어가며 반복
                // resultSet의 각 열에 해당하는 값을 가져와서 findUser 객체에 저장
                findBoard.setBoard_id(resultSet.getInt("id"));
                findBoard.setUser_id(resultSet.getInt("user_id"));
                findBoard.setCategory_id(resultSet.getInt("category_id"));
                findBoard.setTitle(resultSet.getString("title"));
                findBoard.setContent((resultSet.getString("content")));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 날짜 형식 지정을 위한 객체 생성
                findBoard.setCreate_at(LocalDateTime.parse(resultSet.getString("created_at"), formatter));
                // create_at 열의 값을 가져와 LocalDateTime 객체로 변환 후 findUser 객체에 저장
            }
            return findBoard;    // 생성된 findBoard 객체 반환
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    public void updateBoardTitle(Integer board_id, String title){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set title = ? where id = ?";
        // SQL 쿼리문

        try {
            connection = DBConnectionManager.getConnection();
            //DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);
            //SQL 실행하기 위한 객체 PrepareStatement 생성

            // statement 객체의 첫번째 인자와 두번째 인자에 title과 board_id 설정
            // 각각의 값이 쿼리문의 첫번째 값과 두번째 값으로 들어감
            statement.setString(1, title);
            statement.setInt(2, board_id);

            statement.executeUpdate();  // 쿼리문 실행
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    public void updateBoardContent(Integer board_id, String content){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set content = ? where id = ?";
        // SQL 쿼리문

        try {
            connection = DBConnectionManager.getConnection();
            //DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);
            //SQL 실행하기 위한 객체 PrepareStatement 생성

            // statement 객체의 첫번째 인자와 두번째 인자에 contentd와 board_id 설정
            statement.setString(1, content);
            statement.setInt(2, board_id);

            statement.executeUpdate();  // 쿼리문 실행
        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    public void deleteBoard(Integer board_id){
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?";
        // SQL 쿼리문

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1,board_id);
            // statement 첫번째 인자에 board_id 설정

            statement.executeUpdate();
            // 쿼리문 실행

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            closeResource(connection, statement, null);
            //사용한 리소스 반환
        }
    }

    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        //반환할 때는 반드시 역순으로 반환해야 함.

        // resultSet, statement, connection 순서로 리소스 반환
        // 각 리소스 반환 시 오류가 발생하면 로그에 error를 던짐
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                log.error("Error closing resultSet", e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                log.error("Error closing PrepareStatement", e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error("Error closing Connection", e);
            }
        }
    }
}
