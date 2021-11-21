package domini.classes.atributs.valors;

/**
 * Representa el valor categòric d'un atribut.
 * @author maria.prat
 */
public class ValorCategoric extends ValorAtribut<String> {

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
     * @return <code>String</code> que conté el valor d'aquest atribut.
     */
    @Override
    public String obtenirValor() {
        return valor;
    }

    @Override
    public ValorCategoric copiar() {
        return new ValorCategoric(valor);
    }
}
