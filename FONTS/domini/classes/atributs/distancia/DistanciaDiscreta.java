package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.*;

/**
 * Representa la distància discreta entre dos atributs simples (booleans, categòrics, numèrics o textuals).
 * @author maria.prat
 */
public class DistanciaDiscreta extends Distancia {
    /**
     * @return Còpia de la Distància.
     */
    @Override
    public Distancia copiar() {
        return new DistanciaDiscreta();
    }

    @Override
    public String obteNomDistancia() {
        return "DistanciaDiscreta";
    }
    /**
     * @param valorAtribut Valor d'un atribut
     * @return Cert si la distància admet el valor donat. Altrament, fals.
     */
    @Override
    public boolean admet(ValorAtribut<?> valorAtribut) throws IllegalArgumentException {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return valorAtribut instanceof ValorBoolea || valorAtribut instanceof ValorCategoric ||
                valorAtribut instanceof ValorNumeric || valorAtribut instanceof ValorTextual;
    }

    /**
     * @param valor1 Valor del primer atribut
     * @param valor2 Valor del segon atribut
     * @return <code>double</code> que conté el valor de la distància entre els dos valors donats.
     * @throws IllegalArgumentException si els dos valors són de subclasses diferents o si la distància no els admet.
     */
    @Override
    public double obtenir(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admet(valor1)) {
            throw new IllegalArgumentException("La distància no admet el tipus dels ValorAtributs donats.");
        }
        if (valor1.obtenirValor().equals(valor2.obtenirValor())) {
            return 0.0d;
        } else {
            return 1.0d;
        }
    }

    /**
     * Actualitza el factor de normalització de la distància donat un nou valor.
     * @param valor Valor d'un atribut
     * @throws IllegalArgumentException si el valor donat és nul
     */
    @Override
    public void actualitzarFactorDeNormalitzacio(ValorAtribut<?> valor) {
        if (valor == null) {
            throw new IllegalArgumentException("No es pot actualitzar el factor de normalització amb un valor nul.");
        }
    }

    /**
     * El factor de normalització d'aquesta distància sempre és 1.0.
     * @return double Factor de normalització de la distància
     */
    @Override
    public double obtenirFactorDeNormalitzacio() {
        return 1.0;
    }
}
