package domini.classes.atributs;

import domini.classes.atributs.distancia.*;
import domini.classes.atributs.valors.*;
import excepcions.DistanciaNoCompatibleAmbValorException;
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
    public TipusAtribut(ValorAtribut<?> valorAtribut, Distancia distancia) throws DistanciaNoCompatibleAmbValorException {
        this.valorAtribut = valorAtribut;
        this.distancia = distancia;
        if (!distancia.admet(valorAtribut)) {
            throw new DistanciaNoCompatibleAmbValorException("El ValorAtribut i la Distancia donats no són compatibles");
        }
    }

    public TipusAtribut(String valor, String distancia) throws NomInternIncorrecteException, DistanciaNoCompatibleAmbValorException {
        this.valorAtribut = valorAtributDesDelNom(valor);
        this.distancia = distanciaDesDelNom(distancia);
        if (this.valorAtribut == null || this.distancia == null) {
            throw new NomInternIncorrecteException("El ValorAtribut o Distancia demanats no existeixen.");
        }
        if (!this.distancia.admet(this.valorAtribut)) {
            throw new DistanciaNoCompatibleAmbValorException("El ValorAtribut i la Distancia donats no són compatibles");
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
        return switch (valorAtribut) {
            case "ValorBoolea" -> new ValorBoolea();
            case "ValorCategoric" -> new ValorCategoric();
            case "ValorNumeric" -> new ValorNumeric();
            case "ValorTextual" -> new ValorTextual();
            case "ValorConjuntBoolea" -> new ValorConjuntBoolea();
            case "ValorConjuntCategoric" -> new ValorConjuntCategoric();
            case "ValorConjuntNumeric" -> new ValorConjuntNumeric();
            case "ValorConjuntTextual" -> new ValorConjuntTextual();
            default -> null;
        };
    }

    private static Distancia distanciaDesDelNom(String distancia) {
        return switch (distancia) {
            case "DistanciaDiferenciaDeConjunts" -> new DistanciaDiferenciaDeConjunts();
            case "DistanciaDiscreta" -> new DistanciaDiscreta();
            case "DistanciaEuclidiana" -> new DistanciaEuclidiana();
            case "DistanciaLevenshtein" -> new DistanciaLevenshtein();
            case "DistanciaZero" -> new DistanciaZero();
            default -> null;
        };
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
        try {
            return new TipusAtribut(valorAtribut.copiar(), distancia.copiar());
        } catch (DistanciaNoCompatibleAmbValorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
