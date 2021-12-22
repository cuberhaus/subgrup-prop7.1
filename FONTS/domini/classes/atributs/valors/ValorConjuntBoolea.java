package domini.classes.atributs.valors;

import excepcions.FormatIncorrecteException;

import java.util.ArrayList;

/**
 * Representa el valor compost booleà d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntBoolea extends ValorConjunt<Boolean> {
    /**
     * Constructor d'un ValorConjuntBoolea amb valor nul.
     */
    public ValorConjuntBoolea() {
        this.valor = null;
    }

    /**
     * Constructor amb el conjunt booleà.
     * @param valor <code>ArrayList&lt;ValorAtribut&lt;Boolean&gt;&gt;</code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntBoolea(ArrayList<ValorAtribut<Boolean>> valor) {
        this.valor = valor;
    }

    /**
     * Constructor amb un conjunt de booleans.
     * @param valors <code>boolean[]</code> que conté els valors que s'assignaran a aquest atribut.
     */
    public ValorConjuntBoolea(boolean[] valors) {
        if (valors == null) {
            this.valor = null;
        } else {
            this.valor = new ArrayList<>();
            for (boolean valor : valors) {
                this.valor.add(new ValorBoolea(valor));
            }
        }
    }

    /**
     * Constructor amb una String.
     * @param s <code>bString</code> que conté els valors que s'assignaran a aquest atribut separats pel símbol ';'
     * @throws FormatIncorrecteException El format és incorrecte
     */
    public ValorConjuntBoolea(String s) throws FormatIncorrecteException {
        if (s == null) {
            this.valor = null;
        } else if (s.isEmpty()) {
            this.valor = new ArrayList<>();
        } else {
            this.valor = new ArrayList<>();
            for (String valor : s.split(";")) {
                this.valor.add(new ValorBoolea(valor));
            }
        }
    }

    /**
     * @return Còpia del ValorConjuntBoolea.
     */
    @Override
    public ValorConjuntBoolea copiar() {
        return new ValorConjuntBoolea(valor);
    }

    @Override
    public String obteNomValor() {
        return "ValorConjuntBoolea";
    }

    /**
     * @return <code>ArrayList&lt;ValorAtribut&lt;Boolean&gt;&gt;</code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<Boolean>> obtenirValor() {
        return valor;
    }
}
