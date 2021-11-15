package domini.classes;

import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.valors.ValorAtribut;

import java.util.Map;
import java.util.Set;

/**
 * Representa un ítem.
 * @author maria.prat
 */
public class Item {
    final Id id;
    Map<String, ValorAtribut<?>> atributs;
    final TipusItem tipusItem;
    private Set<Valoracio> valoracions;

    /**
     * Constructor d'un ítem.
     * @param id <code>Id</code> que conté l'identificador de l'ítem.
     * @param tipusItem <code>TipusItem</code> que conté el tipus de l'ítem.
     * @param atributs <code>Map<String, ValorAtribut></code> que relaciona el nom de cada atribut de l'ítem amb el seu <code>ValorAtribut</code>.
     * @throws IllegalArgumentException llançada si el <code>TipusItem</code> i el <code>Map<String, ValorAtribut></code>
     * donats no són compatibles.
     */
    public Item(Id id, TipusItem tipusItem, Map<String, ValorAtribut<?>> atributs) throws IllegalArgumentException {
        this.id = id;
        this.tipusItem = tipusItem;
        this.atributs = atributs;
        if (!tipusItem.esCompatible(atributs)) {
            throw new IllegalArgumentException("Els atributs i el tipus d'ítem donats no són compatibles.");
        }
    }

    /**
     * @param item <code>Item</code> que conté l'ítem amb el qual es vol calcular la distància.
     * @return <code>double</code> que conté la distància entre l'ítem actual i l'ítem donat si els ítem són del mateix
     * tipus. Altrament, retorna INF.
     */
    public double obtenirDistancia(Item item) {
        if (!tipusItem.equals(item.tipusItem)) {
            return Double.POSITIVE_INFINITY;
        }
        double distancia = 0.0;
        for (Map.Entry<String, TipusAtribut> entrada : tipusItem.getTipusAtributs().entrySet()) {
            distancia += entrada.getValue().obtenirDistancia(atributs.get(entrada.getKey()),
                    item.atributs.get(entrada.getKey()));
        }
        return distancia;
    }

    public void addValoracio(Valoracio valoracio) throws IllegalArgumentException {
        if (!this.equals(valoracio.getItem())) {
            throw new IllegalArgumentException("No es pot afegir a un ítem una valoració d'un altre ítem.");
        }
        valoracions.add(valoracio);
    }
}
