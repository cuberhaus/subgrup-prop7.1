package Dominio;
/**
 * Representa una valoració.
 * @author pol.casacuberta
 */

public class valoracio {
    private double valor;

    /**
     * Modificadora del paràmetre valor
     * @param  contrasenya El paràmetre valor pren el nou valor
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
