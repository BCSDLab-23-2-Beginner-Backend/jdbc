package bcsdbeginner.jdbc.repository;

import bcsdbeginner.jdbc.domain.User;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    UserRepository userRepository = new UserRepository();

    @Test
    void createUser() throws SQLException {
        User user2 = new User("우정호", "wjh0423@koreatech.ac.kr", "5678");
        User createdUser2 = userRepository.createUser(user2);
        assertEquals(user2.getUsername(), createdUser2.getUsername());
        assertEquals(user2.getEmail(), createdUser2.getEmail());
        assertEquals(user2.getPassword(), createdUser2.getPassword());
    }
}
