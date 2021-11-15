package domini.classes;

import domini.classes.atributs.valors.ValorAtribut;

import java.util.Map;

/**
 * Representa un ítem.
 * @author maria.prat
 */
public class Item {
    final String id;
    Map<String, ValorAtribut<?>> atributs;
    final TipusItem tipusItem;

    /**
     * Constructor d'un ítem.
     * @param id <code>String</code> que conté l'identificador de l'ítem.
     * @param tipusItem <code>TipusItem</code> que conté el tipus de l'ítem.
     * @param atributs <code>Map<String, ValorAtribut></code> que relaciona el nom de cada atribut de l'ítem amb el seu <code>ValorAtribut</code>.
     * @throws IllegalArgumentException llançada si el <code>TipusItem</code> i el <code>Map<String, ValorAtribut></code>
     * donats no són compatibles.
     */
    public Item(String id, TipusItem tipusItem, Map<String, ValorAtribut<?>> atributs) throws IllegalArgumentException {
        this.id = id;
        this.tipusItem = tipusItem;
        this.atributs = atributs;
        if (!tipusItem.esCompatible(atributs)) {
            throw new IllegalArgumentException("Els atributs i el tipus d'ítem donats no són compatibles.");
        }
    }

    /**
     * @return <code>String</code> que conté l'identificador de l'ítem actual.
     */
    public String getId() {
        return id;
    }
}
