package de.medieninformatik.prog3.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Main Klasse des Server-Moduls,
 * Setzt mit Hilfe von jersey und grizzly einen webserver auf.
 * besitzt nur eine main-Methode, die den server startet.
 *
 */
public class Main {
    public static HttpServer SERVER = null;

    /**
     * main-Methode der Main-Klasse
     * @param args
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        Logger.getLogger("org.glassfish").setLevel(Level.SEVERE);
        URI baseURI = new URI("http://0.0.0.0:8080/rest");
        ResourceConfig config = ResourceConfig.forApplicationClass(MovieServerApplication.class);
        SERVER = GrizzlyHttpServerFactory.createHttpServer(baseURI, config);
        StaticHttpHandler handler = new StaticHttpHandler("web");
        handler.setFileCacheEnabled(false);
        ServerConfiguration serverConfig = SERVER.getServerConfiguration();
        serverConfig.addHttpHandler(handler, "/");
        if (!SERVER.isStarted()) {
            SERVER.start();
        }
        System.out.println("Um den Server zu stoppen geben sie folgende URL im Browser ein:");
        System.out.println("http://localhost:8080/rest/movies/stopServer");
    }
}
