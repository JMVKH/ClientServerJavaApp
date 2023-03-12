package de.medieninformatik.prog3.client.model;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Interface welches Methoden fuer die CRUDMovieDataRest- und CRUDMovieDataMock-Klassen enthaelt.
 * Macht es dadurch einfacher zwischen der Mock-Implementierung und er der Rest-Implementierung zu wechseln.
 */
public interface CRUDMovieData {

    public MovieData createMovieData(MovieData data) throws CRUDException;
    public MovieData readMovieData(int id) throws CRUDException;
    public MovieList readMovieList(String filter) throws CRUDException;
    public MovieData updateMovieData(MovieData data) throws CRUDException;
    public MovieData deleteMovieData(int id) throws CRUDException;

}
