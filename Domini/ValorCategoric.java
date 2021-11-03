package Domini;

/**
 * Representa el valor categòric d'un atribut.
 * @author maria.prat
 */
public class ValorCategoric extends ValorAtribut<String> {

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
    public String getValor() {
        return valor;
    }
}
