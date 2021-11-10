package Domini;

/**
 * Representa el valor numèric d'un atribut.
 * @author maria.prat
 */
public class ValorNumeric extends ValorAtribut<Double> {

    /**
     * Constructor amb el valor numèric.
     * @param valor <code>Double</code> que conté el valor d'aquest atribut.
     */
    public ValorNumeric(Double valor) {
        this.valor = valor;
    }

    /**
     * @return <code>Double</code> que conté el valor d'aquest atribut.
     */
    @Override
    public Double getValor() {
        return valor;
    }
}
