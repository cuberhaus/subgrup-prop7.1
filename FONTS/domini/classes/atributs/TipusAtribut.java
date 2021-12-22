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
     * @throws DistanciaNoCompatibleAmbValorException el valor atribut i la distancia no son compatibles.
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


    /**
     * @param valorAtribut Identificador de un ValorAtribut
     * @return la subclasse de ValorAtribut amb nom l'identificador o null si no existeix.
     */
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
    /**
     * @param distancia Identificador de una Distancia
     * @return la subclasse de Distancia amb nom l'identificador o null si no existeix.
     */
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
        try {
            return new TipusAtribut(valorAtribut.copiar(), distancia.copiar());
        } catch (DistanciaNoCompatibleAmbValorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
