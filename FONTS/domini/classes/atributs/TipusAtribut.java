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
        switch (valorAtribut) {
            case "ValorBoolea":
                return new ValorBoolea();
            case "ValorCategoric":
                return new ValorCategoric();
            case "ValorNumeric":
                return new ValorNumeric();
            case "ValorTextual":
                return new ValorTextual();
            case "ValorConjuntBoolea":
                return new ValorConjuntBoolea();
            case "ValorConjuntCategoric":
                return new ValorConjuntCategoric();
            case "ValorConjuntNumeric":
                return new ValorConjuntNumeric();
            case "ValorConjuntTextual":
                return new ValorConjuntTextual();
            default:
                return null;
        }
    }

    private static Distancia distanciaDesDelNom(String distancia) {
        switch (distancia) {
            case "DistanciaDiferenciaDeConjunts":
                return new DistanciaDiferenciaDeConjunts();
            case "DistanciaDiscreta":
                return new DistanciaDiscreta();
            case "DistanciaEuclidiana":
                return new DistanciaEuclidiana();
            case "DistanciaLevenshtein":
                return new DistanciaLevenshtein();
            case "DistanciaZero":
                return new DistanciaZero();
            default:
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
