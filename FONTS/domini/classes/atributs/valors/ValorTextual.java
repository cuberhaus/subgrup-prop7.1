package domini.classes.atributs.valors;

/**
 * Representa el valor textual d'un atribut.
 * @author maria.prat
 */
public class ValorTextual extends ValorAtribut<String> {
    /**
     * Constructor d'un ValorTextual amb valor nul.
     */
    public ValorTextual() {
        this.valor = null;
    }

    /**
     * Constructor amb el valor textual.
     * @param valor <code>String</code> que conté el valor d'aquest atribut.
     */
    public ValorTextual(String valor) {
        this.valor = valor;
    }

    /**
     * @return Còpia del ValorTextual.
     */
    @Override
    public ValorTextual copiar() {
        return new ValorTextual(valor);
    }

    /**
     * @return <code>String</code> que conté el valor d'aquest atribut.
     */
    @Override
    public String obtenirValor() {
        return valor;
    }
}