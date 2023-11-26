package bcsdbeginner.jdbc.repository;

import java.sql.SQLException;

public interface Repository<T> {
    T create(T newT) throws SQLException;
    T findById(Integer id) throws SQLException;
    void update(Integer id, T newT) throws SQLException;
    void delete(Integer id) throws SQLException;
}
