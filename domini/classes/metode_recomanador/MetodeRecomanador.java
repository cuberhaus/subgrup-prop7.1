package domini.classes.metode_recomanador;

import domini.classes.Item;
import domini.classes.Usuari;
import domini.classes.Valoracio;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe base pels mètodes recomanadors.
 * Guarda usuaris, items i valoracions. Genera valoracions per usuaris donats.
 * @author edgar.moreno
 */
public abstract class MetodeRecomanador {
    /** Conjunt d'usuaris per defecte **/
    protected Usuari[] usuaris;
    /** Conjunt d'items per defecte **/
    protected Item[] items;
    /** Conjunt de valoracions per defecte **/
    protected Valoracio[] valoracionsPubliques;

    /**
     * Crea un <code>MetodeRecomanador</code> donant un conjunt de dades per defecte.
     * @param usuaris pot ser buit
     * @param items pot ser buit
     * @param valoracions_publiques pot ser buit
     */
    public MetodeRecomanador(Usuari[] usuaris, Item[] items, Valoracio[] valoracions_publiques) {
        this.usuaris = usuaris;
        this.items = items;
        this.valoracionsPubliques = valoracions_publiques;
    }

    /**
     * Genera recomanacions per l'usuari donat.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param valoracions_usuari Valoracions en les que es basaran les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, Valoracio[] valoracions_usuari, int numRecomanacions) {
        return obteRecomanacions(usuari, new ArrayList<>(List.of(items)), valoracions_usuari, numRecomanacions);
    }

    /**
     * Genera recomanacions per l'usuari donat. Usa les valoracions generades per l'usuari de les valoracions per defecte.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, int numRecomanacions) {
        ArrayList<Valoracio> valoracions_usuari = new ArrayList<>();
        for (var x : valoracionsPubliques) {
            if (x.getUsuari().equals(usuari)) {
                valoracions_usuari.add(x);
            }
        }
        return obteRecomanacions(usuari, valoracions_usuari.toArray(new Valoracio[0]), numRecomanacions);
    }

    /**
     * Genera recomanacions per l'usuari donat. Usa les valoracions generades per l'usuari de les valoracions per defecte.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param conjuntRecomanable conjunt de <code>Item</code> d'on s'extrauran les recomanacions.
     * @param valoracions_usuari valoracions que s'utilitzaran per les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    public abstract ConjuntRecomanacions obteRecomanacions(Usuari usuari, ArrayList<Item> conjuntRecomanable, Valoracio[] valoracions_usuari, int numRecomanacions);
}
