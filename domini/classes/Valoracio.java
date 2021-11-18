package domini.classes;

import java.util.Objects;

/**
 * Representa una valoració.
 *
 * @author pol.casacuberta
 */

public class Valoracio {
    /** Conté el valor de la valoració */
    private double valor;

    /** Conté l'usuari que escriu la valoració */
    private Usuari usuari;

    /** Conté l'item valorat */
    private Item item;
    // TODO: preguntar al sergio si això de treure getters i setters i afegir directament des de la constructora es bona idea

    /** Constructora donat un valor, un usuari i un item.
     * @param valor representa el valor de la valoració
     * @param usuari representa l'usuari que ha fet la valoració
     * @param item representa l'item valorat
     */
    public Valoracio(double valor, Usuari usuari, Item item) {
        this.valor = valor;
        this.usuari = usuari;
        this.item = item;
        usuari.afegirValoracio(this);
        item.afegirValoracio(this);
    }

    /**
     * Consultora del item
     *
     * @return El resultat és l'item del paràmetre implícit
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
     * @return El resultat és l'usuari del paràmetre implícit
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
     * Consultora del valor
     *
     * @return El resultat és el valor del paràmetre implícit
     */
    public double getValor() {
        return valor;
    }

    /**
     * Modificadora del paràmetre valor
     *
     * @param valor El paràmetre valor pren el nou valor
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valoracio valoracio = (Valoracio) o;
        return usuari.equals(valoracio.usuari) && item.equals(valoracio.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuari, item);
    }
}
