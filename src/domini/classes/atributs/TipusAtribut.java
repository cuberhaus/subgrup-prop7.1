package domini.classes.atributs;

import domini.classes.atributs.distancia.Distancia;
import domini.classes.atributs.valors.ValorAtribut;

/**
 * Representa el tipus d'un atribut.
 * @author maria.prat
 */
public class TipusAtribut {
    private final ValorAtribut<?> valorAtribut;
    private final Distancia distancia;

    public TipusAtribut(ValorAtribut<?> valorAtribut, Distancia distancia) {
        this.valorAtribut = valorAtribut;
        this.distancia = distancia;
    }

    public ValorAtribut<?> obtenirValorAtribut() {
        return valorAtribut;
    }

    public Distancia obtenirDistancia() {
        return distancia;
    }

    public TipusAtribut copy() {
        return new TipusAtribut(valorAtribut.copy(), distancia.copy());
    }
}
