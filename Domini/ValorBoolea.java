package Domini;

/**
 * Representa el valor booleà d'un atribut.
 * @author maria.prat
 */
public class ValorBoolea extends ValorAtribut<Boolean> {

    /**
     * Constructor amb el valor booleà.
     * @param valor <code>Boolean</code> que conté el valor d'aquest atribut.
     */
    public ValorBoolea(Boolean valor) {
        this.valor = valor;
    }

    /**
     * @return <code>boolean</code> que conté el valor d'aquest atribut.
     */
    public Boolean getValor() {
        return valor;
    }
}
