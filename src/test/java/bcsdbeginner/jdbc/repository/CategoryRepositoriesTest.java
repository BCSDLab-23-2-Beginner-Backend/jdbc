package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.Categories;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

class CategoryRepositoriesTest {
    CategoriesRepository categoriesRepository = new CategoriesRepository();

    @Test
    void createCategories() throws SQLException {
        Categories categories = new Categories("잡담");
        Categories createdCategories = categoriesRepository.createCategories(categories);
        assertEquals(categories.getName(), createdCategories.getName());
    }
}