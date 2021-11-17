package domini.classes;


import java.util.ArrayList;

public class ConjuntItems extends ConjuntIdentificat<Item> {
    TipusItem tipusItem;
    ArrayList<Item> conjuntItems;

    public ConjuntItems(String nomTipusItem, TaulaCSV taula) {
        tipusItem = new TipusItem(nomTipusItem, taula.obtenirLlistaAtributs(), taula.obtenirItem(1));
        for (int i = 0; i < taula.obtenirNumeroElements(); ++i) {
            String sid = taula.obtenirValorsDeItemSeleccionat()
            conjuntItems.add(new Item(new Id(),tipusItem,taula.obtenirItem(i)));
        }
    }
}
