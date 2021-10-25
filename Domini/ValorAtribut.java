package Domini;

/**
 * Representa el valor d'un atribut.
 * @author maria.prat
 */
public abstract class ValorAtribut {
    private final TipusAtribut tipusAtribut;

    /**
     * Constructor amb un <code>TipusAtribut</code>.
     * @param tipusAtribut <code>TipusAtribut</code> que conté el tipus d'atribut del <code>ValorAtribut</code>.
     */
    public ValorAtribut(TipusAtribut tipusAtribut) {
        this.tipusAtribut = tipusAtribut;
    }

    /**
     * @return Retorna una còpia del <code>TipusAtribut</code> que conté el tipus d'atribut del <code>ValorAtribut</code>.
     */
    public TipusAtribut getTipusAtribut() {
        return tipusAtribut.copy();
    }
}
