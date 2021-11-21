package domini.classes.atributs.valors;

import java.util.ArrayList;

/**
 * Representa el valor compost textual d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntTextual extends ValorConjunt<String> {

    public ValorConjuntTextual() {
        this.valor = null;
    }

    /**
     * Constructor amb el conjunt textual.
     * @param valor <code>ArrayList<ValorAtribut<String></code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntTextual(ArrayList<ValorAtribut<String>> valor) {
        this.valor = valor;
    }

    /**
     * Constructor amb un conjunt de <code>Strings</code>.
     * @param valors <code>String[]</code> que conté els valors que s'assignaran a aquest atribut.
     */
    public ValorConjuntTextual(String[] valors) {
        this.valor = new ArrayList<>();
        for (String valor : valors) {
            this.valor.add(new ValorTextual(valor));
        }
    }

    public ValorConjuntTextual(String s) {
        this(s.split(";"));
    }

    @Override
    public ValorConjuntTextual copiar() {
        return new ValorConjuntTextual(valor);
    }

    /**
     * @return <code>ArrayList<ValorAtribut<String></code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<String>> obtenirValor() {
        return valor;
    }
}
