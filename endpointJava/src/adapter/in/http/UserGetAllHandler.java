package adapter.in.http;

import application.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import domain.model.User;

import java.io.IOException;
import java.util.List;

public class UserGetAllHandler implements HttpHandler {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserGetAllHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        List<User> users = userService.getUsers();
        String response = objectMapper.writeValueAsString(users);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, response.length());
        httpExchange.getResponseBody().write(response.getBytes());
        httpExchange.close();
    }
}
