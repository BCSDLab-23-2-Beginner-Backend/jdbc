package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CategoryRepositoryTest {

    Repository<Category> categoryRepository = new CategoryRepository();

    @BeforeEach
    void clearDB() throws SQLException {
        Helper.clearDB();
    }

    @Test
    void create() throws SQLException {
        Category category1 = new Category("category");
        Category newCategory = categoryRepository.create(category1);

        assertThat(newCategory.getName()).isEqualTo("category");
    }

    @Test
    void findById() throws SQLException {
        Category category1 = new Category("category");
        categoryRepository.create(category1);

        Category findCategory = categoryRepository.findById(1);

        assertThat(findCategory.getId()).isEqualTo(1);
    }

    @Test
    void update() throws SQLException {
        Category category1 = new Category("category");
        categoryRepository.create(category1);

        category1.setName("updateCategory");

        categoryRepository.update(1, category1);
        Category updateCategory = categoryRepository.findById(1);
        log.info("board={}", updateCategory);
        assertThat(updateCategory.getName()).isEqualTo("updateCategory");
    }

    @Test
    void delete() throws SQLException {
        categoryRepository.delete(1);

        Integer deleteId = categoryRepository.findById(1).getId();

        assertThat(deleteId).isNull();
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
                log.error("clearDB error={}", e);
                throw e;
            } finally {
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
    }
}