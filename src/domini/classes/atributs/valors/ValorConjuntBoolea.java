package domini.classes.atributs.valors;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Representa el valor compost booleà d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntBoolea extends ValorConjunt<Boolean> {

    public ValorConjuntBoolea() {
        this.valor = null;
    }

    /**
     * Constructor amb el conjunt booleà.
     * @param valor <code>ArrayList<ValorAtribut<Boolean></code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntBoolea(ArrayList<ValorAtribut<Boolean>> valor) {
        this.valor = valor;
    }

    /**
     * Constructor amb un conjunt de <code>booleans</code>.
     * @param valors <code>boolean[]</code> que conté els valors que s'assignaran a aquest atribut.
     */
    public ValorConjuntBoolea(boolean[] valors) {
        this.valor = new ArrayList<>();
        for (boolean valor : valors) {
            this.valor.add(new ValorBoolea(valor));
        }
    }

    public ValorConjuntBoolea(Boolean[] valors) {
        this.valor = new ArrayList<>();
        for (Boolean valor : valors) {
            this.valor.add(new ValorBoolea(valor));
        }
    }


    public ValorConjuntBoolea(String s) {
        this((Boolean[]) Arrays.stream(s.split(";")).map(Boolean::parseBoolean).toArray());
    }

    @Override
    public ValorConjuntBoolea copiar() {
        return new ValorConjuntBoolea(valor);
    }

    /**
     * @return <code>ArrayList<ValorAtribut<Boolean></code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<Boolean>> obtenirValor() {
        return valor;
    }
}
