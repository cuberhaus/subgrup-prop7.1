package domini.classes.metode_recomanador;

import domini.classes.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Recomanador basat en <code>Collaborative filtering</code> mitjançant K-means i Slope1.
 * @author edgar.moreno
 */
public class MetodeRecomanadorCollaborative extends MetodeRecomanador {

    /** Nombre de clusters que s'utilitzarà com K al K-means **/
    private int num_clusters;

    /** Particions obtingudes de K-Means*/
    private ArrayList<ArrayList<Integer>> particions;

    /** Particio de cada usuari **/
    private Integer[] particio_de_usuari;

    /** Id's per conectar particions i items */
    private final ArrayList<Id> ids;

    /**
     * Crea un <code>RecomanadorCollaborative</code> donant un conjunt de dades per defecte.
     * Per defecte el nombre de clusters per K-Means es 5.
     * @param usuaris pot ser buit
     * @param items pot ser buit
     * @param valoracions_publiques pot ser buit
     */
    public MetodeRecomanadorCollaborative(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracions_publiques) {
        super(usuaris, items, valoracions_publiques);
        num_clusters = Math.min(5, usuaris.mida());
        ids = new ArrayList<>();
        calculaParticions();
    }
    public MetodeRecomanadorCollaborative(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracions_publiques, int num_clusters) {
        super(usuaris, items, valoracions_publiques);
        this.num_clusters = num_clusters;
        ids = new ArrayList<>();
        calculaParticions();
    }

    /**
     * Precalcula els clusters per poder servir queries més ràpidament.
     */
    private void calculaParticions() {
        ConjuntPunts punts_usuaris = new ConjuntPunts();
        for (Usuari it_usu : usuaris.obtenirTotsElsElements().values()) {
            punts_usuaris.add(it_usu.transformaAPunt(items));
            ids.add(it_usu.obtenirId());
        }

        // Amb el algorisme KMeans obtenim el cluster del usuari a qui hem de recomanar.
        KMeans kMeans = new KMeans(punts_usuaris, num_clusters);
        particions = kMeans.getParticions();
        particio_de_usuari = new Integer[usuaris.mida()];
        for (int i = 0; i < num_clusters; ++i) {
            for (int x : particions.get(i)) {
                particio_de_usuari[x] = i;
            }
        }
    }

    /**
     * Genera recomanacions per l'usuari donat.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param valoracions_usuari Valoracions en les que es basaran les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades. Si l'usuari no estava als usuaris inicials
     *          retorna null.
     */
    @Override
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems conjuntRecomanable, ConjuntValoracions valoracions_usuari, int numRecomanacions) {
        int posicio_usuari = -1;

        for(int i = 0; i < ids.size(); ++i) {
            if (ids.get(i).equals(usuari.obtenirId()))
                posicio_usuari = i;
        }
        if (posicio_usuari == -1)
            return null;

        ArrayList<Integer> particio_usuari = particions.get(particio_de_usuari[posicio_usuari]);

        int posicio_a_la_particio = 0;
        for (int i = 0; i < particio_usuari.size(); ++i) {
            if (particio_usuari.get(i) == posicio_usuari)
                posicio_a_la_particio = i;
        }

        ConjuntItems items_usables = new ConjuntItems(conjuntRecomanable.obteTipusItem());
        for (Valoracio val : valoracions_usuari.obteTotesValoracions().values()) {
            items_usables.afegir(val.getItem());
        }
        for (Item item : conjuntRecomanable.obtenirTotsElsElements().values()) {
            items_usables.afegir(item);
        }

        int num_usuaris = particio_usuari.size();
        int num_items = items_usables.mida();

        Double[][] valoracions = new Double[num_usuaris][num_items];
        Item[] items_slope1 = items_usables.obtenirTotsElsElements().values().toArray(new Item[0]);
        ArrayList<Integer> posicions_recomanables = new ArrayList<>();

        for (int i = 0; i < num_items; ++i) {
            if (conjuntRecomanable.conte(items_slope1[i].obtenirId())) {
                posicions_recomanables.add(i);
            }
        }

        for (int i = 0; i < num_usuaris; ++i) {
            Usuari usuari_it = usuaris.obtenir(ids.get(particio_usuari.get(i)));
            for (int j = 0; j < num_items; ++j) {
                if (valoracionsPubliques.conte(usuari_it, items_slope1[j]))
                    valoracions[i][j] = valoracionsPubliques.obte(usuari_it, items_slope1[j]).getValor();
                else
                    valoracions[i][j] = null;
            }
        }

        SlopeOne slopeOne = new SlopeOne(valoracions);

        PriorityQueue<Pair<Double,Item>> pq = new PriorityQueue<>();
        for (Integer posicio_recomanable : posicions_recomanables) {
            pq.add(new Pair<>(slopeOne.getPrediccio(posicio_a_la_particio, posicio_recomanable), items_slope1[posicio_recomanable]));
        }

        while (pq.size() > numRecomanacions) {
            pq.remove();
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
