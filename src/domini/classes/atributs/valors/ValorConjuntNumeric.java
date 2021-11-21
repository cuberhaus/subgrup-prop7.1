package domini.classes.atributs.valors;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Representa el valor compost numèric d'un atribut.
 * @author maria.prat
 */
public class ValorConjuntNumeric extends ValorConjunt<Double> {

    public ValorConjuntNumeric() {
        this.valor = null;
    }

    /**
     * Constructor amb el conjunt numèric.
     * @param valor <code>ArrayList<ValorAtribut<Double></code> que conté el valor d'aquest atribut.
     */
    public ValorConjuntNumeric(ArrayList<ValorAtribut<Double>> valor) {
        this.valor = valor;
    }

    /**
     * Constructor amb un conjunt de <code>doubles</code>.
     * @param valors <code>double[]</code> que conté els valors que s'assignaran a aquest atribut.
     */
    public ValorConjuntNumeric(double[] valors) {
        if (valors == null) {
            this.valor = null;
        } else {
            this.valor = new ArrayList<>();
            for (double valor : valors) {
                this.valor.add(new ValorNumeric(valor));
            }
        }
    }

    public ValorConjuntNumeric(String s) {
        if (s == null) {
            this.valor = null;
        } else if (s.isEmpty()){
            this.valor = new ArrayList<>();
        } else {
            this.valor = new ArrayList<>();
            for (String valor : s.split(";")) {
                if (valor.isEmpty()) {
                    this.valor.add(new ValorNumeric());
                } else {
                    this.valor.add(new ValorNumeric(Double.parseDouble(valor)));
                }
            }
        }
    }

    @Override
    public ValorConjuntNumeric copiar() {
        return new ValorConjuntNumeric(valor);
    }

    /**
     * @return <code>ArrayList<ValorAtribut<Double></code> que conté el valor d'aquest atribut.
     */
    @Override
    public ArrayList<ValorAtribut<Double>> obtenirValor() {
        return valor;
    }

}
