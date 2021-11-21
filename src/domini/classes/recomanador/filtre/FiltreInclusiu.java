package domini.classes.recomanador.filtre;

import domini.classes.ConjuntItems;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Representa un filtre inclusiu.
 * @author maria.prat
 */
public class FiltreInclusiu extends Filtre {
    /**
     * Constructor d'un FiltreInclusiu amb el nom dels atributs.
     * @param nomAtributs Noms dels atributs.
     */
    public FiltreInclusiu(ArrayList<String> nomAtributs) {
        super(nomAtributs);
    }

    /**
     * Només conserva el atributs amb els noms del filtre del conjunt d'ítems donat. Si un nom no es correspon amb cap
     * atribut del conjunt, s'ignora.
     * @param conjuntItems Conjunt d'Items per filtrar.
     * @throws IllegalArgumentException si el conjunt d'items donat és nul.
     */
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
