package domini.classes.atributs.valors;

/**
 * Representa el valor categòric d'un atribut.
 * @author maria.prat
 */
public class ValorCategoric extends ValorAtribut<String> {
    /**
     * Constructor d'un ValorCategoric amb valor nul.
     */
    public ValorCategoric() {
        this.valor = null;
    }

    /**
     * Constructor amb el valor categòric.
     * @param valor <code>String</code> que conté el valor d'aquest atribut.
     */
    public ValorCategoric(String valor) {
        this.valor = valor;
    }

    /**
     * @return Còpia del ValorCategoric.
     */
    @Override
    public ValorCategoric copiar() {
        return new ValorCategoric(valor);
    }

    /**
     * @return <code>String</code> que conté el valor d'aquest atribut.
     */
    @Override
    public String obtenirValor() {
        return valor;
    }
}
