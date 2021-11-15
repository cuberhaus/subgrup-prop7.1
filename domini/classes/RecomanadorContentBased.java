package domini.classes;

import java.util.*;
// TODO: javadoc

public class RecomanadorContentBased extends MetodeRecomanador {

    // Si una valoracio es menor a aquest valor esq no ha agradat i no te sentit utilitzar-la a l'algorisme.
    double minimaValoracioConsiderada = 0;

    void setMinimaValoracioConsiderada(double minimaValoracioConsiderada) {
        this.minimaValoracioConsiderada = minimaValoracioConsiderada;
    }
    public RecomanadorContentBased(Usuari[] usuaris, Item[] items, Valoracio[] valoracions_publiques) {
        super(usuaris, items, valoracions_publiques);
    }

    @Override
    public ConjuntDeRecomanacions obteRecomanacions(Usuari usuari, ArrayList<Item> conjuntRecomanable, Valoracio[] valoracions_usuari, int numRecomanacions) {
        TreeMap<Id, Double> valor_item = new TreeMap<>();
        TreeMap<Id, Item> id_a_item = new TreeMap<>();
        KNN knn = new KNN(conjuntRecomanable.toArray(new Item[0]));
        for (Valoracio val : valoracions_usuari) {
            if (val.getValor() > minimaValoracioConsiderada) {
                // TODO: agafo tants veins com recomanacions volem, no te perque ser la millor eleccio
                ArrayList<Item> veins = knn.getVeins(val.getItem(), numRecomanacions);
                for (Item it : veins) {
                    if (valor_item.containsKey(it.id)) {
                        valor_item.put(it.id ,valor_item.get(it.id) + val.getValor());
                    } else {
                        valor_item.put(it.id, val.getValor());
                        id_a_item.put(it.id, it);
                    }
                }
            }
        }

        PriorityQueue<Pair<Double,Item>> pq = new PriorityQueue<>();
        for (Map.Entry<Id,Double> entry: valor_item.entrySet()) {
            pq.add(new Pair<>(entry.getValue(), id_a_item.get(entry.getKey())));
        }
        while (pq.size() > numRecomanacions) {
            pq.remove();
        }

        ConjuntDeRecomanacions res = new ConjuntDeRecomanacions();
        while(!pq.isEmpty()) {
            Pair<Double, Item> pair = pq.remove();
            res.afegirRecomanacio(new Recomanacio(pair.y.id, pair.x));
        }
        res.ordena();
        return res;
    }
}
