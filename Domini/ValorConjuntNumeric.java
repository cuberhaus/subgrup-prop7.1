package Domini;

import java.util.ArrayList;

/**
 * Representa el valor compost categòric d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntNumeric extends ValorConjunt<String> {

    /**
     * Constructor amb el conjunt categòric.
     * @param valor <code>ArrayList<ValorAtribut<String>></code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntNumeric(ArrayList<ValorAtribut<String>> valor) {
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
