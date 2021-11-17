package domini.classes.recomanador;

import java.util.TreeMap;

/**
 * Representa un conjunt de punts i calcula el baricentre d'aquests.
 * @author edgar.moreno
 */
public class ConjuntDePunts extends TreeMap<Integer, Punt> {

    /** Dimensió dels punts del conjunt */
    private int dimensio;
    /** Baricentre del conjunt de punts */
    private Punt baricentre;

    /** ID de referencia per buscar la primera ID no utilitzada */
    private int ultimId = 0;

    /**
     * Crea un conjunt de punts buit sense cap dimensió assignada.
     */
    public ConjuntDePunts() {
        dimensio = -1;
        baricentre = null;
    }

    /**
     * @return La dimensió dels punts del conjunt o -1 si esta buit.
     */
    public int obtenirDimensio() {
        return dimensio;
    }

    /**
     * @return nombre de punts al conjunt.
     */
    public int obtenirNumPunts() {
        return super.size();
    }

    /**
     * @return una copia del punt baricentre del conjunt o null si el conjunt es buit.
     */
    public Punt obtenirBaricentre() { return (Punt) baricentre.clone(); }

    /**
     * @return una id que no estigui utilitzada per cap element del conjunt.
     */
    public Integer obtenirIdLliure() {
        while (super.containsKey(ultimId)) {
            ultimId++;
        }
        return ultimId;
    }

    private void recalculaBaricentreAfegit(Punt nouPunt) {
        if (baricentre == null) {
            baricentre = (Punt) nouPunt.clone();
        }
        int n = this.obtenirNumPunts();
        baricentre.mult(n);
        baricentre.afegir(nouPunt);
        baricentre.mult(1/(n+1.));
    }

    private void recalculaBaricentreEliminat(Punt vellPunt) {
        int n = this.obtenirNumPunts();
        if (n == 1) {
            baricentre = null;
            return;
        }
        baricentre.mult(n);
        baricentre.resta(vellPunt);
        baricentre.mult(1/(n-1.));
    }

    /**
     * @param id Id amb el que s'identifica el punt afegit.
     * @param doubles <code>Punt</code> a afegir al conjunt.
     * @return <code>null</code>
     * @throws IllegalArgumentException Si punt no té la mateixa dimensió que el conjunt o la id ja existia.
     */
    @Override
    public Punt put(Integer id, Punt doubles) throws IllegalArgumentException {
        if (dimensio == -1) {
            dimensio = doubles.obtenirDimensio();
            baricentre = (Punt) doubles.clone();
        } else if(dimensio != doubles.obtenirDimensio()) {
            throw new IllegalArgumentException("No es pot afegir un punt de diferent dimensio.");
        }
        if (get(id) != null) {
            throw new IllegalArgumentException("Borra el punt amb aquest id abans de afegir-ne un altre.");
        }
        recalculaBaricentreAfegit(doubles);
        return super.put(id, doubles);
    }

    /**
     * @param punt <code>Punt</code> a afegir al conjunt
     * @return la id assignada al punt
     * @throws IllegalArgumentException si la dimensió no concorda amb la del conjunt.
     */
    public Integer add(Punt punt) throws IllegalArgumentException {
        Integer id = obtenirIdLliure();
        put(id, punt);
        return id;
    }

    /**
     * @param id El identificador del punt a eliminar.
     * @return null si no existia el identificador o el punt eliminat en cas contrari.
     */
    @Override
    public Punt remove(Object id) {
        Punt aTreure = super.get(id);
        if (aTreure == null) {
            return null;
        }
        recalculaBaricentreEliminat(aTreure);
        return super.remove(id);
    }
}
