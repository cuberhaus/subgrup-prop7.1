package domini.classes;

import java.util.*;

public final class DGC {
    private static double log2(double a){
        return Math.log(a)/Math.log(2);
    }
    public static double calculaDGC(ArrayList<Pair<Integer, Double>> recomanacions, ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaDGC(recomanacions, valoracions, recomanacions.size());
    }
        // @pre: recomanacions ordenades segons el sistema.
    public static double calculaDGC(ArrayList<Pair<Integer, Double>> recomanacions, ArrayList<Pair<Integer,Double>> valoracions, int p) {
        Map<Integer,Double> id_to_valoracio = new HashMap<>();
        for(Pair<Integer,Double> x : valoracions) {
            id_to_valoracio.put(x.x, x.y);
        }
        double dgc = 0;
        int pos = 1;
        for(int i = 0; i < Math.min(recomanacions.size(), p); ++i) {
            if (i > 0 && !Objects.equals(recomanacions.get(i).y, recomanacions.get(i - 1).y))
                pos = i+1;
            double rel = 0;
            if (id_to_valoracio.containsKey(recomanacions.get(i).x))
                rel = id_to_valoracio.get(recomanacions.get(i).x);
            dgc += (Math.pow(2,rel)-1)/log2(pos);
        }
        return dgc;
    }
    public static double calculaIDGC(ArrayList<Pair<Integer,Double>> valoracions, int p) {
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

    public static double calculaNDGC(ArrayList<Pair<Integer, Double>> recomanacions, ArrayList<Pair<Integer,Double>> valoracions) {
        return calculaNDGC(recomanacions, valoracions, recomanacions.size());
    }

    public static double calculaNDGC(ArrayList<Pair<Integer, Double>> recomanacions, ArrayList<Pair<Integer,Double>> valoracions, int p) {
        return calculaDGC(recomanacions, valoracions, p) / calculaIDGC(valoracions, p);
    }
}
