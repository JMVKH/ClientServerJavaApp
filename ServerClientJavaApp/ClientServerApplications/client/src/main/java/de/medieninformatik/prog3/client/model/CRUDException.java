package de.medieninformatik.prog3.client.model;

/**
 * Klasse die sich um die Ausgabe der CRUDException kuemmert
 */
public class CRUDException extends RuntimeException {
    CRUDException(String msg) {
        super(msg);
    }
}
