package domini.classes;

import java.util.Arrays;

public class SlopeOne {
    private final int num_usuaris;
    private final int num_valoracions;
    private final Double[][] valoracions;
    private final Double[][] prediccions;
    //private Double[] mitjana_usuari;
    //private int[] valoracions_usuari;
    private final Double[][] desviacions;

    // Matriu rectangular!
    // valoracions[i][j] conte la valoració de l'usuari i  l'item j, o null si no existeix
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
        //calculaMitjanesUsuaris();
    }
    /*
    private void calculaMitjanesUsuaris() {
        valoracions_usuari = new int[num_usuaris];
        mitjana_usuari = new Double[num_usuaris];
        for (int i = 0; i < num_usuaris; ++i) {
            for (int j = 0; j < num_valoracions; ++j) {
                if (valoracions[i][j] != null) {
                    valoracions_usuari[i]++;
                    mitjana_usuari[i] += valoracions[i][j];
                }
            }
            mitjana_usuari[i] /= valoracions_usuari[i];
        }
    }
    */

    private void calculaDesviacions() {
        for (int i = 0; i < num_valoracions; ++i)
            for (int j = 0; j < num_valoracions; ++j)
                desviacions[i][j] = calculaDesviacio(i,j);
    }
    private double calculaDesviacio(int j, int i) {
        int cardinal = 0;
        double suma = 0;
        for (int k = 0; k < num_usuaris; ++k) {
            if (valoracions[k][i] != null && valoracions[k][i] != null) {
                ++cardinal;
                suma += valoracions[k][j] - valoracions[k][i];
            }
        }
        if(cardinal == 0) return Double.NaN;
        return suma/cardinal;
    }

    public Double[][] getTotesPrediccions() {
        for (int i = 0; i < num_usuaris; ++i)
            for (int j = 0; j < num_valoracions; ++j)
                getPrediccio(i,j);
        return prediccions;
    }

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
