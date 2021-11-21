package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorConjunt;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa la distància de diferència de conjunts entre dos atributs conjunts.
 * Aquesta distància es defineix com la mida de la unió dels dos conjunts menys la mida de la seva intersecció.
 * @author maria.prat
 */
public class DistanciaDiferenciaDeConjunts extends Distancia {
    /**
     * Norma màxima dels valors amb aquesta distància.
     */
    private double normaMaxima = 0.0;

    /**
     * @return Còpia de la Distància.
     */
    @Override
    public Distancia copiar() {
        return new DistanciaDiferenciaDeConjunts();
    }

    /**
     * @param valorAtribut Valor d'un atribut
     * @return Cert si la distància admet el valor donat. Altrament, fals.
     */
    @Override
    public boolean admet(ValorAtribut<?> valorAtribut) {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return valorAtribut instanceof ValorConjunt;
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
        Set<ValorAtribut<?>> set1 = new HashSet<>(((ValorConjunt<?>) valor1).obtenirValor());
        Set<ValorAtribut<?>> set2 = new HashSet<>(((ValorConjunt<?>) valor2).obtenirValor());
        Set<ValorAtribut<?>> union = new HashSet<>(set1);
        union.addAll(set2);
        Set<ValorAtribut<?>> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return union.size() - intersection.size();
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
        normaMaxima = Math.max(normaMaxima, ((ValorConjunt<?>) valor).obtenirValor().size());
    }

    /**
     * El factor de normalització d'aquesta distància és el doble de la norma màxima dels valors que tenen aquesta
     * distància. Si la norma màxima és zero, el factor de normalització és 1.0.
     * @return double Factor de normalització de la distància
     */
    @Override
    public double obtenirFactorDeNormalitzacio() {
        if (normaMaxima == 0.0) {
            return 1.0;
        }
        // Valor màxim de la distància diferència de conjunts entre conjunts amb norma igual o més petita que normaMaxima.
        return 2.0 * normaMaxima;
    }
}
