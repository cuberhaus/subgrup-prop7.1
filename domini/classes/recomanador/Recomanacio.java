package domini.classes.recomanador;

import domini.classes.Id;

/**
 * Representa una recomanació i l'ítem a la que va enllaçada.
 * @author pablo.vega
 */
public class Recomanacio implements Comparable<Recomanacio>{
    /** Representa l'Id de l'ítem. */
    private Id id;

    /** Representa el valor de la recomanació. */
    private double rate;

    /**
     * Contructora de recomnacio donta un id i un valor de la recomanacio
     * @param id <code>Id</code> id és l'identificador de l'ítem.
     * @param rate <code>double</code> rate és el valor de la recomanació.
     */
    public Recomanacio(Id id, double rate) {
        this.id = id;
        this.rate = rate;
    }

    /**
     * Retorna la id del ítem de la valoració.
     * @return <code>int</code>
     */
    public int obtenirId() {
        return this.id.getValor();
    }

    /**
     * Obté el rate de la valoració.
     * @return <code>double</code>
     */
    public double obtenirRate() {
        return this.rate;
    }

    /**
     * Imprimeix el valor de la recomanació.
     */
    public void imprimir() {
        System.out.print(this.id.getValor());
        System.out.print(' ');
        System.out.println(this.rate);
    }

    @Override
    public int compareTo(Recomanacio rec) {
        return Double.compare(this.obtenirRate(), rec.obtenirRate());
    }
}

