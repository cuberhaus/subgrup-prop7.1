package domini.classes.atributs.tipus;

import domini.classes.atributs.valors.*;

/**
 * Representa un tipus d'atribut simple (booleà, categòric, numèric o textual) amb la distància discreta.
 * @author maria.prat
 */
public class Discret extends TipusAtribut {
    @Override
    public TipusAtribut copy() {
        return new Discret();
    }

    @Override
    public boolean admetValorAtribut(ValorAtribut<?> valorAtribut) throws IllegalArgumentException {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return valorAtribut instanceof ValorBoolea || valorAtribut instanceof ValorCategoric ||
                valorAtribut instanceof ValorNumeric || valorAtribut instanceof ValorTextual;
    }

    @Override
    public double obtenirDistancia(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admetValorAtribut(valor1)) {
            throw new IllegalArgumentException("El TipusAtribut no admet el tipus dels ValorAtributs donats.");
        }
        if (valor1.getValor().equals(valor2.getValor())) {
            return 0.0d;
        } else {
            return 1.0d;
        }
    }
}
