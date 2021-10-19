package Dominio;

import java.util.ArrayList;

/**
 * Representa un tipus d'ítem.
 * @author maria.prat
 */
public class TipusItem {
    private String nom;
    private ArrayList<TipusAtribut> tipusAtributs;

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
     * Assigna els <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * @param tipusAtributs <code>ArrayList</code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public void setTipusAtributs(ArrayList<TipusAtribut> tipusAtributs) {
        this.tipusAtributs = tipusAtributs;
    }

    /**
     * Obté el nom d'un <code>TipusItem</code>.
     * @return String que conté el nom del <code>TipusItem</code>.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obté els <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * @return <code>ArrayList</code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public ArrayList<TipusAtribut> getTipusAtributs() {
        return tipusAtributs;
    }

    /**
     * Afegeix un <code>TipusAtribut</code> als <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * @param tipusAtribut <code>TipusAtribut</code> que s'afegirà als <code>tipusAtributs</code> del
     *                     <code>TipusItem</code>.
     */
    public void addTipusAtributs(TipusAtribut tipusAtribut) {
        this.tipusAtributs.add(tipusAtribut);
    }
}
