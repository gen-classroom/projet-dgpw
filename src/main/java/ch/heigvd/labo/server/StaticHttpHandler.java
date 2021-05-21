package ch.heigvd.labo.server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;

public class StaticHttpHandler implements HttpHandler {
    private final String buildDir;

    public StaticHttpHandler(String buildDir) {
        this.buildDir = buildDir;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        File path = new File(buildDir + "/" + uri);

        Headers h = httpExchange.getResponseHeaders();
        h.add("Content-Type", "text/html");

        OutputStream out = httpExchange.getResponseBody();

        if (path.exists()) {
            httpExchange.sendResponseHeaders(200, path.length());
            out.write(Files.readAllBytes(path.toPath()));
        } else {
            System.err.println("File not found: " + path.getAbsolutePath());
            httpExchange.sendResponseHeaders(404, 0);
            out.write("404 File not found.".getBytes());
        }
        out.close();
    }
}
