package Domini;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class KNN {
    private final Item[] items;
    public KNN(Item[] items) {
        this.items = items;
    }

    public ArrayList<Item> getVeins(Item item, int k) {
        PriorityQueue<Pair<Double, Item>> pq = new PriorityQueue<>();
        for (Item iter : items) {
            // TODO: distance between items?
            double distance = 0;
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
