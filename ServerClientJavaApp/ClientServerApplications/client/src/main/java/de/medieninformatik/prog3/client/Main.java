package de.medieninformatik.prog3.client;

import de.medieninformatik.prog3.client.gui.App;
import de.medieninformatik.prog3.client.model.CRUDFactory;
import de.medieninformatik.prog3.client.model.CRUDMovieData;
import de.medieninformatik.prog3.client.model.MovieData;
import de.medieninformatik.prog3.client.model.MovieList;
import javafx.application.Application;

import java.util.Calendar;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Die Main-Klasse der client(MovieClient) Anwendung.
 * Startet den JavaFX basierten Client, kann über eine Rest API mit der server(MovieServer) Anwendung
 * kommunizieren und erlaubt kann verschiedene Rechte vergeben. Diese erlauben entweder einen eingeschraenkten
 * Nutzer Zugriff, oder einen unbeschränkten Administrator Zugriff.
 */
public class Main {

    private static CRUDMovieData crudImpl = null;

    /**
     * main-Methode der Main-Klasse, startet den JavaFx basierten Client
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    /**
     * Methode die waehrend der Mock-Implementation genutzt wurde
     * @param args
     */
    public static void xmain(String[] args) {
        System.out.println("--> Create rest impl");
        Main.crudImpl = CRUDFactory.getInstance(CRUDFactory.CRUDTypes.rest);

        System.out.println("--> Read movie data by id");
        MovieData movieData = Main.crudImpl.readMovieData(2);
        System.out.println(movieData);

        System.out.println("--> List all movies");
        Main.listData("");
        System.out.println("--> List movies containing 'war'");
        Main.listData("war");
        System.out.println("--> List movies containing 'spiel'");
        Main.listData("spiel");

        System.out.println("--> Create new movie data");
        Calendar cal = Calendar.getInstance();
        movieData = new MovieData();
        movieData.setTitle("Iron Man");
        movieData.setDirector("Jon Favreau");
        movieData.setGenre("Action Fantasy");
        cal.set(2008, 4, 1);
        movieData.setReleaseDate(cal.getTime());
        movieData.setRating(5);
        Main.crudImpl.createMovieData(movieData);

        System.out.println("--> List all movies");
        Main.listData("");

        System.out.println("--> Update movie data");
        movieData = Main.crudImpl.readMovieData(1);
        movieData.setTitle("WAR OF THE WORLDS");
        movieData.setRating(2);
        movieData = Main.crudImpl.updateMovieData(movieData);
        System.out.println("--> List all movies");
        Main.listData("");

        System.out.println("--> Delete movie data");
        movieData = Main.crudImpl.deleteMovieData(2);
        System.out.println("--> List all movies");
        Main.listData("");
    }

    /**
     * Methode die waehrend der Mock-Implementation genutzt wurde
     */
    private static void listData(String filter) {
        MovieList movieList = Main.crudImpl.readMovieList(filter);
        if (movieList != null) {
            movieList.get().forEach(item -> {
                System.out.println("item: " + item.toString());
            });
        }
    }

}
