package yose;

import com.google.gson.Gson;
import com.vtence.molecule.WebServer;
import com.vtence.molecule.routing.DynamicRoutes;

import java.io.IOException;
import java.net.URI;

public class Yose {

    private final WebServer server;

    public Yose(int port) {
        this.server = WebServer.create(port);
    }

    public void start() throws IOException {
        final Gson gson = new Gson();

        server.start(new DynamicRoutes() {{
            get("/").to((request, response) -> response.body("Hello Yose"));
            get("/ping").to(new Ping(gson)::pong);
        }});
    }

    public URI uri() {
        return server.uri();
    }

    public void stop() throws IOException {
        server.stop();
    }

    public static Yose launch(String... args) throws IOException {
        Yose yose = new Yose(port(args));
        yose.start();
        return yose;
    }

    private static int port(String[] args) {
        return args.length > 0 ? Integer.parseInt(args[PORT]) : 8080;
    }

    private static final int PORT = 0;

    public static void main(String[] args) throws IOException {
        System.out.print("To play the game visit " + Yose.launch(args).uri());
    }

}