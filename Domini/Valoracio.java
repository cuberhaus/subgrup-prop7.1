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
