package domini.classes.metode_recomanador;

import domini.classes.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Recomanador basat en <code>Collaborative filtering</code> mitjançant K-means i Slope1.
 * @author edgar.moreno
 */
public class MetodeRecomanadorCollaborative extends MetodeRecomanador {
    /** Nombre de clusters que s'utilitzarà com K al K-means**/
    private int num_clusters;

    /**
     * Crea un <code>RecomanadorCollaborative</code> donant un conjunt de dades per defecte.
     * @param usuaris pot ser buit
     * @param items pot ser buit
     * @param valoracions_publiques pot ser buit
     */
    public MetodeRecomanadorCollaborative(Usuari[] usuaris, Item[] items, Valoracio[] valoracions_publiques) {
        super(usuaris, items, valoracions_publiques);
        // TODO: numero arbitrari de clusters.
        num_clusters = Math.min((int)Math.sqrt(usuaris.length)+1, usuaris.length);
    }

    /**
     * @param num nou valor per <code>num_clusters</code> del K-means
     */
    public void setNumeroClusters(int num) { num_clusters = num; }

    /**
     * Genera recomanacions per l'usuari donat.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param valoracions_usuari Valoracions en les que es basaran les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    @Override
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ArrayList<Item> conjuntRecomanable, Valoracio[] valoracions_usuari, int numRecomanacions) {
        ConjuntPunts punts_usuaris = new ConjuntPunts();
        punts_usuaris.add(usuari.obteComPunt(conjuntRecomanable));
        for (Usuari it_usu : usuaris) {
            if (usuari == it_usu) continue;
            punts_usuaris.add(it_usu.obteComPunt(conjuntRecomanable));
        }

        KMeans kMeans = new KMeans(punts_usuaris, num_clusters);
        ArrayList<ArrayList<Integer>> particions = kMeans.getParticions();
        ArrayList<Integer> particio_usuari = new ArrayList<>();

        for (ArrayList<Integer> part : particions) {
            if (part.contains(0)) {
                particio_usuari = part;
            }
        }

        int num_usuaris = particio_usuari.size();
        int num_items = conjuntRecomanable.size();

        Double[][] valoracions = new Double[num_usuaris][num_items];
        for (int i = 0; i < num_usuaris; ++i) {
            for (int j = 0; j < num_items; ++j) {
                Valoracio valoracio = usuari.obtenirValoracio(conjuntRecomanable.get(j));
                if (valoracio == null) {
                    valoracions[i][j] = null;
                }
                else {
                    valoracions[i][j] = valoracio.getValor();
                }
            }
        }

        SlopeOne slopeOne = new SlopeOne(valoracions);

        PriorityQueue<Pair<Double,Item>> pq = new PriorityQueue<>();
        for (int i = 0; i < num_items; ++i) {
            pq.add(new Pair<>(slopeOne.getPrediccio(0, i), conjuntRecomanable.get(i)));
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
