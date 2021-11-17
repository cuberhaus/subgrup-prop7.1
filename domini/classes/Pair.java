package domini.classes;
/**
 * Representa una parella d'<code>Objectes</code> de qualssevol classes.
 * S'ordena pel primer element i per tant ha de ser <code>Comparable</code>
 * @author edgar.moreno
 */
public class Pair<X extends Comparable<X>,Y> implements Comparable<Pair<X,Y>> {
    /** Primer element de la parella */
    public final X x;
    /** Segon element de la parella */
    public final Y y;

    public X x() { return x; }
    public Y y() { return y; }

    /**
     * Inicialitza una parella
     * @param x primer element
     * @param y segon element
     */
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Pair<X,Y> object) {
        return x.compareTo(object.x);
    }
}
