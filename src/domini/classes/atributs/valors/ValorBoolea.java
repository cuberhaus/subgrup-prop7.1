package domini.classes.atributs.valors;

/**
 * Representa el valor booleà d'un atribut.
 * @author maria.prat
 */
public class ValorBoolea extends ValorAtribut<Boolean> {

    public ValorBoolea() {
        this.valor = null;
    }

    /**
     * Constructor amb el valor booleà.
     * @param valor <code>Boolean</code> que conté el valor d'aquest atribut.
     */
    public ValorBoolea(Boolean valor) {
        this.valor = valor;
    }

    public ValorBoolea(String s) {
        this.valor = Boolean.parseBoolean(s.toLowerCase());
    }

    /**
     * @return <code>boolean</code> que conté el valor d'aquest atribut.
     */
    @Override
    public Boolean getValor() {
        return valor;
    }

    @Override
    public ValorBoolea copy() {
        return new ValorBoolea(valor);
    }


    public static boolean esBoolea(String s) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }
}