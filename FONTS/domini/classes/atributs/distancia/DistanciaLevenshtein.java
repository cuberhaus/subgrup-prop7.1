package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.*;

/**
 * Representa la distància Levenshtein entre dos atributs categòrics o textuals.
 * @author maria.prat
 */
public class DistanciaLevenshtein extends Distancia {
    /**
     * Norma màxima dels valors amb aquesta distància.
     */
    private double normaMaxima = 0.0;

    /**
     * @return Còpia de la Distància.
     */
    @Override
    public Distancia copiar() {
        return new DistanciaLevenshtein();
    }

    @Override
    public String obteNomDistancia() {
        return "DistanciaLevenshtein";
    }

    /**
     * @param valorAtribut Valor d'un atribut
     * @return Cert si la distància admet el valor donat. Altrament, fals.
     */
    @Override
    public boolean admet(ValorAtribut<?> valorAtribut) {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return valorAtribut instanceof ValorCategoric || valorAtribut instanceof ValorTextual;
    }

    /**
     * @param valor1 Valor del primer atribut
     * @param valor2 Valor del segon atribut
     * @return <code>double</code> que conté el valor de la distància entre els dos valors donats.
     * @throws IllegalArgumentException si els dos valors són de subclasses diferents o si la distància no els admet.
     */
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

    /**
     * Actualitza el factor de normalització de la distància donat un nou valor.
     * @param valor Valor d'un atribut
     * @throws IllegalArgumentException si el valor donat és nul
     */
    @Override
    public void actualitzarFactorDeNormalitzacio(ValorAtribut<?> valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No es pot actualitzar el factor de normalització amb un valor nul.");
        }
        normaMaxima = Math.max(normaMaxima, ((String) valor.obtenirValor()).length());
    }

    /**
     * El factor de normalització d'aquesta distància és el doble de la norma màxima dels valors que tenen aquesta
     * distància. Si la norma màxima és zero, el factor de normalització és 1.0.
     * @return double Factor de normalització de la distància
     */
    @Override
    public double obtenirFactorDeNormalitzacio() {
        if (normaMaxima == 0.0) {
            return 1.0;
        }
        // Valor màxim de la distància de DistanciaLevenshtein entre paraules amb norma igual o més petita que normaMaxima,
        // que es pot editar, esborrar i afegir.
        return 3.0 * normaMaxima;
    }
}