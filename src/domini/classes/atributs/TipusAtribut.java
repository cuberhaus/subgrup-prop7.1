package domini.classes.atributs;

import domini.classes.atributs.distancia.Distancia;
import domini.classes.atributs.valors.ValorAtribut;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipusAtribut that = (TipusAtribut) o;
        return valorAtribut.equals(that.valorAtribut) && distancia.equals(that.distancia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valorAtribut, distancia);
    }

    public ValorAtribut<?> obtenirValorAtribut() {
        return valorAtribut;
    }

    public Distancia obtenirDistancia() {
        return distancia;
    }

    public TipusAtribut copy() {
        return new TipusAtribut(valorAtribut.copiar(), distancia.copy());
    }
}
