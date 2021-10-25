package Domini;

/**
 * Representa un tipus d'atribut.
 * @author maria.prat
 */
public abstract class TipusAtribut {
    /**
     * @return <code>TipusAtribut</code> que és una còpia del <code>TipusAtribut</code> actual.
     */
    public abstract TipusAtribut copy();

    /**
     * @param valor1 <code>ValorAtribut</code> que conté el valor del primer atribut.
     * @param valor2 <code>ValorAtribut</code> que conté el valor del segon atribut.
     * @return <code>Double</code> que conté el valor de la distància entre els dos valors donats.
     */
    public abstract double obtenirDistancia(ValorAtribut valor1, ValorAtribut valor2);

}

