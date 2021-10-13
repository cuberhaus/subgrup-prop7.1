public class Usuari {
    private int id;
    private String nom;
    private String contrasenya;

    public Usuari(int id, String nom, String contrasenya) {
        this.id = id;
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public void setNomUsuari(String nom) {
        this.nom = nom;
    }

    public void setIdUsuari(int id) {
        this.id = id;
    }
}

