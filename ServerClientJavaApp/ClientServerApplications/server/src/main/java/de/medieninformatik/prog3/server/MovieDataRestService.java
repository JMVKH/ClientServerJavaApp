package de.medieninformatik.prog3.server;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Diese Klasse stellt ein CRUD (create, update, delete) Interface für die Filmdaten zur Verfügung.
 */

@Path("movies")
public class MovieDataRestService {

    /**
     * Der default Konstruktor
     */
    public MovieDataRestService() {
    }

    /**
     * Methode um ein Objekt zu erstellen
     * @param movieData
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response createMovieData(MovieData movieData) {
        MovieData resultData = MovieDB.createMovieData(movieData);
        return Response.ok(resultData).build();
    }

    /**
     * Methode um ein einzelnes Objekt ueber eine Id zu finden
     * @param id
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response readMovieData (@DefaultValue("0") @QueryParam("id") Integer id) {
        MovieData resultData = MovieDB.readMovieData(id);
        if (resultData != null) {
            return Response.ok(resultData).build();
        }
        return Response.noContent().build();
    }

    /**
     * Methode um eine Liste von Objekten nach Filter-Kriterien zu durchsuchen
     * @param filter
     * @return
     */
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_XML)
    public Response readMovieDataList(@DefaultValue("") @QueryParam("filter") String filter) {
        MovieList resultList = MovieDB.readMovieList(filter);
        if (!resultList.isEmpty()) {
            return Response.ok(resultList).build();
        }
        return Response.noContent().build();
    }

    /**
     * Methode zum Aktualisieren eines Objekts
     * @param movieData
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateMovieData(MovieData movieData) {
        System.out.println(movieData);
        MovieData resultData = MovieDB.updateMovieData(movieData);
        return Response.ok(resultData).build();
    }

    /**
     * methode zum Loeschen eines Objekts
     * @param id
     * @return
     */
    @DELETE
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response deleteMovieData(@DefaultValue("0") @QueryParam("id") Integer id) {
        MovieData resultData = MovieDB.readMovieData((id));
        if (resultData != null) {
            MovieDB.deleteMovieData(id);
            return Response.ok(resultData).build();
        }
        return Response.noContent().build();
    }

    /**
     * Methode um den Server zu stoppen
     * @return
     */
    @GET
    @Path("/stopServer")
    @Produces(MediaType.TEXT_PLAIN)
    public Response stopServer() {
        if (Main.SERVER.isStarted()) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    MovieDB.getInstance().close();
                    Main.SERVER.shutdownNow();
                }
            });
            t.start();
        }
        return Response.ok("Server stopped").build();
    }

}
