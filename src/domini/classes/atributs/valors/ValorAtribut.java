package domini.classes.atributs.valors;

import java.util.Objects;

/**
 * Representa el valor d'un atribut.
 * @author maria.prat
 */
public abstract class ValorAtribut<T> {
    protected T valor;

    public abstract T obtenirValor();

    public abstract ValorAtribut<?> copiar();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValorAtribut<?> that = (ValorAtribut<?>) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
