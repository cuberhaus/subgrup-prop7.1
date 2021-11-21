package domini.classes.recomanador.filtre;

import domini.classes.ConjuntItems;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Representa un filtre exclusiu.
 * @author maria.prat
 */
public class FiltreExclusiu extends Filtre{
    public FiltreExclusiu(ArrayList<String> nomAtributs) {
        super(nomAtributs);
    }

    @Override
    public void filtrar(ConjuntItems conjuntItems) throws IllegalArgumentException {
        if (conjuntItems == null) {
            throw new IllegalArgumentException("No es pot filtrar un ConjuntItems nul.");
        }
        conjuntItems.esborrarAtributs(new TreeSet<>(nomAtributs));
    }
}
