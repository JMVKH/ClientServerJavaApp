package de.medieninformatik.prog3.server;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MovieDB {

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String DB_URL  = "jdbc:mysql://localhost/MovieDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
    private static MovieDB instance = null;
    private Connection connection = null;

    /**
     * Privater Konstruktor, verhindert mehrfach Instanzierung
     */
    private MovieDB() {
    }

    /**
     * Singleton Pattern um an ein Objekt der Klasse zu gelangen.
     */
    public synchronized static MovieDB getInstance()  {
        if (instance == null) {
            try {
                MovieDB db = new MovieDB();
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/moviedb", "root", "");
                instance = db;
                instance.connection = conn;
            } catch (Exception ex) {
                ex.printStackTrace();
                instance = null;
            }
        }
        return instance;
    }

    /**
     * Methode die eine Verbindung zur Datenbank liefert
     */
    public synchronized Connection getConnection() {
        if (instance != null) {
            return connection;
        }
        return null;
    }

    /**
     * Methode die die Datenbankverbindung schliesst
     */
    public synchronized void close() {
        if (instance != null) {
            try {
                instance.getConnection().close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            instance = null;
        }
    }

    /**
     * Methode, die genutzt wird um einen neuen Eintrag in die Datenbank zu ermoeglichen
     * @param movieData
     * @return movieData
     */
    public static MovieData createMovieData(MovieData movieData) {
        Connection conn = MovieDB.getInstance().getConnection();
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "INSERT INTO MovieDB.Movies (Title, Director, Rating, Genre, ReleaseDate) VALUES ( ";
                sql += "'" + movieData.getTitle() + "', ";
                sql += "'" + movieData.getDirector() + "', ";
                sql += movieData.getRating() + ", ";
                sql += "'" + movieData.getGenre() + "', ";
                sql += "'" + SDF.format(movieData.getReleaseDate())+ "'";
                sql += ")";
                stmt.execute(sql);
                return movieData;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Methode, die genutzt wird um mit Hilfe einer ID nach einem spezifischen Datenbankeintrag zu suchen
     * @param id
     * @return movieData
     */
    public static MovieData readMovieData(Integer id) {
        Connection conn = MovieDB.getInstance().getConnection();
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "select ID, Title, Director, Rating, Genre, ReleaseDate from MovieDB.Movies where ID = " + id;
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs.next()) {
                        int col = 1;
                        MovieData movieData = new MovieData();
                        movieData.setUniqueId(rs.getInt(col++));
                        movieData.setTitle(rs.getString(col++));
                        movieData.setDirector(rs.getString(col++));
                        movieData.setRating(rs.getInt(col++));
                        movieData.setGenre(rs.getString(col++));
                        movieData.setReleaseDate(rs.getDate(col++));
                        return movieData;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Methode, die genutzt wird um mit Hilfe eines Such-Strings die Datenbank nach passenden Eintraegen zu durchsuchen
     * @param filter
     * @return movieList
     */
    public static MovieList readMovieList(String filter) {
        String lf = filter == null ? "" : filter.toLowerCase();
        Connection conn = MovieDB.getInstance().getConnection();
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "SELECT ID, Title, Director, Rating, Genre, ReleaseDate FROM MovieDB.Movies";
                if (!filter.isEmpty()) {
                    sql += " WHERE LOWER(Title) LIKE '%" + lf + "%'";
                    sql += " OR LOWER(Director) LIKE '%" + lf + "%'";
                    sql += " OR LOWER(Genre) LIKE '%" + lf + "%'";
                }
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    MovieList movieList = new MovieList();
                    while (rs.next()) {
                        int col = 1;
                        MovieData movieData = new MovieData();
                        movieData.setUniqueId(rs.getInt(col++));
                        movieData.setTitle(rs.getString(col++));
                        movieData.setDirector(rs.getString(col++));
                        movieData.setRating(rs.getInt(col++));
                        movieData.setGenre(rs.getString(col++));
                        movieData.setReleaseDate(rs.getDate(col++));
                        movieList.add(movieData);
                    }
                    return movieList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Methode, die genutzt wird um einen Datenbankeintrag zu editieren
     * @param movieData
     * @return movieData
     */
    public static MovieData updateMovieData(MovieData movieData) {
        Connection conn = MovieDB.getInstance().getConnection();
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "UPDATE MovieDB.Movies SET ";
                sql += "Title = '" + movieData.getTitle() + "', ";
                sql += "Director = '" + movieData.getDirector() + "', ";
                sql += "Rating = " + movieData.getRating() + ", ";
                sql += "Genre = '" + movieData.getGenre() + "', ";
                sql += "ReleaseDate = '" + SDF.format(movieData.getReleaseDate()) + "' ";
                sql += " WHERE ID=" + movieData.getUniqueId();
                System.out.println("--> sql: " + sql);
                stmt.execute(sql);
                return movieData;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Methode, die genutzt wird um einen Datenbankeintrag zu loeschen
     * @param id
     */
    public static void deleteMovieData(int id) {
        Connection conn = MovieDB.getInstance().getConnection();
        if (conn != null) {
            try (Statement stmt = conn.createStatement()) {
                String sql = "DELETE FROM MovieDB.Movies WHERE ID= " + id;
                System.out.println("--> sql: " + sql);
                stmt.execute(sql);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
