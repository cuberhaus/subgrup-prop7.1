package domini.classes.recomanador.metode_recomanador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Processa un conjunt de punts i permet obtenir les particions mitjançant l'algorisme de K-means.
 * @author edgar.moreno
 */
public class KMeans {
    private final int k;
    private final int n_punts;
    private final ConjuntPunts[] particions;
    private final int[] conjunt_del_punt;
    private final ConjuntPunts punts;
    private final Punt[] baricentres;

    /**
     * Inicialitza l'algorisme amb el conjunt de punts desitjat.
     * @param punts Conjunt de punts per executar l'algorisme. Els identificadors han de ser contigus començant en 0.
     * @param k Nombre de particions a realitzar. Han de ser menors al nombre de punts.
     */
    public KMeans(ConjuntPunts punts, int k) {
        this.k = k;
        this.punts = punts;
        n_punts = punts.obtenirNumPunts();
        particions = new ConjuntPunts[k];
        conjunt_del_punt = new int[n_punts];
        baricentres = new Punt[k];
        inicialitzarEnOrdre();
    }

    /**
     * @return Les particions obtingudes amb l'algorisme.
     */
    public ArrayList<ArrayList<Integer>> getParticions() {
        processa();
        ArrayList<ArrayList<Integer>> id_parts = new ArrayList<>(k);
        for (int i = 0; i < k; ++i) {
            id_parts.add(new ArrayList<>(particions[i].keySet()));
        }
        return id_parts;
    }

    private void inicialitzarAleatori() {
        Arrays.fill(conjunt_del_punt, -1);
        Random r = new Random();
        for (int i = 0; i < k; ++i)
        {
            particions[i] = new ConjuntPunts();
            int center;
            do {
                center = r.nextInt(n_punts);
            } while (conjunt_del_punt[center] != -1);
            conjunt_del_punt[center] = i;
            particions[i].put(center, punts.get(center));
        }
        guardaBaricentres();
        for (int i = 0; i < n_punts; ++i) {
            if (conjunt_del_punt[i] == -1) {
                int nou_conjunt = buscaBaricentreProper(i);
                conjunt_del_punt[i] = nou_conjunt;
                particions[nou_conjunt].put(i, punts.get(i));
            }
        }
    }

    private void inicialitzarEnOrdre() {
        Arrays.fill(conjunt_del_punt, -1);
        Random r = new Random();
        for (int i = 0; i < k; ++i)
        {
            particions[i] = new ConjuntPunts();
            conjunt_del_punt[i] = i;
            particions[i].put(i, punts.get(i));
        }
        guardaBaricentres();
        for (int i = 0; i < n_punts; ++i) {
            if (conjunt_del_punt[i] == -1) {
                int nou_conjunt = buscaBaricentreProper(i);
                conjunt_del_punt[i] = nou_conjunt;
                particions[nou_conjunt].put(i, punts.get(i));
            }
        }
    }
    // TODO: inicialitzacio no aleatoria?

    private void canviaDeConjunt(int punt, int nou_conjunt) {
        particions[conjunt_del_punt[punt]].remove(punt);
        conjunt_del_punt[punt] = nou_conjunt;
        particions[nou_conjunt].put(punt, punts.get(punt));
    }
    private void processa() {
        while(iteracio());
    }

    private boolean iteracio() {
        boolean canvi = false;
        guardaBaricentres();
        for (int i = 0; i < n_punts; ++i) {
            int nou_conjunt = buscaBaricentreProper(i);
            if (nou_conjunt != conjunt_del_punt[i]) {
                canvi = true;
                canviaDeConjunt(i, nou_conjunt);
            }
        }
        return canvi;
    }

    private int buscaBaricentreProper(int id) {
        Punt punt = punts.get(id);
        double distancia = punt.distancia(baricentres[0]);
        int conjunt = 0;
        for (int i = 1; i < k; ++i) {
            double nova_dist = punt.distancia(baricentres[i]);
            if (nova_dist < distancia){
                conjunt = i;
                distancia = nova_dist;
            }
        }
        return conjunt;
    }

    private void guardaBaricentres() {
        for (int i = 0; i < k; ++i) {
            if (particions[i].obtenirNumPunts() != 0)
                baricentres[i] = particions[i].obtenirBaricentre();
        }
    }
}
