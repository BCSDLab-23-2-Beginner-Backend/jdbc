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

// lombok 라이브러리에서 제공하는 어노테이션. 자동으로 로깅 코드를 생성
@Slf4j
public class BoardRepository {
    // board를 만드는 함수
    public Board createBoard(Board newBoard) throws SQLException {
        // 데이터 베이스와 연결
        Connection connection = null;

        // sql 쿼리
        PreparedStatement statement = null;
        String sql = "insert into board(user_id, category_id, title, content) values(?, ?, ?, ?)"; //DB에 넘길 SQL 작성

        try {
            connection = DBConnectionManager.getConnection(); // //DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            //DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setInt(1, newBoard.getUser_id());
            statement.setInt(2, newBoard.getCategory_id());
            statement.setString(3, newBoard.getTitle());
            statement.setString(4, newBoard.getContent());

            // sql 쿼리를 실행하고 데이터베이스에 추가
            statement.executeUpdate();
            return newBoard;
        } catch (SQLException e) {
            // 예외 발생시 오류 표시
            log.error("createBoard error={}", e);
            throw e;
        } finally {
            // 메소드 종료시 자원을 반환하고 종료.
            // 마지막에 null인 이유는 resultSet을 사용하지 않았기 때문이다.
            closeResource(connection, statement, null);
        }
    }

    // id로 board를 찾는 메소드이다
    public Board findById(Integer boardId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            // sql의 ? 위치에 바인드
            statement.setInt(1, boardId);
            // sql 쿼리를 실행하고 결과를 rs에 저장
            rs = statement.executeQuery();
            Board board = new Board();

            // rs에 받아온 데이터를 순회하면서 가져오고 board에 넣는다
            while(rs.next()) {
                board.setId(rs.getInt("id"));
                board.setUser_id(rs.getInt("user_id"));
                board.setCategory_id(rs.getInt("category_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                board.setCreated_at(LocalDateTime.parse(rs.getString("created_at"), formatter));
            }
            return board;
        } catch (SQLException e) {
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs);
        }
    }

    // board의 title을 수정하는 메소드이다
    public void updateBoardTitle(Integer id, String title) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set title = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, title);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateBoardTitle error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement,null);
        }
    }

    // id로 board를 삭제하는 메소드이다
    public void deleteBoard(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성
            statement.setInt(1, id);
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
