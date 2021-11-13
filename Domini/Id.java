package Domini;

public class Id {
    final int valor;
    boolean actiu;

    public Id(int valor, boolean actiu) {
        this.valor = valor;
        this.actiu = actiu;
    }

    public int getValor() {
        return valor;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }

    /**
     * Indica si dos id's són iguals
     *
     * @return El resultat retorna true si són iguals, altrament retorna false.
     */
    @Override
    public boolean equals(Object obj) {
        Id id = (Id)obj;
        if(this.valor == id.valor)
            return true;
        else
            return false;
    }

    /**
     * Calcula un codi de hash idèntic per a les valoracions amb el mateix valor,
     * altrament retorna un hash diferent.
     * @return El resultat retorna true si són iguals, altrament retorna false.
     */
    @Override
    public int hashCode() {
        return valor;
    }
}
