package domini.classes;

import java.util.*;

/**
 * @author pablo.vega
 */
public class ConjuntDeRecomanacions {
    private ArrayList<Recomanacio> conjuntRecomanacions;
    private Double dgc = null;
    private Double idgc = null;
    public ConjuntDeRecomanacions() {
        this.conjuntRecomanacions = new ArrayList<>();
    }

    public ConjuntDeRecomanacions(Recomanacio rec) {
        this.conjuntRecomanacions = new ArrayList<>();
        this.conjuntRecomanacions.add(rec);
    }

    public ConjuntDeRecomanacions(ArrayList<Recomanacio> recs) {
        this.conjuntRecomanacions = new ArrayList<>(recs);
        this.conjuntRecomanacions.sort(Collections.reverseOrder());
    }

    public void afegirRecomanacio(Recomanacio rec) {
        this.conjuntRecomanacions.add(rec);
        dgc = null;
        idgc = null;
        //Collections.sort(this.ConjuntRecomanacions);
    }

    public void ordena() {
        this.conjuntRecomanacions.sort(Collections.reverseOrder());
    }

    public ArrayList<Recomanacio> obtenirConjuntRecomanacions() {
        return new ArrayList<>(this.conjuntRecomanacions);
    }

    public void imprimir() {
        for (Recomanacio rec : this.conjuntRecomanacions) {
            rec.imprimir();
        }
    }

    public double obteDGC() { return dgc; }
    public double obteIDGC() { return idgc; }
    public double obteNDGC() { return dgc/idgc; }

    private static double log2(double a){
        return Math.log(a)/Math.log(2);
    }

    public double calculaDGC(ArrayList<Pair<Integer,Double>> valoracions) {
        return dgc = calculaDGC(valoracions, conjuntRecomanacions.size());
    }

    // @pre: recomanacions ordenades segons el sistema.
    public double calculaDGC(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        ordena();
        Map<Integer,Double> id_to_valoracio = new HashMap<>();
        for(Pair<Integer,Double> x : valoracions) {
            id_to_valoracio.put(x.x, x.y);
        }
        dgc = 0.;
        int pos = 1;
        for(int i = 0; i < Math.min(conjuntRecomanacions.size(), p); ++i) {
            if (i > 0 && !Objects.equals(conjuntRecomanacions.get(i).obtenirRate(), conjuntRecomanacions.get(i - 1).obtenirRate()))
                pos = i+1;
            double rel = 0;
            if (id_to_valoracio.containsKey(conjuntRecomanacions.get(i).obtenirId()))
                rel = id_to_valoracio.get(conjuntRecomanacions.get(i).obtenirId());
            dgc += (Math.pow(2,rel)-1)/log2(pos);
        }
        return dgc;
    }

    public double calculaIDGC(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        PriorityQueue<Double> pq = new PriorityQueue<>();
        for (var x : valoracions) {
            if (pq.size() < p) {
                pq.add(x.y);
            }
            else if(pq.poll() < x.y) {
                pq.remove();
                pq.add(x.y);
            }
        }
        Double[] top_val = pq.toArray(new Double[0]);
        Arrays.sort(top_val, Collections.reverseOrder());
        double dgc = 0;
        int pos = 1;
        for(int i = 0; i < top_val.length; ++i) {
            if (i > 0 && !Objects.equals(top_val[i], top_val[i - 1]))
                pos = i+1;
            double rel = top_val[i];
            dgc += (Math.pow(2,rel)-1)/log2(pos);
        }
        return dgc;
    }

    public double calculaNDGC(ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaNDGC(valoracions, conjuntRecomanacions.size());
    }

    public double calculaNDGC( ArrayList<Pair<Integer,Double>> valoracions, int p) {
        if (dgc == null) calculaDGC(valoracions, p);
        if (idgc == null) calculaIDGC(valoracions, p);
        return dgc/idgc;
    }
}
