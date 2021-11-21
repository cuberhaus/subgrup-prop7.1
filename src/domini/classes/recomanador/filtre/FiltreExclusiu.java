package domini.classes.recomanador.filtre;

import domini.classes.ConjuntItems;

import java.util.ArrayList;
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
    public ConjuntItems filtrar(ConjuntItems conjuntItems) throws IllegalArgumentException {
        if (conjuntItems == null) {
            throw new IllegalArgumentException("No es pot filtrar un ConjuntItems nul.");
        }
        ConjuntItems conjuntItemsFiltrat = new ConjuntItems(conjuntItems.obteTipusItem(), conjuntItems.obtenirTotsElsElements());
        TreeSet<String> nomAtributsFiltrats = new TreeSet<>(conjuntItems.obteTipusItem().obtenirTipusAtributs().keySet());
        conjuntItemsFiltrat.esborrarAtributs(nomAtributsFiltrats);
        return conjuntItemsFiltrat;
    }
}
