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
    public MetodeRecomanadorCollaborative(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracions_publiques) {
        super(usuaris, items, valoracions_publiques);
        // TODO: numero arbitrari de clusters.
        //num_clusters = Math.min((int)Math.sqrt(usuaris.mida())+1, usuaris.mida());
        num_clusters = Math.min(3, usuaris.mida());

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
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems conjuntRecomanable, ConjuntValoracions valoracions_usuari, int numRecomanacions) {
        ConjuntPunts punts_usuaris = new ConjuntPunts();
        ArrayList<Id> ids = new ArrayList<>();
        punts_usuaris.add(usuari.transformaAPunt(items));
        ids.add(usuari.obtenirId());
        for (Usuari it_usu : usuaris.obtenirTotsElsElements().values()) {
            if (usuari == it_usu) continue;
            punts_usuaris.add(it_usu.transformaAPunt(items));
            ids.add(it_usu.obtenirId());
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
        int num_items = items.mida();
        Double[][] valoracions = new Double[num_usuaris][num_items];
        Item[] items = this.items.obtenirTotsElsElements().values().toArray(new Item[0]);
        ArrayList<Integer> posicions_recomanables = new ArrayList<>();
        for (int i = 0; i < num_items; ++i) {
            if (conjuntRecomanable.conte(items[i].obtenirId())) {
                posicions_recomanables.add(i);
            }
        }
        for (int i = 0; i < num_usuaris; ++i) {
            for (int j = 0; j < num_items; ++j) {
                Valoracio valoracio = usuaris.obtenir(ids.get(particio_usuari.get(i))).obtenirValoracio(items[j]);
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
        for (Integer posicions_recomanable : posicions_recomanables) {
            pq.add(new Pair<>(slopeOne.getPrediccio(0, posicions_recomanable), items[posicions_recomanable]));
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
