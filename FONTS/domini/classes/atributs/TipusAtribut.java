package domini.classes.atributs;

import domini.classes.atributs.distancia.*;
import domini.classes.atributs.valors.*;
import excepcions.NomInternIncorrecteException;

import java.util.Objects;

/**
 * Representa el tipus d'un atribut.
 * @author maria.prat
 */
public class TipusAtribut {
    /**
     * Valor de l'atribut.
     */
    private final ValorAtribut<?> valorAtribut;
    /**
     * Distància de l'atribut.
     */
    private final Distancia distancia;

    /**
     * Constructor d'un TipusAtribut a partir d'un valor i una distància.
     * @param valorAtribut Valor de l'atribut
     * @param distancia Distància de l'atribut
     */
    public TipusAtribut(ValorAtribut<?> valorAtribut, Distancia distancia) {
        this.valorAtribut = valorAtribut;
        this.distancia = distancia;
    }

    public TipusAtribut(String valor, String distancia) throws NomInternIncorrecteException {
        this.valorAtribut = valorAtributDesDelNom(valor);
        this.distancia = distanciaDesDelNom(distancia);
        // TODO: crear excepcions pròpies
        if (this.valorAtribut == null || this.distancia == null) {
            throw new NomInternIncorrecteException("El valorAtribut o Distancia demanats no existeixen.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipusAtribut that = (TipusAtribut) o;
        // Dos TipusAtribut són iguals si els seus valors i distàncies són iguals.
        return valorAtribut.equals(that.valorAtribut) && distancia.equals(that.distancia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valorAtribut, distancia);
    }

    private static ValorAtribut<?> valorAtributDesDelNom(String valorAtribut) {
        if (Objects.equals(valorAtribut, "ValorBoolea")) {
            return new ValorBoolea();
        } else if (Objects.equals(valorAtribut, "ValorCategoric")) {
            return new ValorCategoric();
        } else if (Objects.equals(valorAtribut, "ValorNumeric")) {
            return new ValorNumeric();
        } else if (Objects.equals(valorAtribut, "ValorTextual")) {
            return new ValorTextual();
        } else if (Objects.equals(valorAtribut, "ValorConjuntBoolea")) {
            return new ValorConjuntBoolea();
        } else if (Objects.equals(valorAtribut, "ValorConjuntCategoric")) {
            return new ValorConjuntCategoric();
        } else if (Objects.equals(valorAtribut, "ValorConjuntNumeric")) {
            return new ValorConjuntNumeric();
        } else if (Objects.equals(valorAtribut, "ValorConjuntTextual")) {
            return new ValorConjuntTextual();
        } else {
            return null;
        }
    }

    private static Distancia distanciaDesDelNom(String distancia) {
        if (Objects.equals(distancia, "DistanciaDiferenciaDeConjunts")) {
            return new DistanciaDiferenciaDeConjunts();
        } else if (Objects.equals(distancia, "DistanciaDiscreta")) {
            return new DistanciaDiscreta();
        } else if (Objects.equals(distancia, "DistanciaEuclidiana")) {
            return new DistanciaEuclidiana();
        } else if (Objects.equals(distancia, "DistanciaLevenshtein")) {
            return new DistanciaLevenshtein();
        } else if (Objects.equals(distancia, "DistanciaZero")) {
            return new DistanciaZero();
        } else {
            return null;
        }
    }
    /**
     * @return ValorAtribut del TipusAtribut.
     */
    public ValorAtribut<?> obtenirValorAtribut() {
        return valorAtribut;
    }

    /**
     * @return Distancia del TipusAtribut.
     */
    public Distancia obtenirDistancia() {
        return distancia;
    }

    /**
     * @return Còpia profunda del TipusAtribut.
     */
    public TipusAtribut copiar() {
        return new TipusAtribut(valorAtribut.copiar(), distancia.copiar());
    }
}
