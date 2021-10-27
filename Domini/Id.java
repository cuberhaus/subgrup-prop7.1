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
}
