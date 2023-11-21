package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import static org.junit.jupiter.api.Assertions.*;

class newUserRepositoryTest {

    @Test
    void save() {
        User user = new User("userA","bcsd@koreatech.ac.kr","1234");

    }

    @Test
    void createUser() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateUsername() {
    }

    @Test
    void deleteUser() {
    }
}