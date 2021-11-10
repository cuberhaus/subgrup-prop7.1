package Domini;

import java.util.ArrayList;

/**
 * Representa el valor compost d'un atribut.
 * @author maria.prat
 */
public abstract class ValorConjunt<T> extends ValorAtribut<ArrayList<ValorAtribut<T>>> {
    @Override
    public abstract ArrayList<ValorAtribut<T>> getValor();
}
