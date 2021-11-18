package domini.classes.filtre;

import domini.classes.ConjuntIdentificat;
import domini.classes.ConjuntItems;

import java.util.ArrayList;

/**
 * Representa un filtre.
 * @author maria.prat
 */
public abstract class Filtre {
    protected ArrayList<String> nomAtributs;

    public Filtre(ArrayList<String> nomAtributs) {
        this.nomAtributs = nomAtributs;
    }

    public ArrayList<String> obtenirNomAtributs() {
        return nomAtributs;
    }

    public abstract ConjuntItems filtrar(ConjuntItems conjuntItems);
}
