package Domini;

import java.util.ArrayList;

public class ConjuntDePunts extends ArrayList<Punt> {
    private int dimensio;
    private Punt baricentre;

    public int getDimensio() {
        return dimensio;
    }
    public int getNumPunts() {
        return super.size();
    }
    public ConjuntDePunts() {
        dimensio = -1;
        baricentre = null;
    }
    private void recalculaBaricentre(Punt nouPunt) {
        if (baricentre == null) {
            baricentre = (Punt) nouPunt.clone();
        }
        int n = this.getNumPunts();
        baricentre.mult(n);
        baricentre.afegir(nouPunt);
        baricentre.mult(1/(n+1.));
    }
    @Override
    public boolean add(Punt doubles) throws IllegalArgumentException {
        if (dimensio == -1) {
            dimensio = doubles.getDimensio();
            baricentre = (Punt) doubles.clone();
        } else if(dimensio != doubles.getDimensio()) {
            throw new IllegalArgumentException("No es pot afegir un punt de diferent dimensio.");
        }
        recalculaBaricentre(doubles);
        return super.add(doubles);
    }
}
