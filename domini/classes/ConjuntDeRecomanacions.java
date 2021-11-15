package domini.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author pablo.vega
 */
public class ConjuntDeRecomanacions {
    private ArrayList<Recomanacio> ConjuntRecomanacions;

    public ConjuntDeRecomanacions() {
        this.ConjuntRecomanacions = new ArrayList<>();
    }

    public ConjuntDeRecomanacions(Recomanacio rec) {
        this.ConjuntRecomanacions = new ArrayList<>();
        this.ConjuntRecomanacions.add(rec);
    }

    public ConjuntDeRecomanacions(ArrayList<Recomanacio> recs) {
        this.ConjuntRecomanacions = new ArrayList<Recomanacio>(recs);
        Collections.sort(this.ConjuntRecomanacions);
    }

    public void afegirRecomanacio(Recomanacio rec) {
        this.ConjuntRecomanacions.add(rec);
        //Collections.sort(this.ConjuntRecomanacions);
    }

    public void ordena() {
        Collections.sort(this.ConjuntRecomanacions);
    }

    public ArrayList<Recomanacio> obtenirConjuntRecomanacions() {
        return new ArrayList<Recomanacio>(this.ConjuntRecomanacions);
    }

    public void imprimir() {
        for (Recomanacio rec : this.ConjuntRecomanacions) {
            rec.imprimir();
        }
    }
}
