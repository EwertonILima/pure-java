import adapter.in.http.RouterHandler;
import adapter.in.http.UserGetAllHandler;
//import adapter.in.http.UserPostController;
import adapter.in.http.UserPostHandler;
import application.UserService;
import com.sun.net.httpserver.HttpServer;
import infra.InMemoryUserRepository;

import java.io.IOException;
import java.net.InetSocketAddress;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
//    public static void main(String[] args) throws IOException {
//        var userRepository = new InMemoryUserRepository();
//        var userService = new UserService(userRepository);
//
//        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
//        server.createContext("/users", new UserPostController(userService));
//        server.setExecutor(null);
//        server.start();
//
//        System.out.println("Server started on http://localhost:8080/users");
//    }

    public static void main(String[] args) throws IOException {
        var userRepository = new InMemoryUserRepository();
        var userService = new UserService(userRepository);

        var router = new RouterHandler();
        router.register("POST", "/users", new UserPostHandler(userService));
        router.register("GET", "/users", new UserGetAllHandler(userService));

        HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
        server.createContext("/", router);
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on http://localhost:8080");
    }
};