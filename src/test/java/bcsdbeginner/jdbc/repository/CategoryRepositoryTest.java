package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Board;
import bcsdbeginner.jdbc.domain.Categories;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRepositoryTest {
    CategoryRepository categoryRepository = new CategoryRepository();

    @BeforeEach
    void clearDB() throws SQLException {
        Helper.clearDB();
    }

    @Test
    void save() throws SQLException {
        Categories categoryA = new Categories("인사 게시판");
        Categories categoryB = new Categories("작별 게시판");

        categoryRepository.save(categoryA);
        categoryRepository.save(categoryB);

        Assertions.assertThat("인사 게시판").isEqualTo(categoryA.getName());
    }

    @Test
    void delete() throws SQLException {
        Categories categoryA = new Categories("인사 게시판");
        Categories categoryB = new Categories("작별 게시판");

        categoryRepository.save(categoryA);
        categoryRepository.save(categoryB);

        categoryRepository.delete(1);
        //Assertions.assertThat(id).isEqualTo(1);
    }

    @Test
    void update() throws SQLException {
        Categories categoryA = new Categories("인사 게시판");
        Categories categoryB = new Categories("작별 게시판");

        categoryRepository.save(categoryA);
        categoryRepository.save(categoryB);

        categoryRepository.update(1, "name","인사하세요오");
        //Assertions.assertThat(id).isEqualTo(1);
    }

    @Test
    void read() {
    }

    static class Helper {
        public static void clearDB() throws SQLException {
            Connection connection = null;
            Statement statement = null;
            String sql1 = "delete from categories";
            String sql2 = "alter table categories AUTO_INCREMENT = 1";//DB에 넘길 SQL 작성

            try {
                connection = DBConnectionManager.getConnection();//DriverManger 통해서 DB커넥션 생성
                statement = connection.createStatement();//SQL실행 하기위한 객체 PrepareStatement 생성

                statement.addBatch(sql1);
                statement.addBatch(sql2);

             /*   statement.setString(1, table);//DB컬럼과 자바 오브젝트 필드 바인딩
                statement.setString(2, table);*/

                statement.executeBatch();
            } catch (SQLException e) {
                //log.error("clearDB error={}", e);
                throw e;
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (Exception e) {
                        //log.error("error", e);
                    }
                }

                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        //log.error("error", e);
                    }
                }
            }
        }
    }
}
