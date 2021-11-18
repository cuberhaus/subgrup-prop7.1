package domini.classes;

import java.util.Map;
import java.util.TreeMap;

/**
 * Representa un conjunt d'elements identificats per un <code>Id</code>.
 * @author maria.prat
 */
public abstract class ConjuntIdentificat<T extends ElementIdentificat> {
    protected TreeMap<Id, T> elements;

    public boolean conte(Id id) {
        return elements.containsKey(id);
    }

    public T obtenir(Id id) {
        if (id == null || !elements.containsKey(id)) {
            throw new IllegalArgumentException("El conjunt no conté cap element amb aquest Id");
        }
        return elements.get(id);
    }

    public T afegir(T element) {
        return elements.put(element.obtenirId(), element);
    }

    public T esborrar(Id id) {
        return elements.remove(id);
    }

    public boolean esborrar(T element) {
        return elements.remove(element.obtenirId(), element);
    }

    public TreeMap<Id, T> obtenirTotsElsElements() { return elements; }

    public int mida() { return elements.size(); }
}
