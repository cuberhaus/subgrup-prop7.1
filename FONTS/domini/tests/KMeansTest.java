package domini.tests;

import domini.classes.recomanador.metode_recomanador.ConjuntPunts;
import domini.classes.recomanador.metode_recomanador.KMeans;
import domini.classes.recomanador.metode_recomanador.Punt;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Unit tests de la classe KMeans
 */

public class KMeansTest {
    private final int generadorRNG = 12345678;
    private final int modulRNG = 100000007;
    private int numeroActual = 1;

    private int seguentNumero() {
        return numeroActual = (numeroActual * generadorRNG)%modulRNG;
    }

    @Test
    public void getParticions() {
        int k = 3;
        ConjuntPunts cjt = new ConjuntPunts();
        // Mitjançant un dibuix és fàcil veure quins son els clusters esperats.
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


    @Test
    public void getParticionsGran() throws IOException {
        int k = 10;
        int d = 5;
        int num_punts = 50;
        int rang = 50;
        ConjuntPunts cjt = new ConjuntPunts();
        for (int i = 0; i < num_punts; ++i) {
            Punt punt = new Punt();
            for (int j = 0; j < d; ++j)
                punt.add((double)(seguentNumero() % rang));
            cjt.add(punt);
        }
        KMeans kmeans = new KMeans(cjt,k);
        ArrayList<ArrayList<Integer>> particions = kmeans.getParticions();
        for (var x : particions) {
            Collections.sort(x);
        }
        particions.sort(Comparator.comparing(o -> o.get(0)));
        File clustersGuardats = new File("../EXE/dades_tests/outputKMeansTest");
        Scanner clustersGuardatsReader = new Scanner(clustersGuardats);
        for (var x : particions) {
            for (var y : x) {
                assertEquals((int) y, clustersGuardatsReader.nextInt());
            }
        }
    }

}