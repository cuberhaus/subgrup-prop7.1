package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.*;

/**
 * Representa la distància discreta entre dos atributs simples (booleans, categòrics, numèrics o textuals).
 * @author maria.prat
 */
public class DistanciaDiscreta extends Distancia {
    @Override
    public Distancia copy() {
        return new DistanciaDiscreta();
    }

    @Override
    public boolean admet(ValorAtribut<?> valorAtribut) throws IllegalArgumentException {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return valorAtribut instanceof ValorBoolea || valorAtribut instanceof ValorCategoric ||
                valorAtribut instanceof ValorNumeric || valorAtribut instanceof ValorTextual;
    }

    @Override
    public double obtenir(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admet(valor1)) {
            throw new IllegalArgumentException("La distància no admet el tipus dels ValorAtributs donats.");
        }
        if (valor1.obtenirValor().equals(valor2.obtenirValor())) {
            return 0.0d;
        } else {
            return 1.0d;
        }
    }

    @Override
    public void actualitzarFactorDeNormalitzacio(ValorAtribut<?> valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No es pot actualitzar el factor de normalització amb un valor nul.");
        }
    }

    @Override
    public double obtenirFactorDeNormalitzacio() {
        return 1.0;
    }
}
