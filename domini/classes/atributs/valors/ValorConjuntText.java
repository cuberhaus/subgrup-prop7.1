package domini.classes.atributs.valors;

import java.util.ArrayList;

/**
 * Representa el valor compost textual d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntText extends ValorConjunt<String> {

    /**
     * Constructor amb el conjunt textual.
     * @param valor <code>ArrayList<ValorAtribut<String>></code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntText(ArrayList<ValorAtribut<String>> valor) {
        this.valor = valor;
    }

    /**
     * @return <code>ArrayList<ValorAtribut<String>></code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<String>> getValor() {
        return valor;
    }
}
