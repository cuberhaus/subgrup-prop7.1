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
