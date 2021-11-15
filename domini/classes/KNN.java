package domini.classes;

import java.util.ArrayList;
import java.util.PriorityQueue;
// TODO: javadoc

public class KNN {
    private final Item[] items;
    public KNN(Item[] items) {
        this.items = items;
    }

    public ArrayList<Item> getVeins(Item item, int k) {
        PriorityQueue<Pair<Double, Item>> pq = new PriorityQueue<>();
        for (Item iter : items) {
            double distance = item.obtenirDistancia(iter);
            if (pq.size() < k) {
                pq.add(new Pair<>(-distance, iter));
            }
        }
        ArrayList<Item> result_items = new ArrayList<>();
        while (!pq.isEmpty()) {
            Pair<Double, Item> top = pq.poll();
            result_items.add(top.y);
        }
        return result_items;
    }
}
