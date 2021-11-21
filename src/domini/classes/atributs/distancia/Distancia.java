package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.ValorAtribut;

/**
 * Representa una funció de distància entre dos atributs.
 * @author maria.prat
 */
public abstract class Distancia {
    /**
     * @return còpia de la <code>Distancia</code> actual.
     */
    public abstract Distancia copiar();

    @Override
    public boolean equals(Object obj) {
        // Dos distàncies són iguals si són instàncies de la mateixa classe.
        return getClass() == obj.getClass();
    }

    /**
     * @param valorAtribut Valor d'un atribut
     * @return Cert si la distància admet el valor donat. Altrament, fals.
     */
    public abstract boolean admet(ValorAtribut<?> valorAtribut);

    /**
     * @param valor1 Valor del primer atribut
     * @param valor2 Valor del segon atribut
     * @return <code>double</code> que conté el valor de la distància entre els dos valors donats.
     */
    public abstract double obtenir(ValorAtribut<?> valor1, ValorAtribut<?> valor2);

    /**
     * Actualitza el factor de normalització de la distància donat un nou valor.
     * @param valor Valor d'un atribut
     */
    public abstract void actualitzarFactorDeNormalitzacio(ValorAtribut<?> valor);

    /**
     * @return Retorna el factor de normalització de la distància.
     */
    public abstract double obtenirFactorDeNormalitzacio();
}

