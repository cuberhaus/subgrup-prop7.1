package Domini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class KMeans {
    private final int k;
    private final int n_punts;
    private final ConjuntDePunts[] particions;
    private final int[] conjunt_del_punt;
    private final ConjuntDePunts punts;
    private final Punt[] baricentres;

    // Els id's dels punts han de ser contigus de 0 a num_punts-1.
    // k <= num_punts
    // s'ha de decidir si volem fer aleatori o si assumim que la entrada es prou aleatoria.
    public KMeans(ConjuntDePunts punts, int k) {
        this.k = k;
        this.punts = punts;
        n_punts = punts.getNumPunts();
        particions = new ConjuntDePunts[k];
        conjunt_del_punt = new int[n_punts];
        baricentres = new Punt[k];
        inicialitzarEnOrdre();
    }

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
            particions[i] = new ConjuntDePunts();
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
            particions[i] = new ConjuntDePunts();
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
            baricentres[i] = particions[i].getBaricentre();
        }
    }
}
