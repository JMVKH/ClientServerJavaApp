package de.medieninformatik.prog3.client.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="Movies")
@XmlAccessorType(XmlAccessType.FIELD)
public class MovieList {

    @XmlElement(name = "Movie")
    private List<MovieData> movieList = new ArrayList<>();

    public List<MovieData> get() {
        return movieList;
    }

    public void set(List<MovieData> movieList) {
    }

    public boolean isEmpty() {
        return movieList.isEmpty();
    }

    public void add(MovieData data) {
        movieList.add(data);
    }
}
