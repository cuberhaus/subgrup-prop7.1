package Domini;
/**
 * Representa una valoració.
 * @author pol.casacuberta
 */

public class Valoracio {
    private double valor;

    /**
     * Modificadora del paràmetre valor
     * @param  valor El paràmetre valor pren el nou valor
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
    /**
     * Consultora del valor
     * @return El resultat és el valor del P.I.
     */
    public double getValor() {
        return valor;
    }
}
