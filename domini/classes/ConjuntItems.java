package domini.classes;

/**
public class ConjuntItems extends Conjunt<Item> {
    //map<id, item>
    TipusItem tipusItem;
    public ConjuntItems(TipusItem tipus) {
        super();
        tipusItem = tipus;
    }
    public ConjuntItems(TaulaCSV taula) {
        super();
        tipusItem = new TipusItem(taula.obtenirLlistaAtributs(), taula.obtenirItem(0));
        for (int i = 0; i < taula.obtenirNumeroElements(); ++i) {
            afegir(new Item(tipusItem, taula.obtenirItem(i)));
        }
    }
}
 */
