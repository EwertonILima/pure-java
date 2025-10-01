package adapter.in.http;

import application.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class UserPostController implements HttpHandler {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserPostController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStream inputStream = exchange.getRequestBody();
        Map<String, String> jsonBody = objectMapper.readValue(inputStream, Map.class);
        String name = jsonBody.get("name");

        try {
            userService.createUser(name);
            String response = objectMapper.writeValueAsString(Map.of("message", "User created successful"));
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        } catch (Exception e) {
            String response = objectMapper.writeValueAsString(Map.of("error", e.getMessage()));
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(400, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}
