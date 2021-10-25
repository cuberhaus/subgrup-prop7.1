package Domini;

/**
 * Representa un tipus d'atribut numèric amb la distància euclidiana.
 * @author maria.prat
 */
public class NumericEuclidia extends TipusAtribut {
    @Override
    public TipusAtribut copy() {
        return new NumericEuclidia();
    }

    @Override
    public double obtenirDistancia(ValorAtribut valor1, ValorAtribut valor2) throws IllegalArgumentException {
        if (!(valor1 instanceof ValorNumeric valorNumeric1 && valor2 instanceof ValorNumeric valorNumeric2)) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser ValorNumerics.");
        }
        return Math.abs(valorNumeric1.getValor() - valorNumeric2.getValor());
    }
}
