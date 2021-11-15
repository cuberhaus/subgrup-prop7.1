package domini.classes.atributs.tipus;

import domini.classes.atributs.valors.ValorAtribut;

/**
 * Representa un tipus d'atribut.
 * @author maria.prat
 */
public abstract class TipusAtribut {
    /**
     * @return <code>TipusAtribut</code> que és una còpia del <code>TipusAtribut</code> actual.
     */
    public abstract TipusAtribut copy();

    public abstract boolean admetValorAtribut(ValorAtribut<?> valorAtribut);

    /**
     * @param valor1 <code>ValorAtribut</code> que conté el valor del primer atribut.
     * @param valor2 <code>ValorAtribut</code> que conté el valor del segon atribut.
     * @return <code>double</code> que conté el valor de la distància entre els dos valors donats.
     */
    public abstract double obtenirDistancia(ValorAtribut<?> valor1, ValorAtribut<?> valor2);
}

