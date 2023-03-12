package de.medieninformatik.prog3.server;

import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <Janik Hagen> <m29127>
 * date:2022-02-27
 * Programmierung 3,  Hausarbeit
 * Beschreibung der Klasse:
 * Application-Klasse (jakarta.ws.rs.core.Application) des Server-Moduls
 * fuegt Klassen einem HashSet hinzu
 */

public class MovieServerApplication  extends Application {

    private Set<Class<?>> classes = new HashSet<>();

    public MovieServerApplication() {
        classes.add(MovieDataRestService.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

}
