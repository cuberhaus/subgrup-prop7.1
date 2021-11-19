package domini.classes.atributs.valors;

import java.util.ArrayList;

/**
 * Representa el valor compost categòric d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntCategoric extends ValorConjunt<String> {

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
     * Constructor amb un conjunt de <code>Strings</code>.
     * @param valors <code>String[]</code> que conté els valors que s'assignaran a aquest atribut.
     */
    public ValorConjuntCategoric(String[] valors) {
        this.valor = new ArrayList<>();
        for (String valor : valors) {
            this.valor.add(new ValorCategoric(valor));
        }
    }

    public ValorConjuntCategoric(String s) {
        this(s.split(";"));
    }

    /**
     * @return <code>ArrayList<ValorAtribut<String></code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<String>> getValor() {
        return valor;
    }

    @Override
    public ValorConjuntCategoric copy() {
        return new ValorConjuntCategoric(valor);
    }
}
