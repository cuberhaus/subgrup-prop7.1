package domini.classes.atributs.valors;

import java.util.ArrayList;

/**
 * Representa el valor compost categòric d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntCategoric extends ValorConjunt<String> {
    /**
     * Constructor d'un ValorConjuntCategoric amb valor nul.
     */
    public ValorConjuntCategoric() {
        this.valor = null;
    }

    /**
     * Constructor amb el conjunt categòric.
     * @param valor <code>ArrayList<ValorAtribut<String></code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntCategoric(ArrayList<ValorAtribut<String>> valor) {
        this.valor = valor;
    }

    /**
     * Constructor amb un conjunt de Strings.
     * @param valors <code>String[]</code> que conté els valors que s'assignaran a aquest atribut.
     */
    public ValorConjuntCategoric(String[] valors) {
        if (valors == null) {
            this.valor = null;
        } else {
            this.valor = new ArrayList<>();
            for (String valor : valors) {
                this.valor.add(new ValorCategoric(valor));
            }
        }
    }

    /**
     * Constructor amb una String.
     * @param s <code>String</code> que conté els valors que s'assignaran a aquest atribut separats pel símbol ';'
     */
    public ValorConjuntCategoric(String s) {
        if (s == null) {
            this.valor = null;
        } else {
            this.valor = new ArrayList<>();
            for (String valor : s.split(";")) {
                this.valor.add(new ValorCategoric(valor));
            }
        }
    }

    /**
     * @return Còpia del ValorConjuntCategoric.
     */
    @Override
    public ValorConjuntCategoric copiar() {
        return new ValorConjuntCategoric(valor);
    }

    /**
     * @return <code>ArrayList<ValorAtribut<String></code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<String>> obtenirValor() {
        return valor;
    }
}
