package domini.classes.recomanador.metode_recomanador;

import domini.classes.*;

/**
 * Classe base pels mètodes recomanadors.
 * Guarda usuaris, items i valoracions. Genera recomanacions per usuaris donats.
 * @author edgar.moreno
 */
public abstract class MetodeRecomanador {
    /** Conjunt d'usuaris per defecte **/
    protected ConjuntUsuaris usuaris;
    /** Conjunt d'items per defecte **/
    protected ConjuntItems items;
    /** Conjunt de valoracions per defecte **/
    protected ConjuntValoracions valoracionsPubliques;

    /**
     * Crea un <code>MetodeRecomanador</code> donant un conjunt de dades per defecte.
     * @param usuaris pot ser buit
     * @param items pot ser buit
     * @param valoracions_publiques pot ser buit
     */
    public MetodeRecomanador(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracions_publiques) {
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
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntValoracions valoracions_usuari, int numRecomanacions) {
        return obteRecomanacions(usuari, items, valoracions_usuari, numRecomanacions);
    }

    /**
     * Genera recomanacions per l'usuari donat.
     * @param usuari <code>Usuari</code> pel qual es generen les recomanacions.
     * @param items_recomanables Items que es poden recomanar al usuari.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades, extreura les valoracions de les valoracions publiques de l'usuari.
     */
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems items_recomanables, int numRecomanacions) {
        ConjuntValoracions valoracions = new ConjuntValoracions();
        for (Valoracio val : valoracionsPubliques.obteTotesValoracions().values()) {
            if (val.getUsuari().equals(usuari))
                valoracions.afegir(val);
        }
        return obteRecomanacions(usuari, items_recomanables, valoracions, numRecomanacions);
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
     * @param valoracions_usuari valoracions que s'utilitzaran per les recomanacions.
     * @param numRecomanacions numero maxim de recomanacions que es generaran.
     * @return Un <code>ConjuntDeRecomanacions</code> amb les recomanacions generades.
     */
    public abstract ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems conjuntRecomanable, ConjuntValoracions valoracions_usuari, int numRecomanacions);
}
