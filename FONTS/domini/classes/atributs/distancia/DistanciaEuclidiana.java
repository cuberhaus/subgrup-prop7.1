package domini.classes.atributs.distancia;

import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorConjuntNumeric;
import domini.classes.atributs.valors.ValorNumeric;

/**
 * Representa la distància euclidiana entre dos atributs numèrics.
 * @author maria.prat
 */
public class DistanciaEuclidiana extends Distancia {
    /**
     * Norma màxima dels valors amb aquesta distància.
     */
    private double normaMaxima = 0.0;

    /**
     * @return Còpia de la Distància.
     */
    @Override
    public Distancia copiar() {
        return new DistanciaEuclidiana();
    }

    @Override
    public String obteNomDistancia() {
        return "DistanciaEuclidiana";
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
        return valorAtribut instanceof ValorNumeric || valorAtribut instanceof ValorConjuntNumeric;
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
        if (valor1 instanceof ValorNumeric) {
            return Math.abs(((ValorNumeric) valor1).obtenirValor() - ((ValorNumeric) valor2).obtenirValor());
        } else {
            ValorConjuntNumeric valorConjuntNumeric1 = (ValorConjuntNumeric) valor1;
            ValorConjuntNumeric valorConjuntNumeric2 = (ValorConjuntNumeric) valor2;
            if (valorConjuntNumeric1.obtenirValor().size() != valorConjuntNumeric2.obtenirValor().size()) {
                throw new IllegalArgumentException("No es pot calcular la distància euclidiana entre dos " +
                        "ValorConjuntNumerics de mides diferents");
            }
            double distancia = 0.0;
            for (int i = 0; i < valorConjuntNumeric1.obtenirValor().size(); ++i) {
                distancia += Math.pow(valorConjuntNumeric1.obtenirValor().get(i).obtenirValor()
                        - valorConjuntNumeric2.obtenirValor().get(i).obtenirValor(), 2);
            }
            return Math.sqrt(distancia);
        }
    }

    /**
     * La norma d'un ValorAtribut es defineix com la distància entre el ValorAtribut i el ValorAtribut amb valor zero.
     * @param valor Valor d'un atribut
     * @return <code>double</code> que conté la norma del ValorAtribut donat.
     * @throws IllegalArgumentException si el valor és nul o si la distància no l'admet.
     */
    public double obtenirNorma(ValorAtribut<?> valor) throws IllegalArgumentException {
        if (valor == null) {
            throw new IllegalArgumentException("No es pot obtenir la norma d'un ValorAtribut nul.");
        }
        if (!admet(valor)) {
            throw new IllegalArgumentException("La distància no admet el tipus del ValorAtribut donat.");
        }
        if (valor instanceof ValorNumeric) {
            return obtenir(new ValorNumeric(0.0), valor);
        } else {
            double[] zero = new double[((ValorConjuntNumeric)valor).obtenirValor().size()];
            return obtenir(new ValorConjuntNumeric(zero), valor);
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
        normaMaxima = Math.max(obtenirNorma(valor), normaMaxima);
    }

    /**
     * El factor de normalització d'aquesta distància és el doble de la norma màxima.
     * @return double Factor de normalització de la distància
     */
    @Override
    public double obtenirFactorDeNormalitzacio() {
        if (normaMaxima == 0.0) {
            return 1.0;
        }
        return 2.0 * normaMaxima;
    }
}
