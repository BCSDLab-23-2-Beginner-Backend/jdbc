package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Category;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryRepositoryTest {

    CategoryRepository categoryRepository = new CategoryRepository();

    @Test
    void createCategory() throws SQLException {
        Category category1 = new Category(1, "Category1");
        Category newCategory = categoryRepository.createCategory(category1); // #1 새 Category를 생성
        assertThat(newCategory.getId()).isEqualTo(1); // #2 테스트 코드 작성으로, 만들어졌다면 통과
    }
}