package domini.classes;


public class ConjuntItems extends ConjuntIdentificat<Item> {
    TipusItem tipusItem;

    public ConjuntItems(TipusItem tipus) {
        tipusItem = tipus;
    }

    public ConjuntItems(TaulaCSV taula) {
        /*
        tipusItem = new TipusItem(taula.obtenirLlistaAtributs(), taula.obtenirItem(0));
        for (int i = 0; i < taula.obtenirNumeroElements(); ++i) {
            afegir(new Item(tipusItem, taula.obtenirItem(i)));
        }
        */
    }
}
