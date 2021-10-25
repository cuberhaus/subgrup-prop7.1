package Dominio;

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
     * Crea un <code>TipusItem</code> amb el nom donat.
     * @param nom Nom del <code>TipusItem</code>.
     */
    public TipusItem(String nom) {
        this.nom = nom;
    }

    /**
     * Assigna el nom del <code>TipusItem</code>.
     * @param nom String que conté el nom del <code>TipusItem</code>.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté el nom d'un <code>TipusItem</code>.
     * @return String que conté el nom del <code>TipusItem</code>.
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
     * Obté els <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * @return <code>Map<String, TipusAtribut></code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public Map<String, TipusAtribut> getTipusAtributs() {
        return tipusAtributs;
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
}
