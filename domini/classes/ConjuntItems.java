package domini.classes;


import java.util.ArrayList;

/**
 * @pablo.vega
 */

public class ConjuntItems extends ConjuntIdentificat<Item> {
    TipusItem tipusItem;
    ArrayList<Item> conjuntItems;

    public ConjuntItems(String nomTipusItem, TaulaCSV taula) {
        tipusItem = new TipusItem(nomTipusItem, taula.obtenirLlistaAtributs(), taula.obtenirItem(1));
        for (int i = 0; i < taula.obtenirNumeroElements(); ++i) {
            String sid = taula.obtenirValorAtributItem(i, "id");
            try {
                int id = Integer.parseInt(sid);
            }
            catch ()

            conjuntItems.add(new Item(new Id(),tipusItem,taula.obtenirItem(i)));
        }
    }
}
