package Domini;

/**
 * Representa el valor textual d'un atribut.
 * @author maria.prat
 */
public class ValorText extends ValorAtribut<String> {

    /**
     * Constructor amb el valor numèric.
     * @param valor <code>String</code> que conté el valor d'aquest atribut.
     */
    public ValorText(String valor) {
        this.valor = valor;
    }

    /**
     * @return <code>String</code> que conté el valor d'aquest atribut.
     */
    public String getValor() {
        return valor;
    }
}