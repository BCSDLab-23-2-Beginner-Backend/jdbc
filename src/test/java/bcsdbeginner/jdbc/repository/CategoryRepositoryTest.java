package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CategoryRepositoryTest {
    CategoryRepository categoryRepository = new CategoryRepository();
    @Test
    void createCategory() throws SQLException {
        Category beginnerCategory = new Category("Free");
        Category newCategory = categoryRepository.createCategory(beginnerCategory);
        assertThat(newCategory.getName()).isEqualTo("Free");
    }
}