package domini.classes;

import java.util.TreeMap;

public class ConjuntDePunts extends TreeMap<Integer, Punt> {
    private int dimensio;
    private Punt baricentre;
    private int new_id = 0;

    public ConjuntDePunts() {
        dimensio = -1;
        baricentre = null;
    }

    public int getDimensio() {
        return dimensio;
    }
    public int getNumPunts() {
        return super.size();
    }
    public Punt getBaricentre() { return (Punt) baricentre.clone(); }
    public Integer getIdLliure() {
        while (super.containsKey(new_id)) {
            new_id++;
        }
        return new_id;
    }
    private void recalculaBaricentreAfegit(Punt nouPunt) {
        if (baricentre == null) {
            baricentre = (Punt) nouPunt.clone();
        }
        int n = this.getNumPunts();
        baricentre.mult(n);
        baricentre.afegir(nouPunt);
        baricentre.mult(1/(n+1.));
    }

    private void recalculaBaricentreEliminat(Punt vellPunt) {
        int n = this.getNumPunts();
        if (n == 1) {
            baricentre = null;
            return;
        }
        baricentre.mult(n);
        baricentre.resta(vellPunt);
        baricentre.mult(1/(n-1.));
    }

    @Override
    public Punt put(Integer id, Punt doubles) throws IllegalArgumentException {
        if (dimensio == -1) {
            dimensio = doubles.getDimensio();
            baricentre = (Punt) doubles.clone();
        } else if(dimensio != doubles.getDimensio()) {
            throw new IllegalArgumentException("No es pot afegir un punt de diferent dimensio.");
        }
        recalculaBaricentreAfegit(doubles);
        return super.put(id, doubles);
    }

    public Integer add(Punt punt) throws IllegalArgumentException {
        Integer id = getIdLliure();
        put(id, punt);
        return id;
    }

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
