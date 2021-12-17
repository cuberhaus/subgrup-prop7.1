package domini.classes.recomanador;

import domini.classes.Id;

/**
 * Representa una recomanació i l'ítem a la que va enllaçada.
 * @author pablo.vega
 */
public class Recomanacio implements Comparable<Recomanacio>{
    /** Representa l'Id de l'ítem. */
    private final Id id;

    /** Representa el valor de la recomanació. */
    private final double seguretat;

    /**
     * Contructora de recomanació donat un id i un valor de la recomanació
     * @param id <code>Id</code> id és l'identificador de l'ítem.
     * @param seguretat <code>double</code> rate és el valor de la recomanació.
     */
    public Recomanacio(Id id, double seguretat) {
        this.id = id;
        this.seguretat = seguretat;
    }

    /**
     * Retorna la id del ítem de la valoració.
     * @return <code>Id</code>
     */
    public Id obtenirId() {
        return this.id;
    }

    /**
     * Obté el rate de la valoració.
     * @return <code>double</code>
     */
    public double obtenirSeguretat() {
        return this.seguretat;
    }

    /**
     * Imprimeix el valor de la recomanació.
     */
    public void imprimir() {
        System.out.print(this.id.obtenirValor());
        System.out.print(' ');
        System.out.println(this.seguretat);
    }

    @Override
    public int compareTo(Recomanacio o) {
        return Double.compare(this.obtenirSeguretat(), o.obtenirSeguretat());
    }
}

