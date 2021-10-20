package Dominio;
/**
 * Representa un usuari.
 * @author pol.casacuberta
 */

public class Usuari {
    private int id;
    private String nom;
    private String contrasenya;

    public Usuari(int id, String nom, String contrasenya) {
        this.id = id;
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

    /**
     * Consultora del nom
     * @return El resultat és el valor del P.I.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Consultora del id
     * @return El resultat és el valor del P.I.
     */
    public int getId() {
        return id;
    }

    /**
     * Modificadora del paràmetre contrasenya
     * @param  contrasenya El paràmetre contrasenya pren el nou valor
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    /**
     * Modificadora del paràmetre nom
     * @param  nom El paràmetre nom pren el nou valor
     */
    public void setNomUsuari(String nom) {
        this.nom = nom;
    }

    /**
     * Modificadora del paràmetre id
     * @param  id El paràmetre id pren el nou valor
     */
    public void setIdUsuari(int id) {
        this.id = id;
    }
}

