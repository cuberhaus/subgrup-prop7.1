package domini.classes;

import java.util.Map;

/**
 * Representa un conjunt d'elements identificats per un <code>Id</code>.
 * @author maria.prat
 */
public abstract class ConjuntIdentificat<T extends ElementIdentificat> {
    protected Map<Id, T> elements;

    public boolean conte(Id id) {
        return elements.containsKey(id);
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
}
