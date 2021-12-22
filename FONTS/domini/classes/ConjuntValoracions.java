package domini.classes;

import domini.classes.csv.TaulaCSV;
import excepcions.AccesAEstatIncorrecteException;
import excepcions.NoExisteixElementException;
import excepcions.UsuariIncorrecteException;
import utilitats.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;

/**
 * Representa un conjunt de valoracions
 *
 * @author pol.casacuberta
 */

public class ConjuntValoracions {
    private final TreeMap<Pair<Usuari, Item>, Valoracio> valoracions;
    private final ArrayList<String> capsalera = new ArrayList<>(Arrays.asList("userId", "itemId", "rating"));
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
     * @throws NoExisteixElementException l'element no es troba al conjunt
     * @throws AccesAEstatIncorrecteException la taula no esta inicialitzada
     * @throws UsuariIncorrecteException no existeix l'usuari
     */
    public void afegir(TaulaCSV valoracions, ConjuntItems items, ConjuntUsuaris usuaris) throws NoExisteixElementException, AccesAEstatIncorrecteException, UsuariIncorrecteException {
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

            if (!usuaris.conte(idUsuari)) {
                usuaris.afegir(new Usuari(idUsuari));
            }
            Usuari usuari = usuaris.obtenir(idUsuari);
            double doubleValoracio = Double.parseDouble(valors.get(i));
            Valoracio valoracio = new Valoracio(doubleValoracio, usuari, item);
            if (!this.valoracions.containsKey(new Pair<>(usuari, item))) {
                this.valoracions.put(new Pair<>(usuari, item), valoracio);
            }
        }
    }


    /**
     * @param usuari Usuari que ha fet la valoracio
     * @param item Item valorat
     * @return retorna si hi ha una valoracio al conjunt del usuari donat a l'item donat.
     */
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
    public TreeMap<Pair<Usuari, Item>, Valoracio> obtenitTotesLesValoracions() {
        return valoracions;
    }

    /**
     * @return Retorna UsuariId, ItemId, valoracio
     */
    public ArrayList<ArrayList<String>> convertirAArrayList() {
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        res.add(capsalera);
        for(var x : valoracions.entrySet()) {
            ArrayList<String> fila = new ArrayList<>();
            fila.add(String.valueOf(x.getKey().x.obtenirId().obtenirValor()));
            fila.add(String.valueOf(x.getKey().y.obtenirId().obtenirValor()));
            fila.add(String.valueOf(x.getValue().obtenirValor()));
            res.add(fila);
        }
        return res;
    }

    /**
     * Esborra totes les valoracions del conjunt de valoracions
     */
    public void esborraTotesLesValoracions() {
        valoracions.clear();
    }

    /**
     * Esborra totes les valoracions on apareix l'ítem amb l'identificador donat
     * @param idItem identificador de l'ítem
     */
    public void esborraValoracionsItem(Id idItem) {
        Set<Pair<Usuari, Item>> setEntrades = valoracions.keySet();

        ArrayList<Pair<Usuari, Item>> perEsborrar = new ArrayList<>();

        for (Pair<Usuari, Item> entrada : setEntrades) {
            if(entrada.y.obtenirId().equals(idItem)) {
                perEsborrar.add(entrada);
            }
        }

        for (Pair<Usuari, Item> eliminar : perEsborrar) {
            valoracions.remove(eliminar);
        }
    }
}
