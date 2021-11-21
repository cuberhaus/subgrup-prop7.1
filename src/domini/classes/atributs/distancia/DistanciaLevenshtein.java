package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.*;

/**
 * Representa la distància DistanciaLevenshtein entre dos atributs categòrics o textuals.
 * @author maria.prat
 */
public class DistanciaLevenshtein extends Distancia {
    private double normaMaxima = 0.0;

    @Override
    public Distancia copy() {
        return new DistanciaLevenshtein();
    }

    @Override
    public boolean admet(ValorAtribut<?> valorAtribut) {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return valorAtribut instanceof ValorCategoric || valorAtribut instanceof ValorTextual;
    }

    @Override
    public double obtenir(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admet(valor1)) {
            throw new IllegalArgumentException("La distància no admet el tipus dels ValorAtributs donats.");
        }

        int m = ((String) valor1.obtenirValor()).length();
        int n = ((String) valor2.obtenirValor()).length();

        double[] u = new double[n + 1];
        double[] v = new double[n + 1];

        for (int i = 0; i < n + 1; ++i) {
            u[i] = i;
        }

        for (int i = 0; i < m; ++i) {
            v[0] = i + 1;
            for (int j = 0; j < n; ++j) {
                double costEsborrat = u[j + 1] + 1;
                double costInsercio = v[j] + 1;
                double costSubstitucio = u[j];
                if (((String)valor1.obtenirValor()).charAt(i) != ((String)valor2.obtenirValor()).charAt(j)) {
                    costSubstitucio += 1;
                }
                v[j + 1] = Math.min(costSubstitucio, Math.min(costEsborrat, costInsercio));
            }
            double[] temp = u;
            u = v;
            v = temp;
        }
        return u[n];
    }

    @Override
    public void actualitzarFactorDeNormalitzacio(ValorAtribut<?> valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No es pot actualitzar el factor de normalització amb un valor nul.");
        }
        normaMaxima = Math.max(normaMaxima, ((String) valor.obtenirValor()).length());
    }

    @Override
    public double obtenirFactorDeNormalitzacio() {
        if (normaMaxima == 0.0) {
            return 1.0;
        }
        // Valor màxim de la distància de DistanciaLevenshtein entre paraules amb norma igual o més petita que normaMaxima.
        return 3.0 * normaMaxima;
    }
}