package domini.classes;


import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * @pablo.vega
 */

public class ConjuntItems extends ConjuntIdentificat<Item> {
    TipusItem tipusItem;

    public ConjuntItems(String nomTipusItem, TaulaCSV taula) throws InputMismatchException, InterruptedException {
        tipusItem = new TipusItem(nomTipusItem, taula.obtenirLlistaAtributs(), taula.obtenirItem(1));
        int id;
        for (int i = 0; i < taula.obtenirNumeroElements(); ++i) {
            String sid = taula.obtenirValorAtributItem(i, "id");
            try {
                id = Integer.parseInt(sid);
            } catch (NumberFormatException e1) {
                throw new InputMismatchException("L'id no es un integer");
            }
            Id identificador = new Id(id, true);
            if (elements.containsKey(identificador)) {
                throw new InputMismatchException("L'item creat ja existeix al conjunt");
            }
            afegir(new Item(identificador, tipusItem, taula.obtenirItem(i)));
        }
    }
}
