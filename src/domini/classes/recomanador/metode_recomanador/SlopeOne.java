package domini.classes.recomanador.metode_recomanador;

import java.util.Arrays;

/**
 * Processa un conjunt de valoracions d'un conjunt d'usuaris a un conjunt d'ítems.
 * Prediu la valoració d'un usuari donat a un item donat.
 * @author edgar.moreno
 */
public class SlopeOne {
    /** Numero de usuaris processats */
    private final int num_usuaris;
    /** Numero de valoracions processades */
    private final int num_valoracions;

    /** Valoracions reals dels usuaris, <code>valoracions[i][j]</code> conté la valoració de l'usuari i a l'item j */
    private final Double[][] valoracions;
    /** Prediccions de l'algorisme, <code>prediccions[i][j]</code> conté la predicció de l'usuari i a l'item j */
    private final Double[][] prediccions;
    /** Desviacions entre ítems, <code>desviacions[i][j]</code> conté la desviació de l'item i respecte el j */
    private final Double[][] desviacions;

    /**
     * @param valoracions Matriu rectangular. <code>prediccions[i][j]</code> conté la predicció de l'usuari a l'item j
     *                    Si la valoració no es coneguda hi ha un valor de <code>null</code>.
     */
    public SlopeOne(Double[][] valoracions) {
        num_usuaris = valoracions.length;
        num_valoracions = valoracions[0].length;
        this.valoracions = valoracions;
        prediccions = new Double[num_usuaris][num_valoracions];
        for (Double[] x : prediccions) {
            Arrays.fill(x, null);
        }
        desviacions = new Double[num_valoracions][num_valoracions];
        calculaDesviacions();
    }

    private void calculaDesviacions() {
        for (int i = 0; i < num_valoracions; ++i)
            for (int j = 0; j < num_valoracions; ++j)
                desviacions[i][j] = calculaDesviacio(i,j);
    }

    private double calculaDesviacio(int j, int i) {
        int cardinal = 0;
        double suma = 0;
        for (int k = 0; k < num_usuaris; ++k) {
            if (valoracions[k][j] != null && valoracions[k][i] != null) {
                ++cardinal;
                suma += valoracions[k][j] - valoracions[k][i];
            }
        }
        if(cardinal == 0) return Double.NaN;
        return suma/cardinal;
    }

    /**
     * @return una matriu de num_usuaris x num_items amb totes les prediccions.
     */
    public Double[][] getTotesPrediccions() {
        for (int i = 0; i < num_usuaris; ++i)
            for (int j = 0; j < num_valoracions; ++j)
                getPrediccio(i,j);
        return prediccions;
    }

    /**
     * @param usuari un nombre 0..num_usuaris-1 que correspon al mateix ordre que a l'entrada inicial.
     * @param item un nombre 0..num_items-1 que correspon al mateix ordre que a l'entrada inicial.
     * @return la predicció del usuari al item
     */
    public double getPrediccio(int usuari, int item) {
        if (prediccions[usuari][item] != null)
            return prediccions[usuari][item];
        prediccions[usuari][item] = 0.;
        double mitjana = 0;
        double suma_desviacions = 0;
        double ratings_usables = 0;
        for (int i = 0; i < num_valoracions; ++i) {
            if (i == item) continue;
            if (valoracions[usuari][i] == null) continue;
            double desviacio = desviacions[item][i];
            if (Double.isNaN(desviacio)) {
                continue;
            }
            ratings_usables++;
            suma_desviacions += calculaDesviacio(item, i);
            mitjana += valoracions[usuari][i];
        }
        mitjana /= ratings_usables;
        suma_desviacions /= ratings_usables;
        return prediccions[usuari][item] = mitjana + suma_desviacions;
    }


}
