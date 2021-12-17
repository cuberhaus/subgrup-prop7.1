package domini.classes;


import domini.classes.csv.TaulaCSV;

import java.util.*;

/**
 * Classe que representa un conjunt d'ítems
 * @author pablo.vega
 */

public class ConjuntItems extends ConjuntIdentificat<Item> {
    protected TipusItem tipusItem;

    /**
     * Constructora de Conjunt d'ítems.
     * @param nomTipusItem <code>String</code> nom del tipus d'ítem.
     * @param taula <code>TaulaCSV</code> contenidor del fitxer CSV desitjar.
     * @throws Exception si id no és correcte o l'ítem creat ja és al conjunt.
     */
    public ConjuntItems(String nomTipusItem, TaulaCSV taula) throws Exception {
        taula.eliminarEspaisInnecessaris();
        tipusItem = new TipusItem(nomTipusItem, taula, taula.obtenirNumItems());

        elements = new TreeMap<>();
        int id;
        for (int i = 0; i < taula.obtenirNumItems(); ++i) {
            String sid = taula.obtenirValorAtribut(i, "id");
            try {
                id = Integer.parseInt(sid);
            } catch (NumberFormatException e1) {
                throw new InputMismatchException("L'id no es un integer");
            }
            Id identificador = new Id(id, true);
            if (elements.containsKey(identificador)) {
                throw new InputMismatchException("L'item creat ja existeix al conjunt");
            }
            afegir(new Item(identificador, tipusItem, taula.obtenirNomsAtributs(), taula.obtenirItem(i)));
        }
    }

    /**
     * Creació d'un conjunt d'ítems buit.
     * @param tipusItem <code>TipusItem</code> el tipus d'ítem dels objectes.
     */
    public ConjuntItems(TipusItem tipusItem) {
        this.tipusItem = tipusItem;
        this.elements = new TreeMap<>();
    }

    /**
     * Creació d'un conjunt d'ítems a partir d'un TipusItem i un contenidor de Id-Item
     * @param tipusItem <code>TipusItem</code> del tipus d'ítem de tot el conjunt.
     * @param elements <code>TreeMap&lt;Id, Item&gt;</code> conte la relació id-item
     */
    public ConjuntItems(TipusItem tipusItem, TreeMap<Id, Item> elements) {
        this.tipusItem = tipusItem;
        this.elements = new TreeMap<>();
        for (Map.Entry<Id, Item> itemIdentificat : elements.entrySet()) {
            this.elements.put(itemIdentificat.getKey().copiar(), itemIdentificat.getValue().copiar());
        }
    }

    public TipusItem obteTipusItem() { return tipusItem; }

    /**
     * Esborra els atributs desitjats.
     * @param nomAtributs <code>TreeSet &lt;String&gt; </code> contenidor de nomAtributs.
     */
    public void esborrarAtributs(TreeSet<String> nomAtributs) {
        tipusItem.esborrarAtributs(nomAtributs);
        for (Map.Entry<Id, Item> itemIdentificat : elements.entrySet()) {
            itemIdentificat.getValue().esborrarAtributs(nomAtributs);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConjuntItems that = (ConjuntItems) o;
        if (!tipusItem.equals(that.tipusItem)) {
            return false;
        }
        if (elements.size() != that.elements.size()) {
            return false;
        }
        for (Map.Entry<Id, Item> element : elements.entrySet()) {
            if (!that.conte(element.getKey())) {
                return false;
            }
            if (!that.obtenir(element.getKey()).equals(element.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retorna una copia del ConjuntItems
     * @return <code>ConjuntItems</code>
     */
    public ConjuntItems copiar() {
        TreeMap<Id, Item> copiaElements = new TreeMap<>();
        for (Map.Entry<Id, Item> element : elements.entrySet()) {
            copiaElements.put(element.getKey().copiar(), element.getValue().copiar());
        }
        return new ConjuntItems(tipusItem.copiar(), copiaElements);
    }

    @Override
    /**
     * Afegeix un item al conjunt d'items
     * @param element <code>Item</code> a afegir al conjunt
     */
    public void afegir(Item element){
        if (element.obtenirTipusItem() != this.tipusItem) {
            throw new IllegalArgumentException();
        }

        else {
            super.afegir(element);
        }
    }
}
