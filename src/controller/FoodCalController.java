package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import model.FoodCalModel;
import model.Makanan;
import view.FoodCalView;

import java.awt.Desktop;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class FoodCalController {

    private final FoodCalModel model;

public FoodCalController(FoodCalModel model) {
    this.model = model;
}

    private Map<String, String> sessions =
        new ConcurrentHashMap<>();

    private HttpServer server;

    // Menjalankan server
    public void jalankanAplikasi() {

        try {

            server = HttpServer.create(
                new InetSocketAddress("localhost", 8080), 0
            );

            server.createContext("/", this::handleHome);
            server.createContext(
                "/api/register", this::handleRegister
            );
            server.createContext(
                "/api/login", this::handleLogin
            );
            server.createContext(
                "/api/makanan", this::handleMakanan
            );

            server.setExecutor(
                Executors.newCachedThreadPool()
            );

            server.start();

            String url = "http://localhost:8080/";

            System.out.println(
                "FoodCal berjalan di " + url
            );

            if (Desktop.isDesktopSupported()
                    && Desktop.getDesktop().isSupported(
                        Desktop.Action.BROWSE)) {

                Desktop.getDesktop().browse(
                    java.net.URI.create(url)
                );
            }

        } catch (IOException e) {

            System.err.println(
                "Gagal menjalankan server: " + e.getMessage()
            );
        }
    }

    // Menampilkan halaman HTML
    private void handleHome(HttpExchange ex)
            throws IOException {

        send(
            ex,
            200,
            "text/html; charset=UTF-8",
            new FoodCalView().getHtml()
        );
    }

    // REGISTRASI
    private void handleRegister(HttpExchange ex)
            throws IOException {

        Map<String, String> form = readForm(ex);

        String nama = form.getOrDefault("nama", "").trim();
        String password = form.getOrDefault("password", "");

        try {

            boolean berhasil =
                model.registrasi(nama, password);

            if (!berhasil) {

                sendJson(
                    ex, 409,
                    jsonMessage(false,
                        "Username sudah digunakan.")
                );

                return;
            }

            sendJson(
                ex, 201,
                jsonMessage(true,
                    "Registrasi berhasil. Silakan login.")
            );

        } catch (IllegalArgumentException e) {

            sendJson(ex, 400,
                jsonMessage(false, e.getMessage()));

        } catch (Exception e) {

            e.printStackTrace();

            sendJson(ex, 500,
                jsonMessage(false, "Registrasi gagal."));
        }
    }

    // LOGIN
    private void handleLogin(HttpExchange ex)
            throws IOException {

        Map<String, String> form = readForm(ex);

        String nama = form.get("nama");
        String password = form.get("password");

        try {

            boolean berhasil =
                model.login(nama, password);

            if (!berhasil) {

                sendJson(
                    ex, 401,
                    jsonMessage(false,
                        "Username atau password salah.")
                );

                return;
            }

            String token = UUID.randomUUID().toString();

            sessions.put(token, nama);

            sendJson(
                ex, 200,
                "{\"success\":true,\"token\":\""
                    + token + "\"}"
            );

        } catch (Exception e) {

            e.printStackTrace();

            sendJson(
                ex, 500,
                jsonMessage(false, "Login gagal.")
            );
        }
    }

    // DATA MAKANAN
    private void handleMakanan(HttpExchange ex)
            throws IOException {

        if (!authorized(ex)) {

            sendJson(
                ex, 401,
                jsonMessage(false, "Silakan login kembali.")
            );

            return;
        }

        try {

            String method = ex.getRequestMethod();

            // Menampilkan makanan
            if ("GET".equals(method)) {

                List<Makanan> list =
                    model.getSemuaMakanan();

                StringBuilder json =
                    new StringBuilder("[");

                for (int i = 0; i < list.size(); i++) {

                    Makanan m = list.get(i);

                    if (i > 0) {
                        json.append(",");
                    }

                    json.append("{\"id\":")
                        .append(m.getId())
                        .append(",\"nama\":\"")
                        .append(escape(m.getNama()))
                        .append("\",\"kalori\":")
                        .append(m.getKalori())
                        .append("}");
                }

                json.append("]");

                sendJson(ex, 200, json.toString());
            }

            // Menambahkan makanan
            else if ("POST".equals(method)) {

                Map<String, String> form = readForm(ex);

                String nama = form.get("nama");

                double kalori = Double.parseDouble(
                    form.get("kalori")
                );

                model.tambahMakanan(nama, kalori);

                sendJson(
                    ex, 201,
                    jsonMessage(true,
                        "Makanan berhasil ditambahkan.")
                );
            }

            // Menghapus makanan
            else if ("DELETE".equals(method)) {

                String query =
                    ex.getRequestURI().getRawQuery();

                Map<String, String> params =
                    parseForm(query == null ? "" : query);

                int id = Integer.parseInt(
                    params.getOrDefault("id", "0")
                );

                boolean berhasil =
                    model.hapusMakanan(id);

                sendJson(
                    ex,
                    berhasil ? 200 : 404,
                    jsonMessage(
                        berhasil,
                        berhasil
                            ? "Makanan berhasil dihapus."
                            : "Makanan tidak ditemukan."
                    )
                );
            }

            else {

                sendJson(ex, 405,
                    jsonMessage(false, "Metode tidak diizinkan."));
            }

        } catch (Exception e) {

            e.printStackTrace();

            sendJson(
                ex, 500,
                jsonMessage(false, "Operasi database gagal.")
            );
        }
    }

    // Memeriksa sesi login
    private boolean authorized(HttpExchange ex) {

        String header =
            ex.getRequestHeaders().getFirst("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return false;
        }

        String token = header.substring(7).trim();

        return sessions.containsKey(token);
    }

    // Membaca data form
    private Map<String, String> readForm(HttpExchange ex)
            throws IOException {

        String body = new String(
            ex.getRequestBody().readAllBytes(),
            StandardCharsets.UTF_8
        );

        return parseForm(body);
    }

    private Map<String, String> parseForm(String body) {

        Map<String, String> result = new HashMap<>();

        if (body.isEmpty()) {
            return result;
        }

        for (String pair : body.split("&")) {

            String[] parts = pair.split("=", 2);

            String key = URLDecoder.decode(
                parts[0], StandardCharsets.UTF_8
            );

            String value = parts.length > 1
                ? URLDecoder.decode(
                    parts[1], StandardCharsets.UTF_8
                  )
                : "";

            result.put(key, value);
        }

        return result;
    }

    // Membuat pesan JSON
    private String jsonMessage(
            boolean success, String message) {

        return "{\"success\":" + success
            + ",\"message\":\"" + escape(message) + "\"}";
    }

    private String escape(String s) {

        return s.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r");
    }

    // Mengirim respons
    private void sendJson(
            HttpExchange ex, int status, String body)
            throws IOException {

        send(
            ex,
            status,
            "application/json; charset=UTF-8",
            body
        );
    }

    private void send(
            HttpExchange ex,
            int status,
            String contentType,
            String body) throws IOException {

        byte[] bytes = body.getBytes(
            StandardCharsets.UTF_8
        );

        ex.getResponseHeaders().set(
            "Content-Type", contentType
        );

        ex.getResponseHeaders().set(
            "Cache-Control", "no-store"
        );

        ex.sendResponseHeaders(status, bytes.length);

        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }
}