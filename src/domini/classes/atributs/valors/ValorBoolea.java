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
        if (s == null || s.isEmpty()) {
            this.valor = null;
        } else {
            this.valor = Boolean.parseBoolean(s.toLowerCase());
        }
    }

    @Override
    public ValorBoolea copiar() {
        return new ValorBoolea(valor);
    }

    /**
     * @return <code>boolean</code> que conté el valor d'aquest atribut.
     */
    @Override
    public Boolean obtenirValor() {
        return valor;
    }


    public static boolean esBoolea(String s) {
        if (s == null) {
            return false;
        }
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }
}