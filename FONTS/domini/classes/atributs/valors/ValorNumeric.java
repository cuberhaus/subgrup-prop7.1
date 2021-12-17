package domini.classes.atributs.valors;

/**
 * Representa el valor numèric d'un atribut.
 * @author maria.prat
 */
public class ValorNumeric extends ValorAtribut<Double> {
    /**
     * Constructor d'un ValorNumèric amb valor nul.
     */
    public ValorNumeric() {
        this.valor = null;
    }

    /**
     * Constructor amb el valor numèric.
     * @param valor <code>Double</code> que conté el valor d'aquest atribut.
     */
    public ValorNumeric(Double valor) {
        this.valor = valor;
    }

    /**
     * @return Còpia del ValorNumeric.
     */
    @Override
    public ValorNumeric copiar() {
        return new ValorNumeric(valor);
    }

    @Override
    public String obteNomValor() {
        return "ValorNumeric";
    }

    /**
     * @return <code>Double</code> que conté el valor d'aquest atribut.
     */
    @Override
    public Double obtenirValor() {
        return valor;
    }
}
