package domini.classes.atributs.tipus;

import domini.classes.atributs.valors.*;

/**
 * Representa un tipus d'atribut categòric o textual amb la distància de Levenshtein.
 * @author maria.prat
 */
public class Levenshtein extends TipusAtribut {
    @Override
    public TipusAtribut copy() {
        return new Levenshtein();
    }

    @Override
    public boolean admetValorAtribut(ValorAtribut<?> valorAtribut) {
        return valorAtribut instanceof ValorCategoric || valorAtribut instanceof ValorTextual;
    }

    @Override
    public double obtenirDistancia(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admetValorAtribut(valor1)) {
            throw new IllegalArgumentException("El TipusAtribut no admet el tipus dels ValorAtributs donats.");
        }

        int n = ((String) valor2.getValor()).length();
        double[] u = new double[n + 1];
        double[] v = new double[n + 1];

        for (int i = 0; i < u.length; ++i) {
            u[i] = i;
        }

        for (int i = 0; i < n - 1; ++i) {
            v[0] = i + 1;
            for (int j = 0; j < u.length - 1; ++j) {
                double costEsborrat = u[j + 1] + 1;
                double costInsercio = v[j] + 1;
                double costSubstitucio = u[j] + 1;
                if (((String)valor1.getValor()).charAt(i) != ((String)valor2.getValor()).charAt(j)) {
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
}