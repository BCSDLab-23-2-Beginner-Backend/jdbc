package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();

    @Test
    void save() {
        User user = new User(1, "userA", "1@koreatech.ac.kr", "1234", LocalDateTime.now());
        Integer newId = userRepository.save(user);
        assertThat(newId).isEqualTo(1);
    }

    @Test
    void findById() {
        User findUser = userRepository.findById(1);
        log.info("findUser={}", findUser);
        assertThat(findUser.getId()).isEqualTo(1);
    }

    @Test
    void updateUsername(){
        userRepository.updateUsername(1, "updateA");
        User updateUser = userRepository.findById(1);
        log.info("updateUser={}", updateUser);
        assertThat(updateUser.getUsername()).isEqualTo("updateA");
    }

    @Test
    void deleteUser(){
        userRepository.deleteUser(1);
        User deleteUser = userRepository.findById(1);
        assertThat(deleteUser.getId()).isNull();
    }
}