package Domini;

/**
 * Representa el valor numèric d'un atribut.
 * @author maria.prat
 */
public class ValorNumeric extends ValorAtribut {
    private double valor;

    /**
     * Constructor amb el <code>TipusAtribut</code> i el valor numèric.
     * @param tipusAtribut <code>TipusAtribut</code> que conté el tipus d'atribut d'aquest valor.
     * @param valor <code>double</code> que conté el valor d'aquest atribut.
     */
    public ValorNumeric(TipusAtribut tipusAtribut, double valor) {
        super(tipusAtribut);
        this.valor = valor;
    }

    /**
     * @return <code>double</code> que conté el valor d'aquest atribut.
     */
    public double getValor() {
        return valor;
    }
}
