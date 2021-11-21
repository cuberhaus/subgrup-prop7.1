package domini.classes.recomanador;

import domini.classes.Pair;
import domini.classes.recomanador.Recomanacio;

import java.util.*;

/**
 * Representa un conjunt de recomanacions ordenat gran a petit segon el valor de la recomanació
 * @author pablo.vega
 */
public class ConjuntRecomanacions {

    /** Contenidor de les recomanacions */
    private final ArrayList<Recomanacio> conjuntRecomanacions;

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
     * @param recs <code>ArrayList&lt;ArrayList&lt;Recomanacio&gt;&gt;</code> recs és el conjunt de recomanacions.
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
     * @return <code>ArrayList&lt;Recomanacio&gt;</code>
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
     * @param a numero
     * @return log_2(a)
     */
    private static double log2(double a){
        return Math.log(a)/Math.log(2);
    }

    /**
     * @param valoracions Valoracions (id, valoracio) fetes per l'usuari sobre el conjunt d'items recomanables.
     * @return El Discounted Cumulative Gain associat a les recomanacions donades les valoracions.
     */
    public double calculaDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaDiscountedCumulativeGain(valoracions, conjuntRecomanacions.size());
    }

    /**
     * @param valoracions Valoracions (id, valoracio) fetes per l'usuari sobre el conjunt d'items recomanables.
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
        for(int i = 0; i < Math.min(conjuntRecomanacions.size(), p); ++i) {
            double rel = 0;
            if (id_to_valoracio.containsKey(conjuntRecomanacions.get(i).obtenirId().obtenirValor()))
                rel = id_to_valoracio.get(conjuntRecomanacions.get(i).obtenirId().obtenirValor());
            discountedCumulativeGain += (Math.pow(2,rel)-1)/log2(i+2);
        }
        return discountedCumulativeGain;
    }

    /**
     * @param valoracions Valoracions (id, valoracio) fetes per l'usuari sobre el conjunt d'items recomanables.
     * @param p nombre de recomanacions sobre el que calcular p.
     * @return El Ideal Discounted Cumulative Gain assolible amb p recomanacions.
     */
    public double calculaIdealDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        PriorityQueue<Double> pq = new PriorityQueue<>();
        for (var x : valoracions) {
            if (pq.size() < p) {
                pq.add(x.y);
            }
            else if(!pq.isEmpty() && pq.peek() < x.y) {
                pq.remove();
                pq.add(x.y);
            }
        }
        Double[] top_val = pq.toArray(new Double[0]);
        Arrays.sort(top_val, Collections.reverseOrder());
        idealDiscountedCumulativeGain = 0.;
        for(int i = 0; i < top_val.length; ++i) {
            double rel = top_val[i];
            idealDiscountedCumulativeGain += (Math.pow(2,rel)-1)/log2(i+2);
        }
        return idealDiscountedCumulativeGain;
    }

    /**
     * A més de retornar el NDCG actualitza el valor de DCG i IDCG consultables amb <code>obteDCG()</code> i <code>obteIDCG()</code>
     * @param valoracions Valoracions (id, valoracio) fetes per l'usuari sobre el conjunt d'items recomanables.
     * @return El Normalized Discounted Cumulative Gain associat a les p primeres recomanacions donades les valoracions (entre 0 i 1).
     */
    public double calculaNormalizedDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaNormalizedDiscountedCumulativeGain(valoracions, conjuntRecomanacions.size());
    }

    /**
     * A més de retornar el NDCG actualitza el valor de dcg i idcg consultables amb <code>obteDCG()</code> i <code>obteIDCG()</code>
     * @param valoracions Valoracions (id, valoracio) fetes per l'usuari sobre el conjunt d'items recomanables.
     * @param p nombre de recomanacions a tenir en compte.
     * @return El Normalized Discount Cumulative Gain associat a les p primeres recomanacions donades les valoracions (entre 0 i 1).
     */
    public double calculaNormalizedDiscountedCumulativeGain(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        return calculaDiscountedCumulativeGain(valoracions, p) / calculaIdealDiscountedCumulativeGain(valoracions, p);
    }

    public double obteDiscountedCumulativeGain() { return discountedCumulativeGain; }
    public double obteIdealDiscountedCumulativeGain() { return idealDiscountedCumulativeGain; }
}
