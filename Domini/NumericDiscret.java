package Domini;

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
    public double obtenirDistancia(ValorAtribut valor1, ValorAtribut valor2) {
        if (!(valor1 instanceof ValorNumeric valorNumeric1 && valor2 instanceof ValorNumeric valorNumeric2)) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser ValorNumerics.");
        }
        if (valorNumeric1.getValor() == valorNumeric2.getValor()) {
            return 1.0d;
        } else {
            return 0.0d;
        }
    }
}
