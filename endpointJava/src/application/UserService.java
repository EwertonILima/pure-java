package application;

import domain.model.User;
import domain.port.UserRepositoryPort;

import java.util.List;

public class UserService {
    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort){
        this.userRepositoryPort = userRepositoryPort;
    }

    public void createUser(String name){
        User user = new User(name);
        userRepositoryPort.save(user);
    }

    public List<User> getUsers(){
        return userRepositoryPort.findAll();
    }
}


