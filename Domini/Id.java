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

    @Override
    public boolean equals(Object obj) {
        Id id = (Id)obj;
        if(this.valor == id.valor)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return valor;
    }
}
