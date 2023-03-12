package de.medieninformatik.prog3.client.model;

import java.text.DateFormat;
import java.util.*;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Klasse die sich um die Mock-Implementierung der Anwendung kuemmert.
 * Dadurch kann ohne eine Server Konnektivitaet an dem Programm gearbeitet werden.
 */
public class CRUDMovieDataMock implements CRUDMovieData {
    private static int idCounter = 0;
    private Map<Integer, MovieData> movieMap = new HashMap<>();

    /**
     * Konstruktor
     */
    public CRUDMovieDataMock() {
        generateTestData();
    }

    /**
     * fuegt der HashMap einen neuen Eintrag hinzu
     * @param data
     * @return movieMap.put(data.getUniqueId(), data);
     * @throws CRUDException
     */
    @Override
    public MovieData createMovieData(MovieData data) throws CRUDException {
        data.setUniqueId(idCounter++);
        return movieMap.put(data.getUniqueId(), data);
    }

    /**
     * Methode die anhand einer Id nach einem Eintrag in der HashMap suchen kann und diesen dann returnt
     * @param id
     * @return
     * @throws CRUDException
     */
    @Override
    public MovieData readMovieData(int id) throws CRUDException {
        return movieMap.get(id);
    }

    /**
     * Methode die eine Suchfunktion beinhaltet
     * @param filter Such-String
     * @return movieList - eine Liste mit Hashmap Gegenstaenden die im suchparameter liegen
     * @throws CRUDException
     */
    @Override
    public MovieList readMovieList(String filter) throws CRUDException {
        MovieList movieList = new MovieList();
        String searchFilter = filter == null ? "" : filter.trim().toLowerCase();
        movieMap.values().forEach((item) -> {
            boolean ok = false;
            if (!searchFilter.isEmpty()) {
                String title = item.getTitle() == null ? "" : item.getTitle().toLowerCase();
                String director = item.getDirector() == null ? "" : item.getDirector().toLowerCase();
                String genre = item.getGenre() == null ? "" : item.getGenre().toLowerCase();
                String rating = "" + item.getRating();
                DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
                String releaseDate = df.format(item.getReleaseDate());
                ok |= title.indexOf(searchFilter) >= 0;
                ok |= director.indexOf(searchFilter) >= 0;
                ok |= genre.indexOf(searchFilter) >= 0;
                ok |= rating.indexOf(searchFilter) >= 0;
                ok |= releaseDate.indexOf(searchFilter) >= 0;
            } else {
                ok = true;
            }
            if (ok) {
                movieList.add(item);
            }
        });
        return movieList;
    }

    /**
     * Methode die zum Editieren von Datensaetzen aufgerufen werden kann
     * @param data vollstaendiger Datensatz passend zu einer einzelnen ID
     * @return movieMap.put(id, data)
     * @throws CRUDException
     */
    @Override
    public MovieData updateMovieData(MovieData data) throws CRUDException {
        int id = data.getUniqueId();
        if (movieMap.containsKey(id)) {
            movieMap.remove(id);
            return movieMap.put(id, data);
        } else {
            throw new CRUDException("No data found for update");
        }
    }

    /**
     * Methode zum loeschen von vorhandenen Eintraegen von der HashMap
     * @param id
     * @return movieMap.remove(id)
     * @throws CRUDException
     */
    @Override
    public MovieData deleteMovieData(int id) throws CRUDException {
        if (movieMap.containsKey(id)) {
            return movieMap.remove(id);
        } else {
            throw new CRUDException("No data found for delete");
        }
    }

    /**
     * Methode die ein paar Eintraege für die HasMap generiert unde sie dieser hinzufuegt
     */
    private void generateTestData() {
        Calendar cal = Calendar.getInstance();
        MovieData movieData = new MovieData();
        movieData.setTitle("Avatar");
        movieData.setDirector("Steven Spielberg");
        movieData.setGenre("Fantasy");
        cal.set(2009, 11, 17);
        movieData.setReleaseDate(cal.getTime());
        movieData.setRating(5);
        createMovieData(movieData);

        movieData = new MovieData();
        movieData.setTitle("War of the worlds");
        movieData.setDirector("Steven Spielberg");
        movieData.setGenre("Science Fiction");
        cal.set(2019, 9, 28);
        movieData.setReleaseDate(cal.getTime());
        movieData.setRating(3);
        createMovieData(movieData);

        movieData = new MovieData();
        movieData.setTitle("Warcraft");
        movieData.setDirector("Duncan Jones");
        movieData.setGenre("Fantasy");
        cal.set(2016, 4, 26);
        movieData.setReleaseDate(cal.getTime());
        movieData.setRating(4);
        createMovieData(movieData);

        movieData = new MovieData();
        movieData.setTitle("Alien");
        movieData.setDirector("Ridley Scott");
        movieData.setGenre("Science Fiction; Horror");
        cal.set(1979, 4, 25);
        movieData.setReleaseDate(cal.getTime());
        movieData.setRating(5);
        createMovieData(movieData);
    }

}
