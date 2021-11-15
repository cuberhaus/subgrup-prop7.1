package domini.classes;

import java.util.Comparator;

/**
 * @author pablo.vega
 */
public class Recomanacio implements Comparable<Recomanacio>{
    private Id id;
    private double rate;

    public Recomanacio(Id id, double rate) {
        this.id = id;
        this.rate = rate;
    }

    public int obtenirId() {
        return this.id.getValor();
    }

    public double obtenirRate() {
        return this.rate;
    }

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

