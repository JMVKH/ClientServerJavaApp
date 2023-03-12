package de.medieninformatik.prog3.client.model;

/**
 * Klasse die mit Hilfe von Lazy initializing CRUDMovieDataMock und CRUDMovieDataRest initialisiert
 */
public class CRUDFactory {

    public enum CRUDTypes {
        mock,
        rest
    }

    private static CRUDMovieDataMock mockImpl = null;
    private static CRUDMovieDataRest restImpl = null;

    /**
     * initialisiert je nach hereingereichtem CRUDType entweder CRUDMovieDataMock oder CRUDMovieDataRest
     * @param type CRUDTypes.rest oder CRUDTypes.mock
     * @return mockImpl oder restImpl
     */
    public static CRUDMovieData getInstance(CRUDTypes type) {
        if (type == CRUDTypes.mock) {
            // Lazy initializing
            if (mockImpl == null) {
                mockImpl = new CRUDMovieDataMock();
            }
            return mockImpl;
        } else if (type == CRUDTypes.rest) {
            // Lazy initializing
            if (restImpl == null) {
                restImpl = new CRUDMovieDataRest();
            }
            return restImpl;
        }
        return null;
    }
}
