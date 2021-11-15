package Domini;

import java.util.*;

public class RecomanadorContentBased extends MetodeRecomanador {

    // Si una valoracio es menor a aquest valor esq no ha agradat i no te sentit utilitzar-la a l'algorisme.
    double minimaValoracioConsiderada = 0;

    void setMinimaValoracioConsiderada(double minimaValoracioConsiderada) {
        this.minimaValoracioConsiderada = minimaValoracioConsiderada;
    }
    public RecomanadorContentBased(Usuari[] usuaris, Item[] items, Valoracio[] valoracions_publiques, Valoracio[] valoracions_privades) {
        super(usuaris, items, valoracions_publiques, valoracions_privades);
    }

    @Override
    public ArrayList<Item> getRecomanacions(Usuari usuari, Valoracio[] valoracions_usuari, int numRecomanacions) {
        return null;
    }

    @Override
    public ArrayList<Item> getRecomanacions(Usuari usuari, int numRecomanacions) {
        return null;
    }

    @Override
    public ArrayList<Pair<Item, Double>> getRecomanacions(Usuari usuari, ArrayList<Item> conjuntRecomanable, Valoracio[] valoracions_usuari, int numRecomanacions) {
        TreeMap<String, Double> valor_item = new TreeMap<>();
        TreeMap<String, Item> id_a_item = new TreeMap<>();
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
        for (Map.Entry<String,Double> entry: valor_item.entrySet()) {
            pq.add(new Pair<Double, Item>(entry.getValue(), id_a_item.get(entry.getKey())));
        }
        while (pq.size() > numRecomanacions) {
            pq.remove();
        }

        ArrayList<Pair<Item, Double>> res = new ArrayList<Pair<Item, Double>>(pq.toArray(new Pair<Item,Double>[0]));
        while(!pq.isEmpty()) {
            Pair<Double, Item> pair = pq.remove();
            res.add(new Pair<>(pair.y, pair.x));
        }
        return res;
    }

    @Override
    public String avaluaQualitatRecomanacio(Usuari usuari, ArrayList<Item> recomanacio) {
        return null;
    }

    @Override
    public String avaluaQualitatRecomanacio(ArrayList<Pair<Item, Double>> recomanacio, Valoracio[] valoracionsGuia) {
        ArrayList<Pair<String, Double>> id_puntuacio_recomanacio = new ArrayList<>();
        for (var pair : recomanacio) {
            id_puntuacio_recomanacio.add(new Pair<>(pair.x.getId(), pair.y));
        }
        id_puntuacio_recomanacio.sort(Comparator.comparing(o -> o.y));
        Collections.reverse(id_puntuacio_recomanacio);
        ArrayList<>
        String str = new String();
        str += "DGC :" + DGC.calculaDGC(recomanacio, va)
    }
}
