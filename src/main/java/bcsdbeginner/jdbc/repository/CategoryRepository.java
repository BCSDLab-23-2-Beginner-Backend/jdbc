package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Categories;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Slf4j
public class CategoryRepository {

    // private로 카테고리 아이디 불러오는 코드 작성
    // id를 내부적으로 사용하는 경우가 있어 private로 설정
    private Integer getCategoryId(Categories category) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        // 특정 이름을 가진 categories의 id를 반환하는 쿼리 작성
        String sqlBoardId = "SELECT `id` FROM `categories` WHERE `name` = ?";
        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sqlBoardId);

            statement.setString(1, category.getName()); // name에 대응하는 값 입력

            result = statement.executeQuery(); // 쿼리 실행
            Integer resCategoryId = null; // 결과 객체 생성
            while(result.next()){
                resCategoryId = result.getInt("id"); // 결과 객체에 값 저장
            }

            return resCategoryId; // 결과 객체 반환

        } catch (SQLException e){

            log.error("getCategoryId error={}", e); // Exception 발생시 로그
            throw e;

        } finally {
            closeResources(connection, statement, result); // 객체 반환. resultset이 있기 때문에 null대신 해당 객체 전달!
        }
    }

    // 새로운 카테고리 생성 함수: Create
    public Categories CreateCategories(Categories newCategories) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // Insert문으로 새로운 카테고리 생성하는 쿼리 작성
        // board와 마찬가지로 id는 AI가 적용되어있기 때문에 따로 입력하지 않음.
        // 추가로 이미 존재하는 카테고리의 경우 추가하지 않고, 없는 경우에만 추가하도록 쿼리 작성
        String sqlCategories = "INSERT INTO `categories`(`name`) SELECT ? FROM DUAL WHERE NOT EXISTS(SELECT `name` FROM `categories` WHERE `name` = ?)";

        try{
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sqlCategories);

            statement.setString(1, newCategories.getName()); // name에 대응하는 값 입력
            statement.setString(2, newCategories.getName()); // name에 대응하는 값 입력

            statement.executeUpdate(); // 쿼리 실행

            newCategories.setId(getCategoryId(newCategories)); // newCategories의 id를 설정 후
            return newCategories; // newCategories 반환

        } catch (SQLException e) {
            log.error("CreateCategories error={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResources(connection, statement, null); // resultset이 없기 때문에 앞의 두 객체만 close!
        }
    }

    // 모든 카테고리를 반환하는 함수: Read
    public ArrayList<Categories> findAll() throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // id, name에 관계 없이 모든 카테고리를 반환하는 쿼리 작성
        String sql = "select * from categories";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            resultset = statement.executeQuery(); // 쿼리 실행

            // 여러개의 Categories객체를 반환해야 하기 때문에 ArrayList 활용
            ArrayList<Categories> resCategories = new ArrayList<>();
            while(resultset.next()){
                Categories categories_tmp = new Categories();
                categories_tmp.setName(resultset.getString("name")); // name set
                categories_tmp.setId(resultset.getInt("id")); // id set
                resCategories.add(categories_tmp); // resCategories에 add
            }

            return resCategories; // resCategories 반환
        } catch (SQLException e){
            log.error("Categories.findAll={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환.
        }
    }

    // 특정 id를 가진 카테고리만 검색: Read
    public Categories findbyId(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        // WHERE절로 id를 검색하는 조건 추가한 쿼리문 작성
        String sql = "select * from categories where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id); // id에 대응하는 값 입력

            resultset = statement.executeQuery(); // 쿼리 실행
            Categories resCategories = new Categories(); // 결과 객체 생성
            while(resultset.next()){
                resCategories.setName(resultset.getString("name")); // name set
                resCategories.setId(resultset.getInt("id")); // id set
            }

            return resCategories; // 결과 객체 반환
        } catch (SQLException e){
            log.error("Categories.findbyId={}", e); // Exception 발생 시 로그
            throw e;
        } finally {
            closeResources(connection, statement, resultset); // 자원 반환
        }
    }

    // 특정 id를 가진 카테고리 업데이트: Update
    public void updateCategoryname(Integer id, String newCategoryname) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        // update문 작성
        String sql = "update categories set name = ? where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, newCategoryname); // 새로운 카테고리 이름 입력
            statement.setInt(2, id); // id 입력

            statement.executeUpdate(); // 쿼리문 실행
        } catch (SQLException e) {
            log.error("updateCategory error={}", e); // Exception 발생시 로그
            throw e;
        } finally {
            closeResources(connection, statement, null); // 자원 반환
        }
    }

    // 특정 id를 가진 카테고리 삭제: Delete
    public void DeleteCategory(int id) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;

        // where에 id값을 조건으로 준 쿼리문 작성
        String sql = "delete from categories where id = ?";

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, id); // id값에 대응하는 값 입력

            statement.executeUpdate(); // 쿼리 실행
        } catch (SQLException e) {
            log.error("deleteCategory error={}", e); // Exception 발생 시 롤그
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
