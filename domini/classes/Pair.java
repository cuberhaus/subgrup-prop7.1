package domini.classes;

/**
 * Representa una parella d'<code>Objectes</code> de qualssevol classes.
 * Perquè sigui comparable els dos objectes han d'extendre <code>Comparable</code>
 * @author edgar.moreno
 */
public class Pair<X extends Comparable<X>,Y extends Comparable<Y>> implements Comparable<Pair<X,Y>> {
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
        int primer = x.compareTo(object.x);
        if (primer == 0) return y.compareTo(object.y);
        return primer;
    }
}
