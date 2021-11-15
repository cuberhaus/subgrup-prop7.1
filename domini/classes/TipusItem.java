package domini.classes;

import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.valors.ValorAtribut;

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
     * Assigna el nom del <code>TipusItem</code>.
     * @param nom <code>String</code> que conté el nom del <code>TipusItem</code>.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return <code>String</code> que conté el nom del <code>TipusItem</code>.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Assigna els <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * @param tipusAtributs <code>Map<String, TipusAtribut></code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public void setTipusAtributs(Map<String, TipusAtribut> tipusAtributs) {
        this.tipusAtributs = tipusAtributs;
    }

    /**
     * @return <code>Map<String, TipusAtribut></code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public Map<String, TipusAtribut> getTipusAtributs() {
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
    public void addTipusAtribut(String nomTipusAtribut, TipusAtribut tipusAtribut) {
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
