package domini.classes.metode_recomanador;

import domini.classes.Pair;

import java.util.*;

/**
 * Representa un conjunt de recomanacions ordenat gran a petit segon el valor de la recomanació
 * @author pablo.vega
 */
public class ConjuntRecomanacions {

    /** Contenidor de les recomanacions */
    private ArrayList<Recomanacio> conjuntRecomanacions;

    /** Discounted Cumulative Gain del conjunt de recomanacions */
    private Double discountedCumulativeGain = null;

    /** Ideal Discounted Cumulative gain del conjunt de recomanacions */
    private Double idealDiscountedCumulativeGain = null;

    /** Constructora buida per defecte */
    public ConjuntRecomanacions() {
        this.conjuntRecomanacions = new ArrayList<>();
    }

    /**
     * Contructora a partir d'una recomanació
     * @param rec <code>Recomanacio</code> rec és la primera recomanació.
     */
    public ConjuntRecomanacions(Recomanacio rec) {
        this.conjuntRecomanacions = new ArrayList<>();
        this.conjuntRecomanacions.add(rec);
    }

    /**
     * Constructora a partir d'un conjunt de recomanacions.
     * @param recs <code>ArrayList<ArrayList<Recomanacio>></code> recs és el conjunt de recomanacions.
     */
    public ConjuntRecomanacions(ArrayList<Recomanacio> recs) {
        this.conjuntRecomanacions = new ArrayList<>(recs);
        this.conjuntRecomanacions.sort(Collections.reverseOrder());
    }

    /**
     * Afegeix una recomanació sempre mantenint l'ordre.
     * @param rec <code>Recomanacio</code> és la recomanació a afegir dins el conjunt.
     */
    public void afegirRecomanacio(Recomanacio rec) {
        this.conjuntRecomanacions.add(rec);
        this.ordena();
        discountedCumulativeGain = null;
        idealDiscountedCumulativeGain = null;
    }

    /**
     * Ordena el conjunt de recomanacions.
     */
    public void ordena() {
        this.conjuntRecomanacions.sort(Collections.reverseOrder());
    }

    /**
     * Retorna el conjunt ordenat.
     * @return <code>ArrayList<Recomanacio></code>
     */
    public ArrayList<Recomanacio> obtenirConjuntRecomanacions() {
        return new ArrayList<>(this.conjuntRecomanacions);
    }

    /**
     * Imprimeix el contingut del conjunt.
     */
    public void imprimir() {
        for (Recomanacio rec : this.conjuntRecomanacions) {
            rec.imprimir();
        }
    }

    /**
     * @return log_2(a)
     */
    private static double log2(double a){
        return Math.log(a)/Math.log(2);
    }

    /**
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @return El Discounted Cumulative Gain associat a les recomanacions donades les valoracions.
     */
    public double calculaDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaDiscountedCumulativeGain(valoracions, conjuntRecomanacions.size());
    }

    /**
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @param p nombre de recomanacions a tenir en compte.
     * @return El Discounted Cumulative Gain associat a les p primeres recomanacions donades les valoracions.
     */
    public double calculaDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        ordena();
        Map<Integer,Double> id_to_valoracio = new HashMap<>();
        for(Pair<Integer,Double> x : valoracions) {
            id_to_valoracio.put(x.x, x.y);
        }
        discountedCumulativeGain = 0.;
        int pos = 1;
        for(int i = 0; i < Math.min(conjuntRecomanacions.size(), p); ++i) {
            if (i > 0 && !Objects.equals(conjuntRecomanacions.get(i).obtenirRate(), conjuntRecomanacions.get(i - 1).obtenirRate()))
                pos = i+1;
            double rel = 0;
            if (id_to_valoracio.containsKey(conjuntRecomanacions.get(i).obtenirId()))
                rel = id_to_valoracio.get(conjuntRecomanacions.get(i).obtenirId());
            discountedCumulativeGain += (Math.pow(2,rel)-1)/log2(pos+1);
        }
        return discountedCumulativeGain;
    }

    /**
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @param p nombre de recomanacions sobre el que calcular p.
     * @return El Ideal Discounted Cumulative Gain assolible amb p recomanacions.
     */
    public double calculaIdealDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        PriorityQueue<Double> pq = new PriorityQueue<>();
        for (var x : valoracions) {
            if (pq.size() < p) {
                pq.add(x.y);
            }
            else if(!pq.isEmpty() && pq.poll() < x.y) {
                pq.remove();
                pq.add(x.y);
            }
        }
        Double[] top_val = pq.toArray(new Double[0]);
        Arrays.sort(top_val, Collections.reverseOrder());
        idealDiscountedCumulativeGain = 0.;
        int pos = 1;
        for(int i = 0; i < top_val.length; ++i) {
            if (i > 0 && !Objects.equals(top_val[i], top_val[i - 1]))
                pos = i+1;
            double rel = top_val[i];
            idealDiscountedCumulativeGain += (Math.pow(2,rel)-1)/log2(pos+1);
        }
        return idealDiscountedCumulativeGain;
    }

    /**
     * A més de retornar el NDCG actualitza el valor de DCG i IDCG consultables amb <code>obteDCG()</code> i <code>obteIDCG()</code>
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @return El Normalized Discounted Cumulative Gain associat a les p primeres recomanacions donades les valoracions (entre 0 i 1).
     */
    public double calculaNormalizedDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaNormalizedDiscountedCumulativeGain(valoracions, conjuntRecomanacions.size());
    }

    /**
     * A més de retornar el NDCG actualitza el valor de dcg i idcg consultables amb <code>obteDCG()</code> i <code>obteIDCG()</code>
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @param p nombre de recomanacions a tenir en compte.
     * @return El Normalized Discount Cumulative Gain associat a les p primeres recomanacions donades les valoracions (entre 0 i 1).
     */
    public double calculaNormalizedDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        return calculaDiscountedCumulativeGain(valoracions, p) / calculaIdealDiscountedCumulativeGain(valoracions, p);
    }

    public double obteDiscountedCumulativeGain() { return discountedCumulativeGain; }
    public double obteIdealDiscountedCumulativeGain() { return idealDiscountedCumulativeGain; }
}
