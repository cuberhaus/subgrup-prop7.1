package domini.classes;


import domini.classes.csv.TaulaCSV;

import java.util.*;

/**
 * Classe que representa un conjunt d'items
 * @author pablo.vega
 */

public class ConjuntItems extends ConjuntIdentificat<Item> {
    TipusItem tipusItem;

    /**
     * Constructora de Conjunt d'items.
     * @param nomTipusItem <code>String</code> nom del tipus d'item.
     * @param taula <code>TaulaCSV</code> contenidor del fitxer CSV desitjar.
     * @throws InputMismatchException si id no es correcte o l'item creat ja es al conjunt.
     */
    public ConjuntItems(String nomTipusItem, TaulaCSV taula) throws InputMismatchException {
        taula.eliminarEspaisInnecessaris();
        tipusItem = new TipusItem(nomTipusItem, taula);

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
     * Creacio d'un conjunt d'items buit.
     * @param tipusItem <code>TipusItem</code> el tipus d'item dels objectes.
     */
    public ConjuntItems(TipusItem tipusItem) {
        this.tipusItem = tipusItem;
        this.elements = new TreeMap<>();
    }

    /**
     * Creacio d'un conjunt d'items a partir d'un TipusItem i un contenidor de Id-Item
     * @param tipusItem
     * @param elements
     */
    public ConjuntItems(TipusItem tipusItem, TreeMap<Id, Item> elements) {
        this.tipusItem = tipusItem;
        this.elements = new TreeMap<>();
        for (Map.Entry<Id, Item> itemIdentificat : elements.entrySet()) {
            this.elements.put(itemIdentificat.getKey().copy(), itemIdentificat.getValue().copy());
        }
    }

    public TipusItem obteTipusItem() { return tipusItem; }

    public boolean conteAtributs(ArrayList<String> nomAtributs) {
        return tipusItem.conteAtributs(nomAtributs);
    }

    public void esborrarAtributs(TreeSet<String> nomAtributs) {
        tipusItem.esborrarAtributs(nomAtributs);
        for (Map.Entry<Id, Item> itemIdentificat : elements.entrySet()) {
            itemIdentificat.getValue().esborrarAtributs(nomAtributs);
        }
    }
}
