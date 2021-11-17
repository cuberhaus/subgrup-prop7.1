package metode_recomanador;

import domini.classes.metode_recomanador.ConjuntPunts;
import domini.classes.metode_recomanador.KMeans;
import domini.classes.metode_recomanador.Punt;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class KMeansTest {

    @Test
    public void getParticions() {
        int k = 3;
        ConjuntPunts cjt = new ConjuntPunts();
        List<Double> x = Arrays.asList(1., 1., 3., 5., 6., 7.,8.);
        List<Double> y = Arrays.asList(3.,4.,5.,1.,2.,2.,8.);
        for (int i = 0; i < x.size(); ++i) {
            Punt punt = new Punt();
            punt.add(x.get(i)); punt.add(y.get(i));
            cjt.add(punt);
        }
        KMeans kmeans = new KMeans(cjt,k);
        ArrayList<ArrayList<Integer>> particions = kmeans.getParticions();
        ArrayList<Integer> part1 = new ArrayList<>(Arrays.asList(0,1,2));
        ArrayList<Integer> part2 = new ArrayList<>(Arrays.asList(3,4,5));
        ArrayList<Integer> part3 = new ArrayList<>(List.of(6));
        assertTrue(part1.equals(particions.get(0)) || part1.equals(particions.get(1)) || part1.equals(particions.get(2)));
        assertTrue(part2.equals(particions.get(0)) || part2.equals(particions.get(1)) || part2.equals(particions.get(2)));
        assertTrue(part3.equals(particions.get(0)) || part3.equals(particions.get(1)) || part3.equals(particions.get(2)));
    }

}