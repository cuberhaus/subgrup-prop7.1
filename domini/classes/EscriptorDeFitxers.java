package domini.classes;

import java.io.IOException;

/**
 * Classe abstracta del gestor de sortida de fitxers.
 * @author pablo.vega
 */
public abstract class EscriptorDeFitxers {
    public EscriptorDeFitxers() {}
    public void escriptorFitxers(String pathname, Taula taula) throws IOException {}
}
