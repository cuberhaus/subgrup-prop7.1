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
    public boolean admetValorAtribut(ValorAtribut<?> valorAtribut) {
        return valorAtribut instanceof ValorNumeric;
    }

    @Override
    public double obtenirDistancia(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1 instanceof ValorNumeric && valor2 instanceof ValorNumeric)) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser ValorNumerics.");
        }
        return Math.abs(((ValorNumeric) valor1).getValor() - ((ValorNumeric) valor2).getValor());
    }
}
