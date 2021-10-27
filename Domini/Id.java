package Domini;

public class Id {
    final int id;
    boolean actiu;

    public Id(int id, boolean actiu) {
        this.id = id;
        this.actiu = actiu;
    }

    public int getId() {
        return id;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
}
