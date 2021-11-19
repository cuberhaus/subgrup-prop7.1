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
    public ConjuntItems filtrar(ConjuntItems conjuntItems) throws IllegalArgumentException {
        if (conjuntItems == null) {
            throw new IllegalArgumentException("No es pot filtrar un ConjuntItems nul.");
        }
        if (!esCompatible(conjuntItems)) {
            throw new IllegalArgumentException("El ConjuntItems donat no és compatible amb el filtre.");
        }
        ConjuntItems conjuntItemsFiltrat = new ConjuntItems(conjuntItems.obteTipusItem(), conjuntItems.obtenirTotsElsElements());
        TreeSet<String> nomAtributsFiltrats = new TreeSet<>(conjuntItems.obteTipusItem().obtenirTipusAtributs().keySet());
        nomAtributs.forEach(nomAtributsFiltrats::remove);
        conjuntItemsFiltrat.esborrarAtributs(nomAtributsFiltrats);
        return conjuntItemsFiltrat;
    }

    private boolean esCompatible(ConjuntItems conjuntItems) {
        return conjuntItems.conteAtributs(nomAtributs);
    }
}
