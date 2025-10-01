package infra;

import domain.model.User;
import domain.port.UserRepositoryPort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryUserRepository implements UserRepositoryPort {
    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
        System.out.println("User created: " + user.getName());
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }
}
