package de.medieninformatik.prog3.client.model;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Klasse die sich um die Rest-Implementierung der Anwendung kuemmert.
 * Dadurch wird ein Datentransfer zwischen Client und Server ermoeglicht.
 */
public class CRUDMovieDataRest implements CRUDMovieData {

    private final Client client;
    private final String baseURI;

    /**
     * Konstruktor der CRUDMovieDataRest-Klasse
     */
    public CRUDMovieDataRest() {
        this.baseURI =  "http://localhost:8080/rest";
        this.client = ClientBuilder.newClient();
    }

    /**
     * schickt Daten zum erstellen eines neuen Datenbankeintrags vom Client zum Server
     * uebergibt einen POST-Befehl ueber http://localhost:8080/rest/movies
     * @param movieData uebergebener Datensatz
     * @return response.readEntity(MovieData.class)
     * @throws CRUDException
     */
    @Override
    public MovieData createMovieData(MovieData movieData) throws CRUDException {
        try {
            WebTarget target = getTarget("POST", "/movies");
            Response response = target.request(MediaType.APPLICATION_XML).post(Entity.entity(movieData, MediaType.APPLICATION_XML));
            if (status(response) == 200) {
                return response.readEntity(MovieData.class);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Methode die anhand einer Id eine Suchanfrage an den Server sendet & die Serverantwort zurueckgibt
     * @param id uebergebene ID
     * @return response.readEntity(MovieData.class)
     * @throws CRUDException
     */
    @Override
    public MovieData readMovieData(int id) throws CRUDException {
        try {
            WebTarget target = getTarget("GET", "/movies?id=" + id);
            Response response = target.request(MediaType.APPLICATION_XML).get();
            if (status(response) == 200) {
                return response.readEntity(MovieData.class);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Mehtode die einen Such-String an den Server uebermittelt und eine passende Liste von Datensaetzen zurueckliefert
     * @param filter
     * @return response.readEntity(MovieList.class)
     * @throws CRUDException
     */
    @Override
    public MovieList readMovieList(String filter) throws CRUDException {
        try {
            WebTarget target = getTarget("GET", "/movies/list?filter=" + filter);
            Response response = target.request(MediaType.APPLICATION_XML).get();
            if (status(response) == 200) {
                return response.readEntity(MovieList.class);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Methode die zum Editieren von Datensaetzen aufgerufen werden kann, teilt dem server mit um welchen Eintrag
     * in der Datenbank es sich handelt & empfaengt den geaenderten Datensatz wieder
     * @param movieData
     * @return
     * @throws CRUDException
     */
    @Override
    public MovieData updateMovieData(MovieData movieData) throws CRUDException {
        try {
            WebTarget target = getTarget("PUT", "/movies");
            Response response = target.request(MediaType.APPLICATION_XML).put(Entity.entity(movieData, MediaType.APPLICATION_XML));
            if (status(response) == 200) {
                return response.readEntity(MovieData.class);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Methode die zum Loeschen von Datensaetzen aufgerufen werden kann, teilt dem server mit um welchen Eintrag
     * in der Datenbank es sich handelt & empfaengt den geaenderten Datensatz wieder
     * @param id
     * @return
     * @throws CRUDException
     */
    @Override
    public MovieData deleteMovieData(int id) throws CRUDException {
        try {
            WebTarget target = getTarget("DELETE", "/movies?id=" + id);
            Response response = target.request(MediaType.APPLICATION_XML).delete();
            if (status(response) == 200) {
                return response.readEntity(MovieData.class);
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private WebTarget getTarget(String crud, String uri) {
        System.out.printf("%n--- %s %s%s%n", crud ,baseURI, uri);
        return client.target(baseURI + uri);
    }

    private int status(Response response) {
        int code = response.getStatus();
        String reason = response.getStatusInfo().getReasonPhrase();
        System.out.printf("Status: %d %s%n", code, reason);
        return code;
    }

}
