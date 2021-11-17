package domini.classes;


import java.util.ArrayList;

/**
 * @pablo.vega
 */

public class ConjuntItems extends ConjuntIdentificat<Item> {
    TipusItem tipusItem;

    public ConjuntItems(String nomTipusItem, TaulaCSV taula) throws InterruptedException {
        tipusItem = new TipusItem(nomTipusItem, taula.obtenirLlistaAtributs(), taula.obtenirItem(1));
        int id;
        for (int i = 0; i < taula.obtenirNumeroElements(); ++i) {
            String sid = taula.obtenirValorAtributItem(i, "id");
            try {
                id = Integer.parseInt(sid);
            } catch (NumberFormatException e1) {
                throw e1;
            }
            Id identificador = new Id(id, true);
            if (elements.containsKey(identificador)) {
                throw new InterruptedException("L'item creat ja existeix al conjunt");
            }
            elements.put(identificador, new Item(identificador, tipusItem, taula.obtenirItem(i)));
        }
    }
}
