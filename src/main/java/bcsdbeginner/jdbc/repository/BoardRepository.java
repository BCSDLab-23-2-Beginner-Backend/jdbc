package bcsdbeginner.jdbc.repository;


import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class BoardRepository {

    //게시물 저장
    public void crateBoard(Board newBoard) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into Board values(null, ?, ?, null, ?)";

        try {
            connection = DBConnectionManager.getConnection(); // DriverManager를 통해 DB 커넥션 생성
            statement = connection.prepareStatement(sql); // SQL 실행을 위한 PrepareStatement 객체 생성

            statement.setString(1, newBoard.getTitle()); // DB 컬럼과 자바 객체 필드를 바인딩
            statement.setString(2, newBoard.getContent());
            statement.setTimestamp(3, newBoard.getBoard_day());


            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("createBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null); // 사용한 리소스 반환
        }
    }

    public void updateBoard(Board updateBoard) throws SQLException {//게시물 수정
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update Board set title = ?, content = ? WHERE board_num = ? and user_num = ?";

        try {
            connection = DBConnectionManager.getConnection(); // DriverManager를 통해 DB 커넥션 생성
            statement = connection.prepareStatement(sql); // SQL 실행을 위한 PrepareStatement 객체 생성

            statement.setString(1, updateBoard.getTitle()); // DB 컬럼과 자바 객체 필드를 바인딩
            statement.setString(2, updateBoard.getContent());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null); // 사용한 리소스 반환
        }
    }

    // 게시물 삭제
    public void deleteBoard(Integer boardNum) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from Board where board_num = ? and user_num = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, boardNum);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("deleteBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);
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