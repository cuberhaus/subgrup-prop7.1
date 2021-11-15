package domini.classes.atributs.tipus;

import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorNumeric;

import java.util.Objects;

/**
 * Representa un tipus d'atribut numèric amb la distància discreta.
 * @author maria.prat
 */
public class NumericDiscret extends TipusAtribut {
    @Override
    public TipusAtribut copy() {
        return new NumericDiscret();
    }

    @Override
    public boolean admetValorAtribut(ValorAtribut<?> valorAtribut) {
        return valorAtribut instanceof ValorNumeric;
    }

    @Override
    public double obtenirDistancia(ValorAtribut<?> valor1, ValorAtribut<?> valor2) {
        if (!(valor1 instanceof ValorNumeric && valor2 instanceof ValorNumeric)) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser ValorNumerics.");
        }
        if (Objects.equals(((ValorNumeric) valor1).getValor(), ((ValorNumeric) valor2).getValor())) {
            return 1.0d;
        } else {
            return 0.0d;
        }
    }
}
