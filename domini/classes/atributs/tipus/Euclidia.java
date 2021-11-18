package domini.classes.atributs.tipus;

import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorConjuntNumeric;
import domini.classes.atributs.valors.ValorNumeric;

/**
 * Representa un tipus d'atribut numèric amb la distància euclidiana.
 * @author maria.prat
 */
public class Euclidia extends TipusAtribut {
    private double normaMinima = Double.POSITIVE_INFINITY;
    private double normaMaxima = 0.0;

    @Override
    public TipusAtribut copy() {
        return new Euclidia();
    }

    @Override
    public boolean admetValorAtribut(ValorAtribut<?> valorAtribut) {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return valorAtribut instanceof ValorNumeric || valorAtribut instanceof ValorConjuntNumeric;
    }

    @Override
    public double obtenirDistancia(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admetValorAtribut(valor1)) {
            throw new IllegalArgumentException("El TipusAtribut no admet el tipus dels ValorAtributs donats.");
        }
        if (valor1 instanceof ValorNumeric) {
            return Math.abs(((ValorNumeric) valor1).getValor() - ((ValorNumeric) valor2).getValor());
        } else {
            ValorConjuntNumeric valorConjuntNumeric1 = (ValorConjuntNumeric) valor1;
            ValorConjuntNumeric valorConjuntNumeric2 = (ValorConjuntNumeric) valor2;
            if (valorConjuntNumeric1.getValor().size() != valorConjuntNumeric2.getValor().size()) {
                throw new IllegalArgumentException("No es pot calcular la distància euclidiana entre dos " +
                        "ValorConjuntNumerics de mides diferents");
            }
            double distancia = 0.0;
            for (int i = 0; i < valorConjuntNumeric1.getValor().size(); ++i) {
                distancia += Math.pow(valorConjuntNumeric1.getValor().get(i).getValor()
                        - valorConjuntNumeric2.getValor().get(i).getValor(), 2);
            }
            return Math.sqrt(distancia);
        }
    }

    public double obtenirNorma(ValorAtribut<?> valor) throws IllegalArgumentException {
        if (valor == null) {
            throw new IllegalArgumentException("No es pot obtenir la norma d'un ValorAtribut nul.");
        }
        if (!admetValorAtribut(valor)) {
            throw new IllegalArgumentException("El TipusAtribut no admet el tipus del ValorAtribut donat.");
        }
        if (valor instanceof ValorNumeric) {
            return obtenirDistancia(new ValorNumeric(0.0), valor);
        } else {
            double[] zero = new double[((ValorConjuntNumeric)valor).getValor().size()];
            return obtenirDistancia(new ValorConjuntNumeric(zero), valor);
        }
    }

    public void actualitzarFactorDeNormalitzacio(ValorAtribut<?> valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No es pot actualitzar el factor de normalització amb un valor nul.");
        }
        normaMinima = Math.min(obtenirNorma(valor), normaMinima);
        normaMaxima = Math.max(obtenirNorma(valor), normaMaxima);
    }

    public double obtenirFactorDeNormalitzacio() {
        if (normaMaxima == Double.POSITIVE_INFINITY || normaMaxima == normaMinima) {
            return 1.0;
        }
        return normaMaxima - normaMinima;
    }
}
