package domini.classes.recomanador.metode_recomanador;

import domini.classes.*;
import domini.classes.recomanador.ConjuntRecomanacions;
import domini.classes.recomanador.Recomanacio;
import excepcions.NoExisteixElementException;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Recomanador basat en <code>Collaborative filtering</code> mitjançant K-means i Slope1.
 * @author edgar.moreno
 */
public class MetodeRecomanadorCollaborative extends MetodeRecomanador {

    /** Nombre de clusters que s'utilitzarà com K al K-means **/
    private final int numClusters;

    /** Particions obtingudes de K-Means*/
    private ArrayList<ArrayList<Integer>> particions;

    /** Partició de cada usuari **/
    private Integer[] particioUsuari;

    /** Id's per conectar particions i items */
    private final ArrayList<Id> ids;

    /**
     * Crea un <code>RecomanadorCollaborative</code> donant un conjunt de dades per defecte.
     * Per defecte el nombre de clusters per K-Means es 5.
     * @param usuaris pot ser buit
     * @param items pot ser buit
     * @param valoracionsPubliques pot ser buit
     */
    public MetodeRecomanadorCollaborative(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques) {
        super(usuaris, items, valoracionsPubliques);
        numClusters = Math.min(5, usuaris.mida());
        ids = new ArrayList<>();
        calculaParticions();
    }
    public MetodeRecomanadorCollaborative(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques, int numClusters) {
        super(usuaris, items, valoracionsPubliques);
        this.numClusters = numClusters;
        ids = new ArrayList<>();
        calculaParticions();
    }

    /**
     * Precalcula els clusters per poder servir queries més ràpidament.
     */
    private void calculaParticions() {
        ConjuntPunts puntsUsuaris = new ConjuntPunts();
        for (Usuari iterUsu : usuaris.obtenirTotsElsElements().values()) {
            puntsUsuaris.add(iterUsu.transformaAPunt(items));
            ids.add(iterUsu.obtenirId());
        }

        // Amb l'algorisme KMeans obtenim el cluster del usuari a qui hem de recomanar.
        KMeans kMeans = new KMeans(puntsUsuaris, numClusters);
        particions = kMeans.getParticions();
        particioUsuari = new Integer[usuaris.mida()];
        for (int i = 0; i < numClusters; ++i) {
            for (int x : particions.get(i)) {
                particioUsuari[x] = i;
            }
        }
    }

    private int buscaPosicioUsuari(Usuari usuari) {
        int posicioUsuari = -1;

        for(int i = 0; i < ids.size(); ++i) {
            if (ids.get(i).equals(usuari.obtenirId()))
                posicioUsuari = i;
        }
        return posicioUsuari;
    }
    private ConjuntItems obteConjuntItemsUsables(ConjuntItems conjuntRecomanable, ConjuntValoracions valoracionsUsuari) {
        ConjuntItems itemsUsables = new ConjuntItems(conjuntRecomanable.obteTipusItem());
        for (Valoracio val : valoracionsUsuari.obtenitTotesLesValoracions().values()) {
            itemsUsables.afegir(val.obtenirItem());
        }
        for (Item item : conjuntRecomanable.obtenirTotsElsElements().values()) {
            itemsUsables.afegir(item);
        }
        return itemsUsables;
    }

    private Double[][] generaTaulaValoracions(ArrayList<Integer> particioUsuari, Item[] items) throws NoExisteixElementException {
        int numUsuaris = particioUsuari.size();
        int numItems = items.length;
        Double[][] valoracions = new Double[numUsuaris][numItems];
        for (int i = 0; i < numUsuaris; ++i) {
            Usuari usuariIt = usuaris.obtenir(ids.get(particioUsuari.get(i)));
            for (int j = 0; j < numItems; ++j) {
                if (valoracionsPubliques.conte(usuariIt, items[j]))
                    valoracions[i][j] = valoracionsPubliques.obte(usuariIt, items[j]).obtenirValor();
                else
                    valoracions[i][j] = null;
            }
        }
        return valoracions;
    }
    /**
     * Genera recomanacions per l'usuari donat.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param valoracionsUsuari Valoracions en les que es basaran les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades. Si l'usuari no estava als usuaris inicials
     *          retorna null.
     */
    @Override
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems conjuntRecomanable, ConjuntValoracions valoracionsUsuari, int numRecomanacions) throws NoExisteixElementException {

        int posicioUsuari = buscaPosicioUsuari(usuari);

        if (posicioUsuari == -1)
            return null;

        ArrayList<Integer> particioUsuari = particions.get(this.particioUsuari[posicioUsuari]);

        int posicioALaParticio = 0;
        for (int i = 0; i < particioUsuari.size(); ++i) {
            if (particioUsuari.get(i) == posicioUsuari)
                posicioALaParticio = i;
        }

        ConjuntItems itemsUsables = obteConjuntItemsUsables(conjuntRecomanable, valoracionsUsuari);

        int numItems = itemsUsables.mida();

        Item[] itemsSlope1 = itemsUsables.obtenirTotsElsElements().values().toArray(new Item[0]);
        ArrayList<Integer> posicionsRecomanables = new ArrayList<>();

        for (int i = 0; i < numItems; ++i) {
            if (conjuntRecomanable.conte(itemsSlope1[i].obtenirId())) {
                posicionsRecomanables.add(i);
            }
        }

        Double[][] valoracions = generaTaulaValoracions(particioUsuari, itemsSlope1);

        SlopeOne slopeOne = new SlopeOne(valoracions);

        PriorityQueue<Pair<Double,Item>> pq = new PriorityQueue<>();
        for (Integer posicioRecomanable : posicionsRecomanables) {
            double valorPrediccio = slopeOne.getPrediccio(posicioALaParticio, posicioRecomanable);
            if (pq.size() < numRecomanacions) {
                pq.add(new Pair<>(valorPrediccio, itemsSlope1[posicioRecomanable]));
            }
            else if(!pq.isEmpty() && pq.peek().x < valorPrediccio) {
                pq.remove();
                pq.add(new Pair<>(valorPrediccio, itemsSlope1[posicioRecomanable]));
            }
        }

        ConjuntRecomanacions res = new ConjuntRecomanacions();
        while(!pq.isEmpty()) {
            Pair<Double, Item> pair = pq.remove();
            res.afegirRecomanacio(new Recomanacio(pair.y.obtenirId(), pair.x));
        }
        res.ordena();
        return res;
    }
}
