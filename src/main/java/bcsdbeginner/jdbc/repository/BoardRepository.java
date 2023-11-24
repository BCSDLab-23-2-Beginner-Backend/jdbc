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
    public Board createBoard(Board newBoard) throws SQLException { // SQLException --> 코드 레벨에서 대처할 수 있는 수준이 아니니,, 바로 나한테 알려줘야함.
        Connection connection = null; // DB커넥션 초기화
        PreparedStatement statement = null; // Statement 초기화
        String sql = "insert into board(title, content) values(?, ?)"; // DB에 넘길 SQL 작성

        try {
            connection = DBConnectionManager.getConnection(); // DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql); // SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newBoard.getTitle()); // DB컬럼과 자바 오브젝트 필드 바인딩 (제목)
            statement.setString(2, newBoard.getContent()); // (내용)

            statement.executeUpdate(); // 업데이팅
            return newBoard;

        } catch (SQLException e) {
            log.error("createBoard error={}", e); // 오류 출력
            throw e;
        } finally {
            closeResource(connection, statement, null); // 사용한 리소스 반환 --> 안해주면 계속 쌓이다가 (낭비), 더 이상 생성이 불가해짐..(Connection, statement)
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

            statement.setInt(1, boardId); // 숫자는 순서를 의미함

            rs = statement.executeQuery(); // sql을 실행하고 받아와야 하므로 executeQuery() 사용!!
            Board board = new Board(); // 찾을 때 쓰일 Board 객체 생성
            while (rs.next()) {
                board.setId(rs.getInt("id")); // 구별하기 위한 id !!! "받아와서 그 값으로 씌어주자!!"
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 시간 포맷 패턴 설정
                board.setCreate_at(LocalDateTime.parse(rs.getString("created_at"), format)); // 그냥 시간 포맷 넣어줌. 실습 코드 참고
            }
            return board;
        } catch (SQLException e) {
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs); // 사용한 리소스 반환
        }
    }
    public Board readBoard(Integer boardId) throws SQLException { // READ 구현
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String sql = "select * from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, boardId);

            rs = statement.executeQuery(); // sql을 실행하고 받아와야 하므로 executeQuery() 사용!!
            Board board = new Board();
            while (rs.next()) {
                System.out.println("--------------------------------------------");
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("title"));
                System.out.println(rs.getString("content"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 시간 포맷 패턴 설정
                System.out.println(LocalDateTime.parse(rs.getString("created_at"), format)); // 그냥 시간 포맷 넣어줌. 실습 코드 참고
                System.out.println("--------------------------------------------");
            }
            return board;
        } catch (SQLException e) {
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs); // 사용한 리소스 반환
        }
    }

    public void updateTitle(Integer id, String newTitle) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update board set title = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection(); // DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql); // SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setString(1, newTitle); // DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null); //사용한 리소스 반환
        }
    }

    public void deleteBoard(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection(); // DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql); // SQL실행 하기위한 객체 PrepareStatement 생성

            statement.setInt(1, id); // DB컬럼과 자바 오브젝트 필드 바인딩

            statement.executeUpdate(); // 업데이팅
        } catch (SQLException e) {
            log.error("deleteBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);
        }
    }

    private void closeResource(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        //반환할 때는 반드시 역순으로 반환해야 함. + 처음에 closeResource 해주는 이유 서술 함.
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
