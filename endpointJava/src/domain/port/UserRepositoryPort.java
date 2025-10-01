package domain.port;

import domain.model.User;

import java.util.List;

public interface UserRepositoryPort {
    void save(User user);
    List<User> findAll();
}
