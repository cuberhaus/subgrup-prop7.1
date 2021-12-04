package domini.classes.recomanador.filtre;

import domini.classes.ConjuntItems;

import java.util.ArrayList;

/**
 * Representa un filtre d'un conjunt d'Items.
 * @author maria.prat
 */
public abstract class Filtre {
    /**
     * Noms dels atributs del filtre.
     */
    protected final ArrayList<String> nomAtributs;

    /**
     * Constructor d'un Filtre amb el nom dels atributs.
     * @param nomAtributs Noms dels atributs.
     */
    public Filtre(ArrayList<String> nomAtributs) {
        this.nomAtributs = nomAtributs;
    }

    /**
     * Filtra el conjunt d'ítems.
     * @param conjuntItems Conjunt d'Items per filtrar.
     */
    public abstract void filtrar(ConjuntItems conjuntItems);
}
