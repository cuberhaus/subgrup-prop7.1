package domini.classes.atributs.valors;

import java.util.ArrayList;

/**
 * Representa el valor compost booleà d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntBoolea extends ValorConjunt<Boolean> {

    /**
     * Constructor amb el conjunt booleà.
     * @param valor <code>ArrayList<ValorAtribut<Boolean>></code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntBoolea(ArrayList<ValorAtribut<Boolean>> valor) {
        this.valor = valor;
    }

    /**
     * @return <code>ArrayList<ValorAtribut<Boolean>></code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<Boolean>> getValor() {
        return valor;
    }
}
