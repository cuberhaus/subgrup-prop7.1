package domini.classes;

import java.util.TreeMap;

/**
 * Representa un conjunt d'elements identificats per un <code>Id</code>.
 * @author maria.prat
 */
public abstract class ConjuntIdentificat<T extends ElementIdentificat> {
    /**
     * TreeMap que conté els elements del conjunt identificat.
     */
    protected TreeMap<Id, T> elements;

    /**
     * @return Nombre d'elements del conjunt identificat.
     */
    public int mida() { return elements.size(); }

    /**
     * @param id <code>Id</code> que conté l'identificador de l'element que es vol trobar.
     * @return Cert si el conjunt identificat conté un element amb l'identificador donat. Altrament, retorna fals.
     */
    public boolean conte(Id id) {
        return elements.containsKey(id);
    }

    /**
     * @param id id <code>Id</code> que conté l'identificador de l'element que es vol trobar.
     * @return Element del conjunt identificat amb l'identificador donat.
     * @throws IllegalArgumentException Si l'identificador donat és nul o el conjunt no conté cap element amb
     * l'identificador donat.
     */
    public T obtenir(Id id) throws IllegalArgumentException {
        if (id == null || !elements.containsKey(id)) {
            throw new IllegalArgumentException("El conjunt no conté cap element amb aquest Id");
        }
        return elements.get(id);
    }

    /**
     * @return Tots els elements del conjunt identificat en un <code>TreeMap(Id, T)</code> que relaciona cada
     * identificador amb el seu element.
     */
    public TreeMap<Id, T> obtenirTotsElsElements() { return elements; }

    /**
     * Afegeix l'element donat al conjunt identificat.
     * @param element Element del mateix tipus que el conjunt identificat.
     */
    public void afegir(T element) {
        elements.put(element.obtenirId(), element);
    }

    /**
     * Esborra l'element amb l'identificador donat del conjunt identificat.
     * @param id Identificador d'un element.
     * @return L'element que anteriorment estava identificat amb l'identificador donat. Si el conjunt no contenia cap
     * element amb aquest Id, retorna <code>null</code>.
     */
    public T esborrar(Id id) {
        return elements.remove(id);
    }

    /**
     * Esborra l'element donat del conjunt identificat.
     * @param element Element.
     * @return Retorna cert si s'ha esborrat l'element del conjunt. Altrament retorna fals.
     */
    public boolean esborrar(T element) {
        return elements.remove(element.obtenirId(), element);
    }

}
