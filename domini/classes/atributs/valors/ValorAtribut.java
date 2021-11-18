package domini.classes.atributs.valors;

import java.util.Objects;

/**
 * Representa el valor d'un atribut.
 * @author maria.prat
 */
public abstract class ValorAtribut<T> {
    protected T valor;

    public abstract T getValor();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValorAtribut<?> that = (ValorAtribut<?>) o;
        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    public abstract ValorAtribut<?> copy();
}
