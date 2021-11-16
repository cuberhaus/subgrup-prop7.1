package domini.classes;

/**
 * Representa una valoració.
 *
 * @author pol.casacuberta
 */

public class Valoracio {
    private double valor;
    private Usuari usuari;
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
     * Indica si dues valoracions són iguals
     *
     * @return El resultat retorna true si són iguals, altrament retorna false.
     */
    @Override
    public boolean equals(Object obj) {
        Valoracio val = (Valoracio)obj;
        return this.usuari.equals(val.usuari) && this.item.equals(val.item);
    }

    /**
     * Calcula un codi de hash idèntic per a les valoracions amb el mateix usuari i item,
     * altrament retorna un hash diferent.
     * @return El resultat retorna true si són iguals, altrament retorna false.
     */
    @Override
    public int hashCode() {
        int prime1 = 29;
        int prime2 = 17;

        int hash1, hash2;
        // Comprovem que els atributs no siguin nulls
        if (usuari == null) hash1 = 0;
        else hash1 = usuari.hashCode();
        if (item == null) hash2 = 0;
        else hash2 = item.hashCode();

        int hash = prime1;
        hash = hash * prime2 + hash1;
        hash = hash * prime2 + hash2;
        return hash;
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
     * Consultora del valor
     *
     * @return El resultat és el valor del P.I.
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
}
