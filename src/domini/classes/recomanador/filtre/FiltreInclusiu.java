package domini.classes.recomanador.filtre;

import domini.classes.ConjuntItems;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Representa un filtre inclusiu.
 * @author maria.prat
 */
public class FiltreInclusiu extends Filtre {
    public FiltreInclusiu(ArrayList<String> nomAtributs) {
        super(nomAtributs);
    }

    @Override
    public void filtrar(ConjuntItems conjuntItems) throws IllegalArgumentException {
        if (conjuntItems == null) {
            throw new IllegalArgumentException("No es pot filtrar un ConjuntItems nul.");
        }
        TreeSet<String> nomAtributsFiltrats = new TreeSet<>(conjuntItems.obteTipusItem().obtenirTipusAtributs().keySet());
        nomAtributs.forEach(nomAtributsFiltrats::remove);
        conjuntItems.esborrarAtributs(nomAtributsFiltrats);
    }
}
