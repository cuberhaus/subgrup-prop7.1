package domini.classes;

import domini.classes.atributs.tipus.*;
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
        this.valoracions = new HashMap<>();
        if (!tipusItem.esCompatible(atributs)) {
            throw new IllegalArgumentException("Els atributs i el tipus d'ítem donats no són compatibles.");
        }
    }

    public Item(Id id, TipusItem tipusItem, ArrayList<String> valors) {
        this.id = id;
        this.tipusItem = tipusItem;
        this.atributs = obtenirAtributs(tipusItem, valors);
        this.valoracions = new HashMap<>();
        if (!tipusItem.esCompatible(atributs)) {
            throw new IllegalArgumentException("Els atributs i el tipus d'ítem donats no són compatibles.");
        }
    }

    private ValorAtribut<?> dedueixValorAtribut(String s) {
        double d;
        try {
            d = Double.parseDouble(s);
        } catch (NumberFormatException e1) {
            if (ValorBoolea.esBoolea(s)) {
                return new ValorBoolea(s);
            }
            if (s.contains(";")) {
                String primerValor = s.split(";", 2)[0];
                try {
                    Double.parseDouble(primerValor);
                } catch (NumberFormatException e2) {
                    return new ValorConjuntCategoric(s);
                }
                return new ValorConjuntNumeric(s);
            }
            return new ValorCategoric(s);
        }
        return new ValorNumeric(d);
    }

    private Map<String, ValorAtribut<?>> obtenirAtributs(TipusItem tipusItem, ArrayList<String> valors) throws IllegalArgumentException {
        if (tipusItem.obtenirTipusAtributs().size() != valors.size()) {
            throw new IllegalArgumentException("No es poden obtenir els atributs d'un ítem d'un TipusItem i un conjunt de valors de mides diferents.");
        }
        Map<String, ValorAtribut<?>> atributs = new HashMap<>();
        int index = 0;
        for (Map.Entry<String, TipusAtribut> atribut : tipusItem.obtenirTipusAtributs().entrySet()) {
            atributs.put(atribut.getKey(), dedueixValorAtribut(valors.get(index)));
            ++index;
        }
        return atributs;
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
     * tipus. Altrament, retorna INF.
     */
    public double obtenirDistancia(Item item) {
        if (!tipusItem.equals(item.tipusItem)) {
            return Double.POSITIVE_INFINITY;
        }
        double distancia = 0.0;
        for (Map.Entry<String, TipusAtribut> entrada : tipusItem.obtenirTipusAtributs().entrySet()) {
            distancia += entrada.getValue().obtenirDistancia(atributs.get(entrada.getKey()),
                    item.atributs.get(entrada.getKey()));
        }
        return distancia;
    }
}
