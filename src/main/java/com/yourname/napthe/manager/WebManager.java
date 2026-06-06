package com.yourname.napthe.manager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.yourname.napthe.NapThePlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class WebManager {
    private final NapThePlugin plugin;
    private HttpServer server;

    public WebManager(NapThePlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        if (!plugin.getConfig().getBoolean("web.enabled")) return;

        int port = plugin.getConfig().getInt("web.port", 8080);

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext(plugin.getConfig().getString("web.path", "/callback"), new CallbackHandler());
            server.createContext("/", new StaticFileHandler());
            server.createContext("/submit", new SubmitHandler());
            server.setExecutor(null);
            server.start();
            plugin.getLogger().info("Web Server đã chạy tại: https://web.yankaree.indevs.in/");
        } catch (IOException e) {
            plugin.getLogger().severe("Không thể khởi động Web Server: " + e.getMessage());
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    private class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File file = new File(plugin.getDataFolder(), "napthe.html");
            if (!file.exists()) {
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            byte[] bytes = Files.readAllBytes(file.toPath());
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    private class SubmitHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }

            // Xử lý POST request từ form (Sử dụng đơn giản qua query hoặc body)
            // Trong thực tế sẽ dùng thư viện để parse body, ở đây ta giả định gởi qua API
            String response = "{\"status\":\"success\", \"message\":\"Thẻ đã được gửi!\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private class CallbackHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            
            // Log callback (Để debug)
            plugin.getLogger().info("Nhận callback: " + params.toString());

            // Xử lý callback từ TheSieuToc (Ví dụ)
            // Thường có: status, request_id (hoặc content), amount, v.v.
            if (params.containsKey("status") && params.get("status").equals("success")) {
                String playerName = params.get("content"); // Content thường chứa tên người chơi
                int realAmount = Integer.parseInt(params.get("amount"));

                Bukkit.getScheduler().runTask(plugin, () -> {
                    plugin.getCardManager().rewardPlayer(playerName, realAmount);
                });
            }

            String response = "OK";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private Map<String, String> queryToMap(String query) {
            Map<String, String> result = new HashMap<>();
            if (query == null) return result;
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }
}
