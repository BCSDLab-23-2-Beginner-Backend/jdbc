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
    void save() {   // 유저 생성(Create)
        User user = new User(1, "userA", "1@koreatech.ac.kr", "1234", LocalDateTime.now());
        // 유저 객체 생성
        Integer newId = userRepository.save(user);  // 생성한 유저 객체를 데이터베이스에 저장
        assertThat(newId).isEqualTo(1);     // 저장된 유저의 id가 1인지 확인(1이 아니면 예외 발생)
    }

    @Test
    void findById() {   // 유저 탐색(Read)
        User findUser = userRepository.findById(1); // id가 1인 유저 탐색
        log.info("findUser={}", findUser);          // 찾은 유저 객체에 대한 정보를 로그로 출력
        assertThat(findUser.getId()).isEqualTo(1);  // 찾은 유저의 id가 1인지 확인(1이 아니면 예외 발생)
    }

    @Test
    void updateUsername(){  // 유저 수정(Update)
        userRepository.updateUsername(1, "updateA");
        // id가 1인 유저의 이름을 "updateA"로 수정
        User updateUser = userRepository.findById(1);   // id가 1인 유저 탐색
        log.info("updateUser={}", updateUser);          // 찾은 유저 객체에 대한 정보를 로그로 출력
        assertThat(updateUser.getUsername()).isEqualTo("updateA");
        // 찾은 유저의 이름이 updateA인지 확인(updateA가 아니면 에외 발생)
    }

    @Test
    void deleteUser(){  // 유저 삭제(Delete)
        userRepository.deleteUser(1);   // id가 1인 유저 삭제
        User deleteUser = userRepository.findById(1);   // id가 1인 유저 탐색
        assertThat(deleteUser.getId()).isNull();    // 찾은 유저의 id가 Null인지 확인(Null이 아니면 예외 발생)
    }
}