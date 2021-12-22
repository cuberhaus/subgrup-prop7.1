package domini.classes.recomanador.filtre;

import domini.classes.ConjuntItems;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Representa un filtre exclusiu d'un conjunt d'ítems.
 * @author maria.prat
 */
public class FiltreExclusiu extends Filtre{
    /**
     * Constructor d'un FiltreExclusiu el nom dels atributs.
     * @param nomAtributs Noms dels atributs.
     */
    public FiltreExclusiu(ArrayList<String> nomAtributs) {
        super(nomAtributs);
    }

    /**
     * Esborra els atributs amb els noms del filtre del conjunt d'ítems donat. Si un nom no es correspon amb cap atribut
     * del conjunt, s'ignora.
     * @param conjuntItems Conjunt d'Items per filtrar.
     * @throws IllegalArgumentException si el conjunt d'ítems donat és nul.
     */
    @Override
    public void filtrar(ConjuntItems conjuntItems) throws IllegalArgumentException {
        if (conjuntItems == null) {
            throw new IllegalArgumentException("No es pot filtrar un ConjuntItems nul.");
        }
        conjuntItems.esborrarAtributs(new TreeSet<>(nomAtributs));
    }
}
