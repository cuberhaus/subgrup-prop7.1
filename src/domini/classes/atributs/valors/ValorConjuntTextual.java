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

    public ValorConjuntTextual(String s) {
        if (s == null) {
            this.valor = null;
        } else {
            this.valor = new ArrayList<>();
            for (String valor : s.split(";")) {
                this.valor.add(new ValorCategoric(valor));
            }
        }
    }

    public ValorConjuntTextual(String[] valors) {
        if (valors == null) {
            this.valor = null;
        } else {
            this.valor = new ArrayList<>();
            for (String valor : valors) {
                this.valor.add(new ValorCategoric(valor));
            }
        }
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
