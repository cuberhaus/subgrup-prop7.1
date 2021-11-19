package domini.classes.recomanador.metode_recomanador;

import domini.classes.*;

import java.util.*;

/**
 * Recomanador basat en <code>Content-based filtering</code> mitjançant k-NN.
 * @author edgar.moreno
 */
public class MetodeRecomanadorContentBased extends MetodeRecomanador {

    /** Minima valoració necessària perquè sigui considerada com rellevant per l'algorisme **/
    double minimaValoracioConsiderada = 0;

    /**
     * Crea un <code>RecomanadorContentBased</code> donant un conjunt de dades per defecte.
     * @param usuaris pot ser buit
     * @param items pot ser buit
     * @param valoracions_publiques pot ser buit
     */
    public MetodeRecomanadorContentBased(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracions_publiques) {
        super(usuaris, items, valoracions_publiques);
    }

    /**
     * @param minimaValoracioConsiderada nou valor de <code>minimaValoracioConsiderada</code>
     */
    public void setMinimaValoracioConsiderada(double minimaValoracioConsiderada) {
        this.minimaValoracioConsiderada = minimaValoracioConsiderada;
    }

    /**
     * Genera recomanacions per l'usuari donat.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param valoracions_usuari Valoracions en les que es basaran les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    @Override
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems conjuntRecomanable, ConjuntValoracions valoracions_usuari, int numRecomanacions) {
        TreeMap<Id, Double> valor_item = new TreeMap<>();
        KNN knn = new KNN(conjuntRecomanable.obtenirTotsElsElements().values().toArray(new Item[0]));
        for (Valoracio val : valoracions_usuari.obteTotesValoracions().values()) {
            if (val.obtenirValor() > minimaValoracioConsiderada) {
                // TODO: agafo tants veins com recomanacions volem, no te perque ser la millor eleccio
                ArrayList<Item> veins = knn.obtenirVeins(val.obtenirItem(), numRecomanacions);
                for (Item it : veins) {
                    if (valor_item.containsKey(it.obtenirId())) {
                        valor_item.put(it.obtenirId(), valor_item.get(it.obtenirId()) + val.obtenirValor());
                    } else {
                        valor_item.put(it.obtenirId(), val.obtenirValor());
                    }
                }
            }
        }

        PriorityQueue<Pair<Double,Item>> pq = new PriorityQueue<>();
        for (Map.Entry<Id,Double> entry: valor_item.entrySet()) {
            pq.add(new Pair<>(entry.getValue(), conjuntRecomanable.obtenir(entry.getKey())));
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
