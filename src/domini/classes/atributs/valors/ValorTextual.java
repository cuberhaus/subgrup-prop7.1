package domini.classes.atributs.valors;

/**
 * Representa el valor textual d'un atribut.
 * @author maria.prat
 */
public class ValorTextual extends ValorAtribut<String> {

    public ValorTextual() {
        this.valor = null;
    }

    /**
     * Constructor amb el valor numèric.
     * @param valor <code>String</code> que conté el valor d'aquest atribut.
     */
    public ValorTextual(String valor) {
        this.valor = valor;
    }

    /**
     * @return <code>String</code> que conté el valor d'aquest atribut.
     */
    @Override
    public String obtenirValor() {
        return valor;
    }

    @Override
    public ValorTextual copiar() {
        return new ValorTextual(valor);
    }
}
