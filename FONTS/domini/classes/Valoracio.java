package domini.classes;

import excepcions.UsuariIncorrecteException;

import java.util.Objects;

/**
 * Representa una valoració.
 *
 * @author pol.casacuberta
 */

public class Valoracio {
    /**
     * Conté el valor de la valoració
     */
    private final double valor;

    /**
     * Conté l'usuari que escriu la valoració
     */
    private final Usuari usuari;

    /**
     * Conté l'ítem valorat
     */
    private final Item item;

    /**
     * Constructora donat un valor, un usuari i un item.
     *
     * @param valor  representa el valor de la valoració
     * @param usuari representa l'usuari que ha fet la valoració
     * @param item   representa l'ítem valorat
     */
    public Valoracio(double valor, Usuari usuari, Item item) throws UsuariIncorrecteException {
        this.valor = valor;
        this.usuari = usuari;
        this.item = item;
        usuari.afegirValoracio(this);
        item.afegirValoracio(this);
    }

    /**
     * Consultora del item
     *
     * @return El resultat és l'ítem del paràmetre implícit
     */
    public Item obtenirItem() {
        return item.copiar();
    }

    /**
     * Consultora del usuari
     *
     * @return El resultat és l'usuari del paràmetre implícit
     */
    public Usuari obtenirUsuari() {
        return usuari;
    }

    /**
     * Consultora del valor
     *
     * @return El resultat és el valor del paràmetre implícit
     */
    public double obtenirValor() {
        return valor;
    }

    /**
     * Indica si dues valoracions són iguals.
     *
     * @return El resultat retorna true si són iguals, altrament retorna false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valoracio valoracio = (Valoracio) o;
        return usuari.equals(valoracio.usuari) && item.equals(valoracio.item);
    }

    /**
     * Retorna un codi de hash idèntic per a dues valoracions amb el mateix Usuari i Item,
     * altrament retorna un hash diferent
     */
    @Override
    public int hashCode() {
        return Objects.hash(usuari, item);
    }

    /**
     * Retorna una còpia de la valoració del paràmetre implícit
     *
     * @return Còpia del paràmetre implícit
     */
    public Valoracio copiar() throws UsuariIncorrecteException {
        return new Valoracio(valor, usuari.copiar(), item.copiar());
    }
}
