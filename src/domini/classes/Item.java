package domini.classes;

import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.valors.*;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Representa un ítem.
 * @author maria.prat
 */
public class Item implements Comparable<Item>, ElementIdentificat {
    private final Id id;
    private final TipusItem tipusItem;
    private Map<String, ValorAtribut<?>> atributs;
    private Map<Usuari, Valoracio> valoracions;

    public Item(Id id, TipusItem tipusItem, Map<String, ValorAtribut<?>> atributs, Map<Usuari, Valoracio> valoracions) {
        this.id = id;
        this.tipusItem = tipusItem;
        this.atributs = atributs;
        this.valoracions = valoracions;
    }

    /**
     * Constructor d'un ítem amb conjunt de valoracions buit.
     * @param id <code>Id</code> que conté l'identificador de l'ítem.
     * @param tipusItem <code>TipusItem</code> que conté el tipus de l'ítem.
     * @param nom_atributs <code>ArrayList<String></code> que conté els noms dels atributs els valors dels quals es troben a <code>valors</code>.
     * @param valors <code>ArrayList<String></code> que conté els valors de l'ítem en forma de String.
     * @throws IllegalArgumentException llançada si el <code>TipusItem</code> i el <code>Map<String, ValorAtribut></code>
     * donats no són compatibles.
     */
    public Item(Id id, TipusItem tipusItem, ArrayList<String> nom_atributs, ArrayList<String> valors) throws IllegalArgumentException {
        this.id = id;
        this.tipusItem = tipusItem;
        obtenirAtributs(nom_atributs, valors);
        actualitzarFactorNormalitzacioAtributs();
        this.valoracions = new TreeMap<>();
        if (!tipusItem.esCompatible(atributs)) {
            throw new IllegalArgumentException("Els atributs i el tipus d'ítem donats no són compatibles.");
        }
    }

    public Item copiar() {
        return new Item(this.obtenirId(), this.obtenirTipusItem(), this.obtenirAtributs(), this.obtenirValoracions());
    }

    public Map<Usuari, Valoracio> obtenirValoracions() {
        Map<Usuari, Valoracio> valoracions = new TreeMap<>();
        for (Map.Entry<Usuari, Valoracio> valoracioEntry : this.valoracions.entrySet()) {
            valoracions.put(valoracioEntry.getKey().copy(), valoracioEntry.getValue().copy());
        }
        return valoracions;
    }

    @Override
    public int compareTo(Item o) {
        return id.compareTo(o.id);
    }

    public Id obtenirId() { return id.copiar(); }

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

    public boolean afegirValoracio(Valoracio valoracio) throws IllegalArgumentException {
        if (valoracio == null) {
            throw new IllegalArgumentException("No es pot afegir una valoració nul·la.");
        }
        if (!this.equals(valoracio.obtenirItem())) {
            throw new IllegalArgumentException("No es pot afegir a un ítem una valoració d'un altre ítem.");
        }
        if (valoracions.containsKey(valoracio.obtenirUsuari())) {
            return false;
        }
        valoracions.put(valoracio.obtenirUsuari(), valoracio);
        return true;
    }

    public boolean esborrarValoracio(Usuari usuari) {
        if (usuari == null || !valoracions.containsKey(usuari)) {
            return false;
        }
        valoracions.remove(usuari);
        return true;
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

    private void obtenirAtributs(ArrayList<String> nomAtributs, ArrayList<String> valors) throws IllegalArgumentException {
        if (tipusItem.obtenirTipusAtributs().size() != nomAtributs.size() ||
                tipusItem.obtenirTipusAtributs().size() != valors.size()) {
            throw new IllegalArgumentException("No es poden obtenir els atributs d'un Item a partir de conjunts de " +
                    "mides diferents.");
        }
        atributs = new TreeMap<>();
        for (int i = 0; i < nomAtributs.size(); ++i) {
            if (!tipusItem.obtenirTipusAtributs().containsKey(nomAtributs.get(i))) {
                throw new IllegalArgumentException("El TipusItem no és compatible amb els noms dels atributs donats.");
            }
            atributs.put(nomAtributs.get(i),
                    obtenirValorAtribut(tipusItem.obtenirTipusAtributs().get(nomAtributs.get(i)).obtenirValorAtribut(),
                            valors.get(i)));
        }
    }

    public void esborrarAtributs(TreeSet<String> nomAtributs) {
        tipusItem.esborrarAtributs(nomAtributs);
        for (String nomAtribut : nomAtributs) {
            atributs.remove(nomAtribut);
        }
    }

    private void actualitzarFactorNormalitzacioAtributs() {
        for (Map.Entry<String, TipusAtribut> atribut : tipusItem.obtenirTipusAtributs().entrySet()) {
            atribut.getValue().obtenirDistancia().actualitzarFactorDeNormalitzacio(atributs.get(atribut.getKey()));
        }
    }

    public TipusItem obtenirTipusItem() {
        return tipusItem.copiar();
    }

    public Map<String, ValorAtribut<?>> obtenirAtributs() {
        Map<String, ValorAtribut<?>> atributs = new TreeMap<>();
        for (Map.Entry<String, ValorAtribut<?>> valorAtributEntry : this.atributs.entrySet()) {
            atributs.put(valorAtributEntry.getKey(), valorAtributEntry.getValue().copy());
        }
        return atributs;
    }
}
