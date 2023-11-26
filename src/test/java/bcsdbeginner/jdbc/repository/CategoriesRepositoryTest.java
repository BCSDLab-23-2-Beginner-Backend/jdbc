package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Categories;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CategoriesRepositoryTest {

    // CategoriesRepository 객체 생성
    CategoriesRepository categoriesRepository = new CategoriesRepository();

    @Test
    void createCategories() throws SQLException {
        // 이름이 java인 카테고리 객체 생성
        Categories categories1 = new Categories("java");
        // categoriesRepository의 createCategories 호출
        Categories newCategories =  categoriesRepository.createCategories(categories1);

        assertThat(newCategories.getName()).isEqualTo("java");

    }
}
