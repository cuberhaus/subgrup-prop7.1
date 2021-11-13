package Domini;
/**
 * Representa un usuari.
 * @author pol.casacuberta
 */

public class Usuari {
    private Id id;
    private String nom;
    private String contrasenya;

    public Usuari(Id id, String nom, String contrasenya) {
        this.id = id;
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

    /**
     * Indica si dos usuaris són iguals.
     *
     * @return El resultat retorna true si són iguals, altrament retorna false.
     */
    @Override
    public boolean equals(Object obj) {
        Usuari usuari = (Usuari)obj;
        if(this.id.equals(usuari.id))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int prime1 = 29;
        int prime2 = 17;

        int hash1, hash2, hash3;
        // Comprovem que els atributs no siguin nulls
        if (id == null) hash1 = 0;
        else hash1 = id.hashCode();
//        if (nom == null) hash2 = 0;
//        else hash2 = nom.hashCode();
//        if (contrasenya == null) hash3 = 0;
//        else hash3 = contrasenya.hashCode();

        int hash = prime1;
        hash = hash * prime2 + hash1;
//        hash = hash * prime2 + hash2;
//        hash = hash * prime2 + hash3;
        return hash;
    }

    /**
     * Consultora del nom
     * @return El resultat és el valor del P.I.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modificadora del paràmetre nom
     * @param  nom El paràmetre "nom" pren el nou valor
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Consultora del id
     * @return El resultat és el valor del P.I.
     */
    public int getId() {
        return id.getValor();
    }


    public void setId(Id id) {
        this.id = id;
    }

    /**
     * Modificadora del paràmetre contrasenya
     * @param  contrasenya El paràmetre contrasenya pren el nou valor
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

}

