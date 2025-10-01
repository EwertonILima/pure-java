package adapter.in.http;

import application.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserPostHandler implements HttpHandler {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserPostHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();
        Map<String, String> body = objectMapper.readValue(inputStream, Map.class);
        String name = body.get("name");

        userService.createUser(name);

        String response = objectMapper.writeValueAsString(Map.of("message", "User created"));
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(201, response.length());
        httpExchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
        httpExchange.close();
    }
}
