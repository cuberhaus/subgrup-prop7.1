package domini.classes;

import domini.classes.atributs.tipus.*;
import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorBoolea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Representa un tipus d'ítem.
 * @author maria.prat
 */
public class TipusItem {
    private String nom;
    /** Relaciona el nom d'un atribut amb el seu tipus. */
    private Map<String, TipusAtribut> tipusAtributs;

    /**
     * Crea un <code>TipusItem</code> amb el nom i el <code>Map<String, TipusAtribut></code> donats.
     * @param nom <code>String</code> que conté el nom del <code>TipusItem</code>.
     * @param tipusAtributs <code>Map<String, TipusAtribut></code> que conté els tipus d'atributs del <code>TipusItem</code>.
     */
    public TipusItem(String nom, Map<String, TipusAtribut> tipusAtributs) {
        this.nom = nom;
        this.tipusAtributs = tipusAtributs;
    }

    /**
     * Crea un <code>TipusItem</code> donats el nom del <code>TipusItem</code>, els noms dels atributs i un conjunt de valors.
     * Assigna el <code>TipusAtribut</code>. per defecte en funció del tipus de cada valor.
     * @param nomTipusItem <code>String</code> que conté el nom del <code>TipusItem</code>.
     * @param nomsAtributs <code>ArrayList<String></code> que conté el nom dels atributs del <code>TipusItem</code>.
     * @param valors <code>ArrayList<String></code> que conté el conjunt de valors d'un item del <code>TipusItem</code>
     *               corresponents als noms que conté <code>nomsAtributs</code>.
     */
    public TipusItem(String nomTipusItem, ArrayList<String> nomsAtributs, ArrayList<String> valors) throws IllegalArgumentException {
        this.nom = nomTipusItem;
        this.tipusAtributs = new HashMap<>();
        if (nomsAtributs.size() != valors.size()) {
            throw new IllegalArgumentException("No es pot deduir el TipusItem d'un conjunt de noms i valors de mides diferents.");
        }
        for (int i = 0; i < nomsAtributs.size(); ++i) {
            this.tipusAtributs.put(nomsAtributs.get(i), dedueixTipusAtribut(valors.get(i)));
        }
    }

    private TipusAtribut dedueixTipusAtribut(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e1) {
            if (ValorBoolea.esBoolea(s)) {
                return new Discret();
            }
            if (s.contains(";")) {
                String primerValor = s.split(";", 2)[0];
                try {
                    Double.parseDouble(primerValor);
                } catch (NumberFormatException e2) {
                    // TODO(maria): pensar si volem que aquest mètode pugui llegir conjunts de booleans (caldria iterar
                    // per tots els elements per assegurar que són tots booleans. L'alternativa és fer el cast més endavant
                    // si l'usuari ho demana.
                    return new DiferenciaDeConjunts();
                }
                return new Euclidia();
            }
            return new Levenshtein();
        }
        return new Euclidia();
    }

    /**
     * Assigna el nom del <code>TipusItem</code>.
     * @param nom <code>String</code> que conté el nom del <code>TipusItem</code>.
     */
    public void assignarNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return <code>String</code> que conté el nom del <code>TipusItem</code>.
     */
    public String obtenirNom() {
        return nom;
    }

    /**
     * Assigna els <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * @param tipusAtributs <code>Map<String, TipusAtribut></code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public void assignarTipusAtributs(Map<String, TipusAtribut> tipusAtributs) {
        this.tipusAtributs = tipusAtributs;
    }

    /**
     * @return <code>Map<String, TipusAtribut></code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public Map<String, TipusAtribut> obtenirTipusAtributs() {
        Map<String, TipusAtribut> copiaTipusAtributs = new HashMap<>();
        for (Map.Entry<String, TipusAtribut> entrada : tipusAtributs.entrySet()) {
            copiaTipusAtributs.put(entrada.getKey(), entrada.getValue().copy());
        }
        return copiaTipusAtributs;
    }

    /**
     * Afegeix el <code>TipusAtribut</code> amb el nom donats als <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * Si <code>tipusAtributs</code> ja contenia un tipus d'atribut amb el nom donat, el sobreescriu.
     * @param nomTipusAtribut Nom del <code>TipusAtribut</code> que s'afegirà als <code>tipusAtributs</code> del
     *                        <code>TipusItem</code>.
     * @param tipusAtribut <code>TipusAtribut</code> que s'afegirà als <code>tipusAtributs</code> del
     *                     <code>TipusItem</code>.
     */
    public void afegirTipusAtribut(String nomTipusAtribut, TipusAtribut tipusAtribut) {
        this.tipusAtributs.put(nomTipusAtribut, tipusAtribut);
    }

    /**
     * Un conjunt d'atributs és compatible amb un tipus d'ítem si hi ha una bijecció entre el conjunt d'atributs i el
     * conjunt de tipus d'atributs del tipus d'ítem, de manera que cada atribut es relaciona amb un tipus d'atribut amb
     * el mateix nom i el mateix TipusAtribut.
     * @param atributs <code>Map<String, ValorAtribut></code> que relaciona el nom d'un atribut amb el seu <code>ValorAtribut</code>.
     * @return Retorna cert si els atributs donats són compatibles amb el tipus d'ítem actual i, altrament, retorna fals.
     */
    public boolean esCompatible(Map<String, ValorAtribut<?>> atributs) {
        if (tipusAtributs.size() != atributs.size()) {
            return false;
        }
        for (Map.Entry<String, TipusAtribut> entrada : tipusAtributs.entrySet()) {
            if (!atributs.containsKey(entrada.getKey())) {
                return false;
            }
            if (!entrada.getValue().admetValorAtribut(atributs.get(entrada.getKey()))) {
                return false;
            }
        }
        return true;
    }
}
