package domini.classes;

import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.distancia.*;
import domini.classes.atributs.valors.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa un ítem.
 * @author maria.prat
 */
public class Item implements Comparable<Item>, ElementIdentificat {
    private final Id id;
    private final TipusItem tipusItem;
    private Map<String, ValorAtribut<?>> atributs;
    private Map<Usuari, Valoracio> valoracions;

    /**
     * Constructor d'un ítem amb conjunt de valoracions buit.
     * @param id <code>Id</code> que conté l'identificador de l'ítem.
     * @param tipusItem <code>TipusItem</code> que conté el tipus de l'ítem.
     * @param atributs <code>Map<String, ValorAtribut></code> que relaciona el nom de cada atribut de l'ítem amb el seu <code>ValorAtribut</code>.
     * @throws IllegalArgumentException llançada si el <code>TipusItem</code> i el <code>Map<String, ValorAtribut></code>
     * donats no són compatibles.
     */
    public Item(Id id, TipusItem tipusItem, Map<String, ValorAtribut<?>> atributs) throws IllegalArgumentException {
        this.id = id;
        this.tipusItem = tipusItem;
        this.atributs = atributs;
        actualitzarFactorNormalitzacio();
        this.valoracions = new HashMap<>();
        if (!tipusItem.esCompatible(atributs)) {
            throw new IllegalArgumentException("Els atributs i el tipus d'ítem donats no són compatibles.");
        }
    }

    public Item(Id id, TipusItem tipusItem, ArrayList<String> nom_atributs, ArrayList<String> valors) {
        this.id = id;
        this.tipusItem = tipusItem;
        obtenirAtributs(nom_atributs, valors);
        actualitzarFactorNormalitzacio();
        this.valoracions = new HashMap<>();
        if (!tipusItem.esCompatible(atributs)) {
            throw new IllegalArgumentException("Els atributs i el tipus d'ítem donats no són compatibles.");
        }
    }

    private void actualitzarFactorNormalitzacio() {
        for (Map.Entry<String, TipusAtribut> atribut : tipusItem.obtenirTipusAtributs().entrySet()) {
            atribut.getValue().obtenirDistancia().actualitzarFactorDeNormalitzacio(atributs.get(atribut.getKey()));
        }
    }

    private void obtenirAtributs(ArrayList<String> nomAtributs, ArrayList<String> valors) throws IllegalArgumentException {
        if (tipusItem.obtenirTipusAtributs().size() != nomAtributs.size() ||
                tipusItem.obtenirTipusAtributs().size() != valors.size()) {
            throw new IllegalArgumentException("No es poden obtenir els atributs d'un Item a partir de conjunts de " +
                    "mides diferents.");
        }
        atributs = new HashMap<>();
        for (int i = 0; i < nomAtributs.size(); ++i) {
            if (!tipusItem.obtenirTipusAtributs().containsKey(nomAtributs.get(i))) {
                throw new IllegalArgumentException("El TipusItem no és compatible amb els noms dels atributs donats.");
            }
            atributs.put(nomAtributs.get(i),
                    obtenirValorAtribut(tipusItem.obtenirTipusAtributs().get(nomAtributs.get(i)).obtenirValorAtribut(),
                    valors.get(i)));
        }
    }

    private ValorAtribut<?> obtenirValorAtribut(ValorAtribut<?> valorAtribut, String s) throws IllegalArgumentException {
        if (valorAtribut instanceof ValorBoolea) {
            return new ValorBoolea(Boolean.parseBoolean(s));
        } else if (valorAtribut instanceof ValorCategoric) {
            return new ValorCategoric(s);
        } else if (valorAtribut instanceof ValorNumeric) {
            return new ValorNumeric(Double.parseDouble(s));
        } else if (valorAtribut instanceof ValorTextual) {
            return new ValorTextual(s);
        } else if (valorAtribut instanceof ValorConjuntBoolea) {
            return new ValorConjuntBoolea(s);
        } else if (valorAtribut instanceof ValorConjuntCategoric) {
            return new ValorConjuntCategoric(s);
        } else if (valorAtribut instanceof ValorConjuntNumeric) {
            return new ValorConjuntNumeric(s);
        } else if (valorAtribut instanceof ValorConjuntTextual) {
            return new ValorConjuntTextual(s);
        } else {
            throw new IllegalArgumentException("El ValorAtribut donat no és una instància de cap subclasse reconeguda.");
        }
    }

    public Id obtenirId() { return id; }

    @Override
    public int compareTo(Item o) {
        return id.compareTo(o.id);
    }

    public boolean afegirValoracio(Valoracio valoracio) throws IllegalArgumentException {
        if (valoracio == null) {
            throw new IllegalArgumentException("No es pot afegir una valoració nul·la.");
        }
        if (!this.equals(valoracio.getItem())) {
            throw new IllegalArgumentException("No es pot afegir a un ítem una valoració d'un altre ítem.");
        }
        if (valoracions.containsKey(valoracio.getUsuari())) {
            return false;
        }
        valoracions.put(valoracio.getUsuari(), valoracio);
        return true;
    }

    public boolean esborrarValoracio(Usuari usuari) {
        if (usuari == null || !valoracions.containsKey(usuari)) {
            return false;
        }
        valoracions.remove(usuari);
        return true;
    }

    /**
     * @param item <code>Item</code> que conté l'ítem amb el qual es vol calcular la distància.
     * @return <code>double</code> que conté la distància entre l'ítem actual i l'ítem donat si els ítem són del mateix
     * tipus.
     */
    public double obtenirDistancia(Item item) throws IllegalArgumentException {
        if (!tipusItem.equals(item.tipusItem)) {
            throw new IllegalArgumentException("No es pot calcular la distància entre dos ítems de TipusItems diferents.");
        }
        double distancia = 0.0;
        for (Map.Entry<String, TipusAtribut> tipusAtribut : tipusItem.obtenirTipusAtributs().entrySet()) {
            distancia += tipusAtribut.getValue().obtenirDistancia().obtenir(atributs.get(tipusAtribut.getKey()),
                    item.atributs.get(tipusAtribut.getKey())) /
                    (tipusAtribut.getValue().obtenirDistancia().obtenirFactorDeNormalitzacio());
        }
        return distancia;
    }
}
