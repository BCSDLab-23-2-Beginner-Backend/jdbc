package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.BoardHashtags;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Slf4j
public class BoardHashtagsRepository {

    // 새로운 boardhashtags를 생성하는 함수: Create
    public BoardHashtags CreateBoardHashtags(BoardHashtags newBoardHashtags) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // Insert문 작성
        String sqlBoardHashtags = "INSERT INTO `board_hashtags` VALUES(?, ?)";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sqlBoardHashtags);

            statement.setInt(1, newBoardHashtags.getBoard_id()); // board_id에 대응하는 값 입력
            statement.setInt(2, newBoardHashtags.getHashtag_id()); // hashtag_id에 대응하는 값 입력

            statement.executeUpdate(); // 쿼리문 실행

            return newBoardHashtags; // newBoardHashtags반환

        } catch (SQLException e) {

            log.error("CreateBoardHashtags error={}", e); // Exception 발생시 로그
            throw e;

        } finally {
            closeResources(connection, statement, null); // 자원 반환
        }
    }

    // 모든 boardhashtag를 불러오는 함수: Read
    public ArrayList<BoardHashtags> findAll() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // 모든 값 불러오는 쿼리문 작성
        String sql = "select * from board_hashtags";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            resultset = statement.executeQuery();
            ArrayList<BoardHashtags> resBoardHashtags = new ArrayList<>(); // 여러개의 값을 저장하기 위해 ArrayList활용
            while(resultset.next()){
                BoardHashtags BoardHashtags_tmp = new BoardHashtags(); // 임시 객체 생성
                BoardHashtags_tmp.setBoard_id(resultset.getInt("board_id")); // set board_id
                BoardHashtags_tmp.setHashtag_id(resultset.getInt("hashtag_id")); // set hashtag_id
                resBoardHashtags.add(BoardHashtags_tmp); // resBoardHashtags에 add
            }

            return resBoardHashtags; // resBoardHashtags 반환
        } catch (SQLException e){
            log.error("BoardHashtags.findAll={}", e); // Exception 발생 시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환
        }
    }

    // 특정 board_id로 검색하는 함수: Read
    public ArrayList<BoardHashtags> findbyBoardId(int board_id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // board_id를 where절 조건으로 준 쿼리 작성
        String sql = "select * from board_hashtags where board_id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, board_id); // board_id에 대응하는 값 입력

            resultset = statement.executeQuery(); // 쿼리 실행
            ArrayList<BoardHashtags> resBoardHashtags = new ArrayList<>(); // 여러개의 값을 반환해야 하기 때문에 ArrayList 활용
            while(resultset.next()){
                BoardHashtags BoardHashtags_tmp = new BoardHashtags(); // 임시 객체 생성
                BoardHashtags_tmp.setBoard_id(resultset.getInt("board_id")); // set board_id
                BoardHashtags_tmp.setHashtag_id(resultset.getInt("hashtag_id")); // set hashtag_id
                resBoardHashtags.add(BoardHashtags_tmp); // resBoardHashtags에 add
            }

            return resBoardHashtags; // resBoardHashtags 반환
        } catch (SQLException e){
            log.error("BoardHashtags.findbyBoardId={}", e); // Exception 발생 시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환
        }
    }

    // 특정 hashtag_id로 검색하는 함수: Read
    public ArrayList<BoardHashtags> findbyHashtagId(int hashtag_id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // hashtag_id를 where절 조건으로 준 쿼리문 작성
        String sql = "select * from board_hashtags where hashtag_id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, hashtag_id); // hashtag_id에 대응하는 값 입력

            resultset = statement.executeQuery(); // 쿼리 실행
            ArrayList<BoardHashtags> resBoardHashtags = new ArrayList<>(); // 여러개의 값을 반환해야 하기 때문에 ArrayList 사용
            while(resultset.next()){
                BoardHashtags BoardHashtags_tmp = new BoardHashtags(); // 임시 객체 생성
                BoardHashtags_tmp.setBoard_id(resultset.getInt("board_id")); // set board_id
                BoardHashtags_tmp.setHashtag_id(resultset.getInt("hashtag_id")); // set hashtag_id
                resBoardHashtags.add(BoardHashtags_tmp); // resBoardHashtags에 add
            }

            return resBoardHashtags; // resBoardHashtags 반환
        } catch (SQLException e){
            log.error("BoardHashtags.findbyHashtagId={}", e); // Exception 발생 시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환
        }
    }

    // update, delete 함수 미구현
    // board_hashtag table은 hashtag table과 board table의 id값을 참조하여 생성되는 table이다.
    // 굳이 update, delete를 사용하지 않아도 테이블 설정에 아래 구문이 있기 때문에 board table 또는 hashtags table이 udpate되면 같이 update된다.
    /*
    -- 게시글과 해시태그의 관계를 나타내는 연결 테이블
CREATE TABLE board_hashtags (
    board_id INT,
    hashtag_id INT,
    PRIMARY KEY (board_id, hashtag_id),
    FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE,
    FOREIGN KEY (hashtag_id) REFERENCES hashtags(id) ON DELETE CASCADE
);
     */
    // ON DELETE CASCADE가 있기 때문에.

    // 자원 반환하는 함수
    // 반드시 ResultSet, PreparedStatement, Connection 순서대로 반환해야한다!
    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultset) {
        if (resultset != null){ // resultset 객체가 있는 경우
            try{
                resultset.close();
            } catch (SQLException e) {
                log.error("resultset close error");
            }
        }

        if (statement != null){ // statement 객체가 있는 경우
            try{
                statement.close();
            } catch (SQLException e) {
                log.error("statement close error");
            }
        }

        if (connection != null){ // connection 객체가 있는 경우
            try{
                connection.close();
            } catch (SQLException e) {
                log.error("connection close error");
            }
        }

    }
}
