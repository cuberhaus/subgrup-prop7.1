package domini.classes;

import domini.classes.csv.TaulaCSV;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Representa un conjunt de valoracions
 *
 * @author pol.casacuberta
 */

public class ConjuntValoracions {
    private final TreeMap<Pair<Usuari, Item>, Valoracio> valoracions;

    /**
     * Constructora per defecte
     */
    public ConjuntValoracions() {
        valoracions = new TreeMap<>();
    }

    /**
     * Afegeix un conjunt de valoracions al Paràmetre implícit a partir de:
     *
     * @param valoracions Una taula de valoracions
     * @param items       un conjunt d'ítems
     * @param usuaris     un conjunt d'usuaris
     * @throws Exception no s'ha pogut eliminar els espais
     */
    public void afegir(TaulaCSV valoracions, ConjuntItems items, ConjuntUsuaris usuaris) throws Exception {
        valoracions.eliminarEspaisInnecessaris();
        ArrayList<String> idsUsuaris = valoracions.obtenirValorsAtribut("userId");
        ArrayList<String> idsItems = valoracions.obtenirValorsAtribut("itemId");
        ArrayList<String> valors = valoracions.obtenirValorsAtribut("rating");

        for (int i = 0; i < idsUsuaris.size(); ++i) {
            int idUsuariInt = Integer.parseInt(idsUsuaris.get(i));
            int idItemInt = Integer.parseInt(idsItems.get(i));
            Id idItem = new Id(idItemInt, true);
            Id idUsuari = new Id(idUsuariInt, true);
            Item item = items.obtenir(idItem);
            Usuari usuari = usuaris.obtenir(idUsuari);
            double doubleValoracio = Double.parseDouble(valors.get(i));
            Valoracio valoracio = new Valoracio(doubleValoracio, usuari, item);
            if (!this.valoracions.containsKey(new Pair<>(usuari, item))) {
                this.valoracions.put(new Pair<>(usuari, item), valoracio);
            }
        }
    }

    public boolean conte(Usuari usuari, Item item) {
        return valoracions.containsKey(new Pair<>(usuari, item));
    }

    /**
     * Consultora de valoració
     *
     * @param usuari usuari que fa la valoració
     * @param item   item valorat
     * @return El resultat és la valoració amb usuari i item passat com a paràmetre
     */
    public Valoracio obte(Usuari usuari, Item item) {
        if (item == null || usuari == null || !valoracions.containsKey(new Pair<>(usuari, item))) {
            throw new IllegalArgumentException("El conjunt no conté cap element amb aquest Id");
        }
        return valoracions.get(new Pair<>(usuari, item));
    }

    /**
     * Afegeix una valoració al conjunt de valoracions.
     * Retorna la valoració anterior amb el mateix usuari i item o null si no existia.
     *
     * @param valoracio el paràmetre s'ha afegit al conjunt si no hi era abans.
     * @return Valoració anterior
     */
    public Valoracio afegir(Valoracio valoracio) {
        return valoracions.put(new Pair<>(valoracio.obtenirUsuari(), valoracio.obtenirItem()), valoracio);
    }

    /**
     * Esborra una valoració del conjunt de valoracions.
     *
     * @param usuari usuari que fa la valoració
     * @param item   item valorat
     * @return Valoració esborrada
     */
    public Valoracio esborrar(Usuari usuari, Item item) {
        return valoracions.remove(new Pair<>(usuari, item));
    }

    /**
     * Esborra una valoració del conjunt de valoracions.
     * Retorna true si s'ha esborrat correctament, retorna false si no hi era
     *
     * @param valoracio La valoració que es vol esborrar del conjunt
     * @return true si s'ha esborrat false si no
     */
    public boolean esborrar(Valoracio valoracio) {
        return valoracions.remove(new Pair<>(valoracio.obtenirUsuari(), valoracio.obtenirItem()), valoracio);
    }

    /**
     * Consultora de conjunt de valoracions
     *
     * @return El resultat és el conjunt de valoracions
     */
    public TreeMap<Pair<Usuari, Item>, Valoracio> obteTotesValoracions() {
        return valoracions;
    }
}
