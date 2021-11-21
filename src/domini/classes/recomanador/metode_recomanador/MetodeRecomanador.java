package domini.classes.recomanador.metode_recomanador;

import domini.classes.*;
import domini.classes.recomanador.ConjuntRecomanacions;

/**
 * Classe base pels mètodes recomanadors.
 * Guarda usuaris, items i valoracions. Genera recomanacions per usuaris donats.
 * @author edgar.moreno
 */
public abstract class MetodeRecomanador {
    /** Conjunt d'usuaris per defecte **/
    protected final ConjuntUsuaris usuaris;
    /** Conjunt d'items per defecte **/
    protected final ConjuntItems items;
    /** Conjunt de valoracions per defecte **/
    protected final ConjuntValoracions valoracionsPubliques;

    /**
     * Crea un <code>MetodeRecomanador</code> donant un conjunt de dades per defecte.
     * @param usuaris pot ser buit
     * @param items pot ser buit
     * @param valoracionsPubliques pot ser buit
     */
    public MetodeRecomanador(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques) {
        this.usuaris = usuaris;
        this.items = items;
        this.valoracionsPubliques = valoracionsPubliques;
    }

    /**
     * Genera recomanacions per l'usuari donat.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param valoracionsUsuari Valoracions en les que es basaran les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntValoracions valoracionsUsuari, int numRecomanacions) {
        return obteRecomanacions(usuari, items, valoracionsUsuari, numRecomanacions);
    }

    /**
     * Genera recomanacions per l'usuari donat.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param itemsRecomanables Items que es poden recomanar al usuari.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades, extreura les valoracions de les valoracions publiques de l'usuari.
     */
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems itemsRecomanables, int numRecomanacions) {
        ConjuntValoracions valoracions = new ConjuntValoracions();
        for (Valoracio val : valoracionsPubliques.obteTotesValoracions().values()) {
            if (val.obtenirUsuari().equals(usuari))
                valoracions.afegir(val);
        }
        return obteRecomanacions(usuari, itemsRecomanables, valoracions, numRecomanacions);
    }

    /**
     * Genera recomanacions per l'usuari donat. Usa les valoracions generades per l'usuari de les valoracions per defecte.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, int numRecomanacions) {
        ConjuntValoracions valoracions_usuari = new ConjuntValoracions();
        for (var x : valoracionsPubliques.obteTotesValoracions().entrySet()) {
            if (x.getKey().x.equals(usuari)) {
                valoracions_usuari.afegir(x.getValue());
            }
        }
        return obteRecomanacions(usuari, valoracions_usuari, numRecomanacions);
    }

    /**
     * Genera recomanacions per l'usuari donat. Usa les valoracions generades per l'usuari de les valoracions per defecte.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param conjuntRecomanable conjunt de <code>Item</code> d'on s'extrauran les recomanacions.
     * @param valoracionsUsuari valoracions que s'utilitzaran per les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    public abstract ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems conjuntRecomanable,
                                                           ConjuntValoracions valoracionsUsuari, int numRecomanacions);
}
