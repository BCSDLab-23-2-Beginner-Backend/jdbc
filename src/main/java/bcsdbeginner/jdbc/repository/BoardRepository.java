package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BoardRepository {

    // update함수에서 사용할 enum 작성
    public enum UpdateField {
        TITLE, CONTENT, CATEGORY
    }

    // private로 게시글 id 받아오는 함수 작성
    // 내부적으로 id를 활용하는 경우가 많아 private로 선언함
    private Integer getBoardId(Board board) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        // user_id, category_id, title, content를 비교하여 원하는 게시글의 id를 불러옴
        String sqlBoardId = "SELECT `id` FROM `board` WHERE `user_id` = ? AND `category_id` = ? AND `title` = ? AND `content` = ?";
        try{
            connection = DBConnectionManager.getConnection(); //DriverManger 통해서 DB커넥션 생성
            statement = connection.prepareStatement(sqlBoardId); // sql 실행을 위한 PreparedStatement 객체 생성

            statement.setInt(1, board.getUser_id()); // user_id에 대응하는 값 입력
            statement.setInt(2, board.getCategory_id()); // category_id에 대응하는 값 입력
            statement.setString(3, board.getTitle()); // title에 대응하는 값 입력
            statement.setString(4, board.getContent()); // content에 대응하는 값 입력

            result = statement.executeQuery(); // 쿼리 실행

            Integer resBoardId = null; // 결과 저장용 변수
            while(result.next()){ // result에 값이 있는 경우 반복
                resBoardId = result.getInt("id"); // result에서 columnLabel이 "id"인 값을 찾아 resBoardId에 저장
            }

            return resBoardId; // 결과값 리턴

        } catch (SQLException e){

            log.error("getBoardId error={}", e); // Exception 발생시 log
            throw e;

        } finally {
            closeResources(connection, statement, result); // 자원 반환
        }
    }

    // board 테이블에 데이터 생성하는 함수: Create
    public Board CreateBoard(Board newBoard) throws SQLException {

        Connection connection = null;
        PreparedStatement statement = null;

        // user_id, category_id, title, content, created_at만 이용하여 board insert하는 쿼리문 작성
        // board_id 는 AI가 적용되어있어 그냥 데이터를 넣으면 알아서 정해짐 !!
        String sqlBoard = "INSERT INTO `board`(`user_id`, `category_id`, `title`, `content`, `created_at`) VALUES(?, ?, ?, ?, ?)";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sqlBoard);

            statement.setInt(1, newBoard.getUser_id()); // user_id에 대응하는 값 입력
            statement.setInt(2, newBoard.getCategory_id()); // category_id에 대응하는 값 입력
            statement.setString(3, newBoard.getTitle()); // title에 대응하는 값 입력
            statement.setString(4, newBoard.getContent()); // content에 대응하는 값 입력
            // timestamp의 자료형은 LocalDateTime이기 때문에 toString 함수로 문자열로 변환
            statement.setString(5, newBoard.getTimestamp().toString()); // timestamp에 대응하는 값 입력

            statement.executeUpdate(); // 쿼리문 실행

            // 쿼리문이 실행된 이후이기 때문에 id가 존재함
            // id 받아와서 newBoard에 설정
            newBoard.setId(getBoardId(newBoard));
            return newBoard; // newBoard 반환

        } catch (SQLException e) {
            log.error("CreateBoard error={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResources(connection, statement, null); // 자원 반환. resultset이 없기 때문에 null
        }

    }

    // 모든 게시글 정보를 불러오는 함수: Read
    public ArrayList<Board> findAll() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // board table의 모든 값을 가져오는 쿼리문 작성
        String sql = "select * from board";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            resultset = statement.executeQuery(); // 쿼리문 실행

            ArrayList<Board> resBoard = new ArrayList<>(); // 여러개의 Board객체를 반환해야하기 때문에 ArrayList로 객체 생성
            while(resultset.next()){
                Board board_tmp = new Board(); // 임시 Board객체 생성
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // timestamp 포매터 생성
                board_tmp.setTimestamp(LocalDateTime.parse(resultset.getString("created_at"), format)); // timestamp 설정
                board_tmp.setId(resultset.getInt("id")); // id 설정
                board_tmp.setContent(resultset.getString("content")); // content 설정
                board_tmp.setUser_id(resultset.getInt("user_id")); // user_id 설정
                board_tmp.setCategory_id(resultset.getInt("category_id")); // category_id 설정
                board_tmp.setTitle(resultset.getString("title")); // title 설정
                resBoard.add(board_tmp); // 임시 Board객체를 resBoard에 add
            }

            return resBoard; // resBoard 반환

        } catch (SQLException e){
            log.error("Categories.findAll={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환
        }
    }

    // id를 이용하여 특정 게시글을 검색하는 함수: Read
    public Board findbyId(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        
        // where절로 id를 검색하는 조건 추가한 쿼리문 작성
        String sql = "select * from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id); // id에 대응하는 값 입력

            resultset = statement.executeQuery(); // 쿼리문 실행
            Board resBoard = new Board(); // 결과물 객체 생성
            while(resultset.next()){ // Board 정보 set
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                resBoard.setTimestamp(LocalDateTime.parse(resultset.getString("created_at"), format));
                resBoard.setId(resultset.getInt("id"));
                resBoard.setContent(resultset.getString("content"));
                resBoard.setUser_id(resultset.getInt("user_id"));
                resBoard.setCategory_id(resultset.getInt("category_id"));
                resBoard.setTitle(resultset.getString("title"));
            }

            return resBoard; // 결과물 객체 반환
            
        } catch (SQLException e){
            log.error("Categories.findbyId={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환
        }
    }

    // 특정 게시물의 title을 update하는 함수: Update
    public void updateBoard(Integer id, String newString, UpdateField field) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // sql 객체 생성
        String sql = "";

        // 입력한 field에 따라 sql문 선택
        switch(field){
            case TITLE:
                // TITLE인 경우
                sql = "update board set title = ? where id = ?";
                break;
            case CATEGORY:
                // CATEGORY인 경우
                sql = "update board set category_id = ? where id = ?";
                break;
            case CONTENT:
                // CONTENT인 경우
                sql = "update board set content = ? where id = ?";
                break;

        }

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            switch(field){
                case CATEGORY:
                    // CATEGORY인 경우 정수형이 들어가야하기 때문에 따로 작성
                    statement.setInt(1, Integer.parseInt(newString));
                    break;
                default:
                    // 그 외의 상황은 전부 문자열
                    statement.setString(1, newString);
                    break;
            }
            statement.setInt(2, id); // id에 대응하는 값 입력

            statement.executeUpdate(); // 쿼리문 실행
            
        } catch (SQLException e) {
            log.error("updateBoardTitle error={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResources(connection, statement, null); // 자원 반환
        }
    }

    // 특정 게시물을 제거하는 함수: Delete
    public void DeleteBoard(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        // 특정 id를 가진 게시글을 제거하는 쿼리문 작성
        String sql = "delete from board where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id); // id에 대응하는 값 입력

            statement.executeUpdate(); // 쿼리문 실행
        } catch (SQLException e) {
            log.error("deleteBoard error={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResources(connection, statement, null); // 자원 반환
        }
    }

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
