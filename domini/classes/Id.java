package domini.classes;

public class Id implements Comparable<Id> {
    final int valor;
    private boolean actiu;

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
        return this.valor == id.valor;
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

    @Override
    public int compareTo(Id o) {
        return Integer.compare(this.valor, o.valor);
    }
}
