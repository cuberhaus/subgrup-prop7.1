package domini.classes;

import java.util.Objects;

/**
 * Representa un identificador d'un element.
 * @author maria.prat
 */
public class Id implements Comparable<Id> {
    /**
     * Valor de l'identificador.
     */
    final int valor;
    /**
     * Diu si és un identificador actiu o no ho és.
     */
    private boolean actiu;

    /**
     * Constructor d'un Id.
     * @param valor Valor de l'identificador.
     * @param actiu Booleà que diu si l'identificador està actiu o no ho està.
     */
    public Id(int valor, boolean actiu) {
        this.valor = valor;
        this.actiu = actiu;
    }

    /**
     * Constructor d'un Id, es fica actiu.
     * @param valor Valor de l'identificador.
     */
    public Id(int valor) {
        this.valor = valor;
        this.actiu = true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        // Dos Ids són iguals si tenen el mateix 'valor'.
        return valor == id.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    /**
     * @return Còpia de l'identificador.
     */
    public Id copiar() {
        return new Id(valor, actiu);
    }

    @Override
    public int compareTo(Id o) {
        return Integer.compare(this.valor, o.valor);
    }

    /**
     * @return Valor de l'identificador.
     */
    public int obtenirValor() {
        return valor;
    }

    /**
     * @return Si l'identificador està actiu o no ho està.
     */
    public boolean esActiu() {
        return actiu;
    }

    /**
     * @param actiu <code>boolean</code> que s'assignarà a l'atribut 'actiu'.
     */
    public void assignarActiu(boolean actiu) {
        this.actiu = actiu;
    }
}
