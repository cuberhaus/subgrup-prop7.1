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
    private final int numPunts;
    private final ConjuntPunts[] particions;
    private final int[] conjuntDelPunt;
    private final ConjuntPunts punts;

    /**
     * Inicialitza l'algorisme amb el conjunt de punts desitjat.
     * @param punts Conjunt de punts per executar l'algorisme. Els identificadors han de ser contigus començant en 0.
     * @param k Nombre de particions a realitzar. Han de ser menors al nombre de punts.
     */
    public KMeans(ConjuntPunts punts, int k) {
        this.k = k;
        this.punts = punts;
        numPunts = punts.obtenirNumPunts();
        particions = new ConjuntPunts[k];
        conjuntDelPunt = new int[numPunts];
        inicialitzarEnOrdre();
    }

    /**
     * @return Les particions obtingudes amb l'algorisme.
     */
    public ArrayList<ArrayList<Integer>> getParticions() {
        processa();
        ArrayList<ArrayList<Integer>> idParts = new ArrayList<>(k);
        for (int i = 0; i < k; ++i) {
            idParts.add(new ArrayList<>(particions[i].keySet()));
        }
        return idParts;
    }

    private void inicialitzarAleatori() {
        Arrays.fill(conjuntDelPunt, -1);
        Random r = new Random();
        for (int i = 0; i < k; ++i)
        {
            particions[i] = new ConjuntPunts();
            int center;
            do {
                center = r.nextInt(numPunts);
            } while (conjuntDelPunt[center] != -1);
            conjuntDelPunt[center] = i;
            particions[i].put(center, punts.get(center));
        }
        Punt[] baricentres = generaBaricentres();
        for (int i = 0; i < numPunts; ++i) {
            if (conjuntDelPunt[i] == -1) {
                int nouConjunt = buscaBaricentreProper(i, baricentres);
                conjuntDelPunt[i] = nouConjunt;
                particions[nouConjunt].put(i, punts.get(i));
            }
        }
    }

    private void inicialitzarEnOrdre() {
        Arrays.fill(conjuntDelPunt, -1);
        for (int i = 0; i < k; ++i)
        {
            particions[i] = new ConjuntPunts();
            conjuntDelPunt[i] = i;
            particions[i].put(i, punts.get(i));
        }
        Punt[] baricentres = generaBaricentres();
        for (int i = 0; i < numPunts; ++i) {
            if (conjuntDelPunt[i] == -1) {
                int nouConjunt = buscaBaricentreProper(i, baricentres);
                conjuntDelPunt[i] = nouConjunt;
                particions[nouConjunt].put(i, punts.get(i));
            }
        }
    }

    private void canviaDeConjunt(int punt, int nouConjunt) {
        particions[conjuntDelPunt[punt]].remove(punt);
        conjuntDelPunt[punt] = nouConjunt;
        particions[nouConjunt].put(punt, punts.get(punt));
    }
    private void processa() {
        while(iteracio());
    }

    private boolean iteracio() {
        boolean canvi = false;
        Punt[] baricentres = generaBaricentres();
        for (int i = 0; i < numPunts; ++i) {
            int nouConjunt = buscaBaricentreProper(i, baricentres);
            if (nouConjunt != conjuntDelPunt[i]) {
                canvi = true;
                canviaDeConjunt(i, nouConjunt);
            }
        }
        return canvi;
    }

    private int buscaBaricentreProper(int id, Punt[] baricentres) {
        Punt punt = punts.get(id);
        double distancia = punt.distancia(baricentres[0]);
        int conjunt = 0;
        for (int i = 1; i < k; ++i) {
            if (baricentres[i] == null) continue;
            double novaDist = punt.distancia(baricentres[i]);
            if (novaDist < distancia){
                conjunt = i;
                distancia = novaDist;
            }
        }
        return conjunt;
    }

    private Punt[] generaBaricentres() {
        Punt[] baricentres = new Punt[k];
        for (int i = 0; i < k; ++i) {
            if (particions[i].obtenirNumPunts() != 0)
                baricentres[i] = particions[i].obtenirBaricentre();
        }
        return baricentres;
    }
}
