package domini.classes;

import java.util.Objects;

/**
 * Representa un identificador.
 * @author maria.prat
 */
public class Id implements Comparable<Id> {
    final int valor;
    private boolean actiu;

    public Id(int valor, boolean actiu) {
        this.valor = valor;
        this.actiu = actiu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return valor == id.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    public Id copiar() {
        return new Id(valor, actiu);
    }

    @Override
    public int compareTo(Id o) {
        return Integer.compare(this.valor, o.valor);
    }

    public int obtenirValor() {
        return valor;
    }

    public boolean esActiu() {
        return actiu;
    }

    public void assignarActiu(boolean actiu) {
        this.actiu = actiu;
    }
}
