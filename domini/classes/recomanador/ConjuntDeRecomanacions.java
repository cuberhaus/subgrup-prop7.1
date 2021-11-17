package domini.classes.recomanador;

import domini.classes.Pair;

import java.util.*;

/**
 * Representa un conjunt de recomanacions ordenat gran a petit segon el valor de la recomanació
 * @author pablo.vega
 */
public class ConjuntDeRecomanacions {

    /** Contenidor de les recomanacions */
    private ArrayList<Recomanacio> conjuntRecomanacions;

    /** */
    private Double dcg = null;

    /** */
    private Double idcg = null;

    /** Constructora buida per defecte */
    public ConjuntDeRecomanacions() {
        this.conjuntRecomanacions = new ArrayList<>();
    }

    /**
     * Contructora a partir d'una recomanació
     * @param rec <code>Recomanacio</code> rec és la primera recomanació.
     */
    public ConjuntDeRecomanacions(Recomanacio rec) {
        this.conjuntRecomanacions = new ArrayList<>();
        this.conjuntRecomanacions.add(rec);
    }

    /**
     * Constructora a partir d'un conjunt de recomanacions.
     * @param recs <code>ArrayList<ArrayList<Recomanacio></code> recs és el conjunt de recomanacions.
     */
    public ConjuntDeRecomanacions(ArrayList<Recomanacio> recs) {
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
        dcg = null;
        idcg = null;
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
     * @return El Discount Cumulative Gain associat a les recomanacions donades les valoracions.
     */
    public double calculaDCG(ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaDCG(valoracions, conjuntRecomanacions.size());
    }

    /**
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @param p nombre de recomanacions a tenir en compte.
     * @return El Discount Cumulative Gain associat a les p primeres recomanacions donades les valoracions.
     */
    public double calculaDCG(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        ordena();
        Map<Integer,Double> id_to_valoracio = new HashMap<>();
        for(Pair<Integer,Double> x : valoracions) {
            id_to_valoracio.put(x.x, x.y);
        }
        dcg = 0.;
        int pos = 1;
        for(int i = 0; i < Math.min(conjuntRecomanacions.size(), p); ++i) {
            if (i > 0 && !Objects.equals(conjuntRecomanacions.get(i).obtenirRate(), conjuntRecomanacions.get(i - 1).obtenirRate()))
                pos = i+1;
            double rel = 0;
            if (id_to_valoracio.containsKey(conjuntRecomanacions.get(i).obtenirId()))
                rel = id_to_valoracio.get(conjuntRecomanacions.get(i).obtenirId());
            dcg += (Math.pow(2,rel)-1)/log2(pos);
        }
        return dcg;
    }

    /**
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @param p nombre de recomanacions sobre el que calcular p.
     * @return El Ideal Discount Cumulative Gain assolible amb p recomanacions.
     */
    public double calculaIDCG(ArrayList<Pair<Integer,Double>> valoracions, int p) {
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
        idcg = 0.;
        int pos = 1;
        for(int i = 0; i < top_val.length; ++i) {
            if (i > 0 && !Objects.equals(top_val[i], top_val[i - 1]))
                pos = i+1;
            double rel = top_val[i];
            idcg += (Math.pow(2,rel)-1)/log2(pos);
        }
        return idcg;
    }

    /**
     * A més de retornar el NDCG actualitza el valor de dcg i idcg consultables amb <code>obteDCG()</code> i <code>obteIDCG()</code>
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @return El Normalized Discount Cumulative Gain associat a les p primeres recomanacions donades les valoracions (entre 0 i 1).
     */
    public double calculaNDCG(ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaNDCG(valoracions, conjuntRecomanacions.size());
    }

    /**
     * A més de retornar el NDCG actualitza el valor de dcg i idcg consultables amb <code>obteDCG()</code> i <code>obteIDCG()</code>
     * @param valoracions Valoracions fetes per l'usuari sobre el conjunt d'items recomanables.
     * @param p nombre de recomanacions a tenir en compte.
     * @return El Normalized Discount Cumulative Gain associat a les p primeres recomanacions donades les valoracions (entre 0 i 1).
     */
    public double calculaNDCG(ArrayList<Pair<Integer,Double>> valoracions, int p) {
        calculaDCG(valoracions, p);
        calculaIDCG(valoracions, p);
        return dcg / idcg;
    }

    public double obteDCG() { return dcg; }
    public double obteIDCG() { return idcg; }
}
