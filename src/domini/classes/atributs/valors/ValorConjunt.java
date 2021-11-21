package domini.classes.atributs.valors;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa el valor compost d'un atribut.
 * @author maria.prat
 */
public abstract class ValorConjunt<T> extends ValorAtribut<ArrayList<ValorAtribut<T>>> {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValorConjunt<?> that = (ValorConjunt<?>) o;
        if (this.obtenirValor() == null && that.obtenirValor() == null) {
            return true;
        }
        if (this.obtenirValor() == null || that.obtenirValor() == null) {
            return false;
        }
        if (this.obtenirValor().size() != that.obtenirValor().size()) {
            return false;
        }
        for (int i = 0; i < obtenirValor().size(); ++i) {
            if (!this.obtenirValor().get(i).equals(that.obtenirValor().get(i))) {
                return false;
            }
        }
        return true;
    }
}
