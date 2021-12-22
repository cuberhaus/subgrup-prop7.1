package domini.classes.atributs.valors;

import java.util.Objects;

/**
 * Representa el valor d'un atribut.
 * @author maria.prat
 */
public abstract class ValorAtribut<T> {
    protected T valor;

    /**
     * @return Còpia del ValorAtribut.
     */
    public abstract ValorAtribut<?> copiar();

    /**
     * @return el nom de la subclasse de l'instància.
     */
    public abstract String obteNomValor();

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

    /**
     * @return Valor del ValorAtribut.
     */
    public abstract T obtenirValor();

}
