package domini.classes.atributs.valors;

import excepcions.FormatIncorrecteException;

/**
 * Representa el valor booleà d'un atribut.
 * @author maria.prat
 */
public class ValorBoolea extends ValorAtribut<Boolean> {
    /**
     * Constructor d'un ValorBooleà amb valor nul.
     */
    public ValorBoolea() {
        this.valor = null;
    }

    /**
     * Constructor amb un booleà.
     * @param valor <code>Boolean</code> que conté el valor d'aquest atribut.
     */
    public ValorBoolea(Boolean valor) {
        this.valor = valor;
    }

    /**
     * Constructor d'un ValorBooleà amb una String que conté un booleà.
     * @param s <code>String</code> que conté el valor d'aquest atribut.
     * @throws FormatIncorrecteException El format és incorrecte
     */
    public ValorBoolea(String s) throws FormatIncorrecteException {
        if (s == null || s.isEmpty()) {
            this.valor = null;
        } else {
            if (s.equalsIgnoreCase("true")) {
                this.valor = true;
            } else if (s.equalsIgnoreCase("false")) {
                this.valor = false;
            } else {
                throw new FormatIncorrecteException("No es pot llegir un booleà de: " + s);
            }
        }
    }

    /**
     * @return Còpia del ValorBoolea.
     */
    @Override
    public ValorBoolea copiar() {
        return new ValorBoolea(valor);
    }

    @Override
    public String obteNomValor() {
        return "ValorBoolea";
    }

    /**
     * @return <code>boolean</code> que conté el valor d'aquest atribut.
     */
    @Override
    public Boolean obtenirValor() {
        return valor;
    }

    /**
     * @param s <code>String</code> que conté un valor
     * @return Cert si s'ha pogut obtenir un valor booleà de la String donada. Altrament, retorna fals.
     */
    public static boolean esBoolea(String s) {
        if (s == null) {
            return false;
        }
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }

    @Override
    public String toString() {
        return String.valueOf(this.valor);
    }
}