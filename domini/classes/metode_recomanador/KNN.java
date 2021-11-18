package domini.classes.metode_recomanador;

import domini.classes.Item;
import domini.classes.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Processa un conjunt de <code>Item</code> i permet obtenir els més propers a un <code>Item</code> donat.
 * @author edgar.moreno
 */
public class KNN {
    // TODO: conjunt items
    /** Items considerats a l'algorisme*/
    private final Item[] items;

    /**
     * @param items Conjunt de <code>Item</code> que seran considerats.
     */
    public KNN(Item[] items) {
        this.items = items;
    }


    /**
     * @param item <code>Item</code> del que es vol buscar els propers.
     * @param k nombre de veins a obtenir.
     * @return els k items més propers al donat.
     */
    public ArrayList<Item> obtenirVeins(Item item, int k) {
        PriorityQueue<Pair<Double, Item>> pq = new PriorityQueue<>();
        for (Item iter : items) {
            double distance = item.obtenirDistancia(iter);
            if (pq.size() < k) {
                pq.add(new Pair<>(-distance, iter));
            }
            else if(!pq.isEmpty() && -pq.poll().x > distance) {
                pq.remove();
                pq.add(new Pair<>(-distance,iter));
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
