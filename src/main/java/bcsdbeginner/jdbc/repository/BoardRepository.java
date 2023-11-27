package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class BoardRepository {


    // 게시글 생성(create) 코드
    public Board createBoard(Board newBoard) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        // board에 값을 넣는 sql문, id는 자동으로 증가하여 입력할 필요 없음. 생성 시간도 시간을 직접 받아오기 때문에 입력 필요 없음.
        String sql = "insert into board(user_id, category_id, title, content) values(?, ?, ?, ?)";

        try{
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성
            // sql문의 ?에 순서대로 들어갈 값들을 설정
            statement.setInt(1, newBoard.getUser_id());
            statement.setInt(2, newBoard.getCategory_id());
            statement.setString(3, newBoard.getTitle());
            statement.setString(4, newBoard.getContent());

            statement.executeUpdate();
            return newBoard;
        } catch(SQLException e){
            log.error("createBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);
        }
    }
    // 게시글 조회(read) 코드
    public Board findById(Integer boardId) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        // 게시글의 id로 조회
        String sql = "select * from board where id = ?";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);
            // 게시글의 id가 'boardId'인 게시글의 컬럼 조회
            statement.setInt(1, boardId);
            // 조회한 결과물을 resultset에 저장
            rs = statement.executeQuery();
            Board board = new Board();
            // resultset에 저장된 조회 결과물을 next()를 통해 board에 저장한다.
            while(rs.next()){
                board.setId(rs.getInt("id"));
                board.setUser_id(rs.getInt("user_id"));
                board.setCategory_id(rs.getInt("category_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                board.setCreated_at(LocalDateTime.parse(rs.getString("created_at"), format));
            }
            return board;
        } catch (SQLException e){
            log.error("selectBoard error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, rs);
        }
    }
    // 게시글 업데이트
    public void updateBoard(Integer id, String newTitle, String newContent) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        // 게시글의 id를 조건으로 제목, 내용 업데이트
        String sql = "update board set title = ?, content = ? where id = ?";
        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성
            // 위 sql문의 ?에 순서대로 들어갈 값들
            statement.setString(1, newTitle);//DB컬럼과 자바 오브젝트 필드 바인딩
            statement.setString(2, newContent);
            statement.setInt(3, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("updateUser error={}", e);
            throw e;
        } finally {
            closeResource(connection, statement, null);//사용한 리소스 반환
        }
    }
    // 게시글 삭제
    public void deleteBoard(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        // 게시글의 id로 삭제
        String sql = "delete from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sql);//SQL실행 하기위한 객체 PrepareStatement 생성
            // 위 sql문의  ?에 들어갈 값
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
