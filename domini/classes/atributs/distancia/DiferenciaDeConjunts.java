package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorConjunt;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa la distància de diferència de conjunts entre dos atributs conjunts.
 * @author maria.prat
 */
public class DiferenciaDeConjunts extends Distancia {
    @Override
    public Distancia copy() {
        return new DiferenciaDeConjunts();
    }

    @Override
    public boolean admet(ValorAtribut<?> valorAtribut) {
        if (valorAtribut == null) {
            throw new IllegalArgumentException("No es pot comprovar si un ValorAtribut nul és admissible.");
        }
        return valorAtribut instanceof ValorConjunt;
    }

    @Override
    public double obtenir(ValorAtribut<?> valor1, ValorAtribut<?> valor2) throws IllegalArgumentException {
        if (!(valor1.getClass().equals(valor2.getClass()))) {
            throw new IllegalArgumentException("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.");
        }
        if (!admet(valor1)) {
            throw new IllegalArgumentException("La distància no admet el tipus dels ValorAtributs donats.");
        }
        Set<ValorAtribut<?>> set1 = new HashSet<>(((ValorConjunt<?>) valor1).getValor());
        Set<ValorAtribut<?>> set2 = new HashSet<>(((ValorConjunt<?>) valor2).getValor());
        Set<ValorAtribut<?>> union = new HashSet<>(set1);
        union.addAll(set2);
        Set<ValorAtribut<?>> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return union.size() - intersection.size();
    }
}