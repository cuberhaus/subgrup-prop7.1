package domini.classes;


import domini.classes.csv.TaulaCSV;
import excepcions.*;

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
     * @throws AccesAEstatIncorrecteException si id no és correcte o l'ítem creat ja és al conjunt.
     * @throws NoExisteixElementException La taula es incompleta
     * @throws DistanciaNoCompatibleAmbValorException La definicio de TipusItem es incorrecta
     * @throws FormatIncorrecteException Un item te un format incorrecte
     */
    public ConjuntItems(String nomTipusItem, TaulaCSV taula) throws AccesAEstatIncorrecteException,
            DistanciaNoCompatibleAmbValorException, NoExisteixElementException, FormatIncorrecteException {
        taula.eliminarEspaisInnecessaris();
        tipusItem = new TipusItem(nomTipusItem, taula, taula.obtenirNumItems());
        elements = new TreeMap<>();
        int id = 0;
        boolean errores = false;
        for (int i = 0; i < taula.obtenirNumItems(); ++i) {
            String sid = taula.obtenirValorAtribut(i, "id");
            try {
                id = Integer.parseInt(sid);
            } catch (NumberFormatException e1) {
                errores = true;
            }
            if (!errores) {
                Id identificador = new Id(id, true);
                if (!elements.containsKey(identificador)) {
                    afegir(new Item(identificador, tipusItem, taula.obtenirNomsAtributs(), taula.obtenirItem(i)));
                }
            }

            errores = false;
        }
    }

    /**
     * Constructora de Conjunt d'ítems.
     * @param taula <code>TaulaCSV</code> contenidor del fitxer CSV desitjar.
     * @param tipusItem el tipus dels items a crear.
     * @throws AccesAEstatIncorrecteException la taula que s'ha passat no està inicialitzada.
     */
    public ConjuntItems(TaulaCSV taula, TipusItem tipusItem) throws AccesAEstatIncorrecteException {
        taula.eliminarEspaisInnecessaris();
        this.tipusItem = tipusItem;

        elements = new TreeMap<>();
        int id;
        for (int i = 0; i < taula.obtenirNumItems(); ++i) {
            try {
                String sid = taula.obtenirValorAtribut(i, "id");
                id = Integer.parseInt(sid);
                Id identificador = new Id(id, true);
                if (elements.containsKey(identificador)) {
                    throw new JaExisteixElementException("L'item creat ja existeix al conjunt");
                }
                afegir(new Item(identificador, tipusItem, taula.obtenirNomsAtributs(), taula.obtenirItem(i)));
            } catch (Exception ignored) {
            }
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
            try {
                if (!that.obtenir(element.getKey()).equals(element.getValue())) {
                    return false;
                }
            } catch (NoExisteixElementException e) {
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

    /**
     * Afegeix un item al conjunt d'ítems
     * @param element <code>Item</code> a afegir al conjunt
     */
    @Override
    public void afegir(Item element){
        if (!tipusItem.equals(element.obtenirTipusItem())) {
            throw new IllegalArgumentException();
        }
        else {
            super.afegir(element);
        }
    }

    /**
     * Crea una matriu on la primera fila es una capsalera i despres a cada fila hi ha un item.
     * @return Una matriu d'<code>String</code> que serialitza el ConjuntItems.
     */
    public ArrayList<ArrayList<String>> convertirAArrayList() {
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        ArrayList<String> capsalera = new ArrayList<>();
        capsalera.add("id");
        for (var x : tipusItem.obtenirTipusAtributs().entrySet()) {
            capsalera.add(x.getKey());
        }
        res.add(capsalera);
        for (Map.Entry<Id, Item> items : elements.entrySet()) {
            res.add(items.getValue().converteixAArray());
        }
        return res;
    }
}
