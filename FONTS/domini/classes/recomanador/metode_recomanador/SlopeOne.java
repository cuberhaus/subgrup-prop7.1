package domini.classes.recomanador.metode_recomanador;

import java.util.Arrays;

/**
 * Processa un conjunt de valoracions d'un conjunt d'usuaris a un conjunt d'ítems.
 * Prediu la valoració d'un usuari donat a un item donat.
 * @author edgar.moreno
 */
public class SlopeOne {
    /** Numero de usuaris processats */
    private final int numUsuaris;
    /** Numero de valoracions processades */
    private final int numValoracions;

    /** Valoracions reals dels usuaris, <code>valoracions[i][j]</code> conté la valoració de l'usuari i a l'ítem j */
    private final Double[][] valoracions;
    /** Prediccions de l'algorisme, <code>prediccions[i][j]</code> conté la predicció de l'usuari i a l'ítem j */
    private final Double[][] prediccions;
    /** Desviacions entre ítems, <code>desviacions[i][j]</code> conté la desviació de l'ítem i respecte al j */
    private final Double[][] desviacions;

    /**
     * @param valoracions Matriu rectangular. <code>prediccions[i][j]</code> conté la predicció de l'usuari a l'ítem j
     *                    Si la valoració no és coneguda, hi ha un valor de <code>null</code>.
     */
    public SlopeOne(Double[][] valoracions) {
        numUsuaris = valoracions.length;
        numValoracions = valoracions[0].length;
        this.valoracions = valoracions;
        prediccions = new Double[numUsuaris][numValoracions];
        for (Double[] x : prediccions) {
            Arrays.fill(x, null);
        }
        desviacions = new Double[numValoracions][numValoracions];
        calculaDesviacions();
    }

    private void calculaDesviacions() {
        for (int i = 0; i < numValoracions; ++i)
            for (int j = 0; j < numValoracions; ++j)
                desviacions[i][j] = calculaDesviacio(i,j);
    }

    private double calculaDesviacio(int j, int i) {
        int cardinal = 0;
        double suma = 0;
        for (int k = 0; k < numUsuaris; ++k) {
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
        for (int i = 0; i < numUsuaris; ++i)
            for (int j = 0; j < numValoracions; ++j)
                getPrediccio(i,j);
        return prediccions;
    }

    /**
     * @param usuari un nombre 0..num_usuaris-1 que correspon al mateix ordre que a l'entrada inicial.
     * @param item un nombre 0..num_items-1 que correspon al mateix ordre que a l'entrada inicial.
     * @return la predicció de l'usuari a l'ítem
     */
    public double getPrediccio(int usuari, int item) {
        if (prediccions[usuari][item] != null)
            return prediccions[usuari][item];
        prediccions[usuari][item] = 0.;
        double mitjana = 0;
        double sumaDesviacions = 0;
        double ratingsUsables = 0;
        for (int i = 0; i < numValoracions; ++i) {
            if (i == item) continue;
            if (valoracions[usuari][i] == null) continue;
            double desviacio = desviacions[item][i];
            if (Double.isNaN(desviacio)) {
                continue;
            }
            ratingsUsables++;
            sumaDesviacions += desviacio;
            mitjana += valoracions[usuari][i];
        }
        mitjana /= ratingsUsables;
        sumaDesviacions /= ratingsUsables;
        return prediccions[usuari][item] = mitjana + sumaDesviacions;
    }


}
