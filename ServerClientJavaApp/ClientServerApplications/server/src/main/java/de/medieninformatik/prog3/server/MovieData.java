package de.medieninformatik.prog3.server;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Klasse, die verschiedene Getter und Setter beinhaltet
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieData {
    private int uniqueId;
    private String title;
    private String director;
    private int rating;
    private String genre;
    private Date releaseDate;

    public MovieData() {
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "MovieData{" +
                "uniqueId='" + uniqueId + '\'' +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", rating=" + rating +
                ", genre='" + genre + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
