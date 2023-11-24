package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.DBConnection.DBConnectionManager;
import bcsdbeginner.jdbc.domain.Categories;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CategoriesRepositoryTest {

    CategoriesRepository categoriesRepository = new CategoriesRepository();

    @BeforeEach
    void clearDB() throws SQLException {
        Helper.clearDB();
    }

    @Test
    void createCategories() throws SQLException{
        Categories categories1 = new Categories("study");
        Categories newCategories = categoriesRepository.createCategories(categories1);
        newCategories.setCreate_at(LocalDateTime.now());
        assertThat(categories1.getName()).isEqualTo("study");
    }

    static class Helper {
        public static void clearDB() throws SQLException {
            Connection connection = null;
            Statement statement = null;
            String sql1 = "delete from categories";
            String sql2 = "alter table categories AUTO_INCREMENT = 1";

            try {
                connection = DBConnectionManager.getConnection();
                statement = connection.createStatement();

                statement.addBatch(sql1);
                statement.addBatch(sql2);

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