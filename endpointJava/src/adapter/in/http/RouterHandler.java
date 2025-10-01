package adapter.in.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RouterHandler implements HttpHandler {

    private final Map<String,Map<String, HttpHandler>> routes = new HashMap<>();

    public void register(String method, String path, HttpHandler handler) {
        method = method.toUpperCase();
        routes.computeIfAbsent(method, k -> new HashMap<>()).put(path,handler);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod().toUpperCase();
        String path = exchange.getRequestURI().getPath();

        HttpHandler handler = null;

        if(routes.containsKey(method)) {
            handler = routes.get(method).get(path);
        }
        if(handler != null) {
            handler.handle(exchange);
        }
        else {
            String response = "404 Not Found: " + method + " " + path;
            exchange.sendResponseHeaders(404, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }
}
