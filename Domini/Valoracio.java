package Domini;

/**
 * Representa una valoració.
 *
 * @author pol.casacuberta
 */

public class Valoracio {
    private double valor;
    private Usuari usuari;
    private Item item;

    /**
     * Indica si dues valoracions són iguals
     *
     * @return El resultat retorna true si són iguals, altrament retorna false.
     */
    @Override
    public boolean equals(Object obj) {
        Valoracio val = (Valoracio)obj;
        if(this.usuari == val.usuari && this.item==val.item)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return this.usuari.hashCode() - this.item.hashCode();
    }

    /** Constructora donat un valor, un usuari i un item.
     * @param valor representa el valor de la valoració
     * @param usuari representa l'usuari que ha fet la valoració
     * @param item representa l'item valorat
     */
    public Valoracio(double valor, Usuari usuari, Item item) {
        this.valor = valor;
        this.usuari = usuari;
        this.item = item;
    }

    /**
     * Consultora del item
     *
     * @return El resultat és l'item del P.I.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Modificadora del paràmetre item
     *
     * @param item El paràmetre item pren el nou valor
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Consultora del usuari
     *
     * @return El resultat és l'usuari del P.I.
     */
    public Usuari getUsuari() {
        return usuari;
    }

    /**
     * Modificadora del paràmetre usuari
     *
     * @param usuari El paràmetre usuari pren el nou valor
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Modificadora del paràmetre valor
     *
     * @param valor El paràmetre valor pren el nou valor
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Consultora del valor
     *
     * @return El resultat és el valor del P.I.
     */
    public double getValor() {
        return valor;
    }
}
