package domini.classes;

import domini.classes.csv.TaulaCSV;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Representa un conjunt de valoracions
 * @author pol.casacuberta
 */

public class ConjuntValoracions {
    private TreeMap<Pair<Usuari,Item>,Valoracio> valoracions;

    public void afegir(String nomTipusItem, TaulaCSV valoracions, ConjuntItems items, ConjuntUsuaris usuaris) throws InterruptedException {
        ArrayList<String> idsUsuaris= valoracions.obtenirValorsAtribut("userId");
        ArrayList<String> idsItems= valoracions.obtenirValorsAtribut("itemId");
        ArrayList<String> valors= valoracions.obtenirValorsAtribut("rating");

        for (int i = 0; i < idsUsuaris.size(); ++i) {
            int idUsuariInt = Integer.parseInt(idsUsuaris.get(i));
            int idItemInt = Integer.parseInt(idsUsuaris.get(i));
            Id idItem = new Id (idItemInt,true);
            Id idUsuari = new Id (idUsuariInt,true);
            Item item = items.obte(idItem);
            Usuari usuari = usuaris.obte(idUsuari);
            double doubleValoracio = Double.parseDouble(valors.get(i));
            Valoracio valoracio = new Valoracio(doubleValoracio, usuari,item);
            if (!this.valoracions.containsKey(new Pair<>(usuari, item))) {
                this.valoracions.put(new Pair<>(usuari,item), valoracio);
            }
        }
    }

    public boolean conte(Usuari usuari, Item item){
        return valoracions.containsKey(new Pair<>(usuari,item));
    }

    public Valoracio obte(Usuari usuari, Item item) {
        if (item == null || usuari == null || !valoracions.containsKey(new Pair<>(usuari,item))) {
            throw new IllegalArgumentException("El conjunt no conté cap element amb aquest Id");
        }
        return valoracions.get(new Pair<>(usuari, item));
    }

    public Valoracio afegir(Valoracio valoracio) {
        return valoracions.put(new Pair<>(valoracio.getUsuari(),valoracio.getItem()), valoracio);
    }

    public Valoracio esborrar(Usuari usuari, Item item) {
        return valoracions.remove(new Pair<>(usuari,item));
    }

    public boolean esborrar(Valoracio valoracio) {
        return valoracions.remove(new Pair<>(valoracio.getUsuari(),valoracio.getItem()), valoracio);
    }

    public TreeMap<Pair<Usuari, Item>, Valoracio> obteTotesValoracions() { return valoracions; }
}
