package domini.classes.atributs.valors;

import java.util.ArrayList;

/**
 * Representa el valor compost textual d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntTextual extends ValorConjunt<String> {
    /**
     * Constructor d'un ValorConjuntTextual amb valor nul.
     */
    public ValorConjuntTextual() {
        this.valor = null;
    }

    /**
     * Constructor amb el conjunt textual.
     * @param valor <code>ArrayList&lt;ValorAtribut&lt;String&gt;&gt;</code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntTextual(ArrayList<ValorAtribut<String>> valor) {
        this.valor = valor;
    }

    /**
     * Constructor amb una String.
     * @param s <code>String</code> que conté els valors que s'assignaran a aquest atribut separats pel símbol ';'
     */
    public ValorConjuntTextual(String s) {
        if (s == null) {
            this.valor = null;
        } else {
            this.valor = new ArrayList<>();
            for (String valor : s.split(";")) {
                this.valor.add(new ValorTextual(valor));
            }
        }
    }

    /**
     * Constructor amb un conjunt de Strings.
     * @param valors <code>String[]</code> que conté els valors que s'assignaran a aquest atribut.
     */
    public ValorConjuntTextual(String[] valors) {
        if (valors == null) {
            this.valor = null;
        } else {
            this.valor = new ArrayList<>();
            for (String valor : valors) {
                this.valor.add(new ValorTextual(valor));
            }
        }
    }

    /**
     * @return Còpia del ValorConjuntTextual.
     */
    @Override
    public ValorConjuntTextual copiar() {
        return new ValorConjuntTextual(valor);
    }

    /**
     * @return <code>ArrayList&lt;ValorAtribut&lt;String&gt;&gt;</code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<String>> obtenirValor() {
        return valor;
    }
}
