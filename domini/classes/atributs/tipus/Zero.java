package domini.classes.atributs.tipus;

import domini.classes.atributs.valors.*;

/**
 * Representa un tipus d'atribut amb distància zero.
 * @author maria.prat
 */
public class Zero extends TipusAtribut {
    @Override
    public TipusAtribut copy() {
        return new Zero();
    }

    @Override
    public boolean admetValorAtribut(ValorAtribut<?> valorAtribut) {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return true;
    }

    @Override
    public double obtenirDistancia(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admetValorAtribut(valor1)) {
            throw new IllegalArgumentException("El TipusAtribut no admet el tipus dels ValorAtributs donats.");
        }
        return 0.0d;
    }
}
