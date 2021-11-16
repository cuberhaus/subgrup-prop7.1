package domini.classes.atributs.tipus;

import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorNumeric;

/**
 * Representa un tipus d'atribut numèric amb la distància euclidiana.
 * @author maria.prat
 */
public class Euclidia extends TipusAtribut {
    @Override
    public TipusAtribut copy() {
        return new Euclidia();
    }

    @Override
    public boolean admetValorAtribut(ValorAtribut<?> valorAtribut) {
        return valorAtribut instanceof ValorNumeric;
    }

    @Override
    public double obtenirDistancia(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admetValorAtribut(valor1)) {
            throw new IllegalArgumentException("El TipusAtribut no admet el tipus dels ValorAtributs donats.");
        }
        return Math.abs(((ValorNumeric) valor1).getValor() - ((ValorNumeric) valor2).getValor());
    }
}
