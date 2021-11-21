package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.ValorAtribut;

/**
 * Representa una funció de distància entre dos atributs.
 * @author maria.prat
 */
public abstract class Distancia {
    /**
     * @return <code>Distancia</code> que és una còpia del <code>Distancia</code> actual.
     */
    public abstract Distancia copiar();

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass();
    }

    public abstract boolean admet(ValorAtribut<?> valorAtribut);

    /**
     * @param valor1 <code>ValorAtribut</code> que conté el valor del primer atribut.
     * @param valor2 <code>ValorAtribut</code> que conté el valor del segon atribut.
     * @return <code>double</code> que conté el valor de la distància entre els dos valors donats.
     */
    public abstract double obtenir(ValorAtribut<?> valor1, ValorAtribut<?> valor2);

    public abstract void actualitzarFactorDeNormalitzacio(ValorAtribut<?> valor);

    public abstract double obtenirFactorDeNormalitzacio();
}

