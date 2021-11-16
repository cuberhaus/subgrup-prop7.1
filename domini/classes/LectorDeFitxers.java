package domini.classes;

import java.io.IOException;

/**
 * Classe abstracta del gestor d'entrada dels fitxers.
 * @author pablo.vega
 */
public abstract class LectorDeFitxers {
    public LectorDeFitxers() {}
    public abstract Taula lectorDeFitxers(String pathname) throws IOException;
}
