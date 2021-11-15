package domini.classes;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Representa un usuari.
 * @author pol.casacuberta
 */
// TODO: afegir valoracio
public class Usuari {
    private final Id id;
    private String nom;
    private String contrasenya;
    private Map<Item,Valoracio> valoracions;
//    private ConjuntUsuari conjuntUsuari;

    /** Constructora donat un id, un estat "actiu", un nom i una contrasenya.
     * @param id representa l'id de l'usuari
     * @param actiu representa si l'usuari està actiu o no.
     * @param nom representa el nom de l'usuari.
     * @param contrasenya representa la contrasenya de l'usuari.
     */
    public Usuari(int id, boolean actiu, String nom, String contrasenya) {
        this.id = new Id(id,actiu);
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

    /** Constructora donat un id, un nom i una contrasenya.
     * @param id representa l'id de l'usuari
     * @param nom representa el nom de l'usuari.
     * @param contrasenya representa la contrasenya de l'usuari.
     */
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
        return this.id.equals(usuari.id);
    }

    /**
     * Calcula un codi de hash idèntic per als usuaris amb el mateix id,
     * altrament retorna un hash diferent.
     * @return El resultat retorna true si són iguals, altrament retorna false.
     */
    @Override
    public int hashCode() {
        int prime1 = 29;
        int prime2 = 17;
        int hash1;
        // Comprovem que els atributs no siguin nulls
        if (id == null) {
            hash1 = 0;
        }
        else {
            hash1 = id.hashCode();
        }
        int hash = prime1;
        hash = hash * prime2 + hash1;
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

//    public void setId(Id id) {
//        this.id = id;
//    }

    /**
     * Modificadora del paràmetre "actiu"
     * @param  actiu El paràmetre actiu pren el nou valor
     */
    public void setActiu(boolean actiu) {
        id.setActiu(actiu);
    }

    /**
     * Consultora de "actiu"
     * @return El resultat és el valor del P.I.
     */
    public boolean isActiu() {
        return id.isActiu();
    }

    /**
     * Modificadora del paràmetre contrasenya
     * @param  contrasenya El paràmetre contrasenya pren el nou valor
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    /**
     * Afegeix una valoració al conjunt de valoracions.
     * @param  valoracio el paràmetre s'ha afegit  al conjunt de valoracions si no hi era abans.
     */
    public boolean afegirValoracio(Valoracio valoracio) throws IllegalArgumentException {
        if (!this.equals(valoracio.getUsuari())) {
            throw new IllegalArgumentException("No es pot afegir a un usuari una valoració d'un altre usuari.");
        }
        if (!valoracions.containsKey(valoracio.getItem())) {
            return false;
        }
        valoracions.put(valoracio.getItem(),valoracio);
        return true;
    }

    /**
     * Esborra una valoració del conjunt de valoracions.
     * @param  item la valoració amb l'item s'ha esborrat del conjunt.
     */
    public boolean esborraValoracio(Item item) {
        if (!valoracions.containsKey(item)) {
            return false;
        }
        valoracions.remove(item);
        return true;
    }

    /**
     * Retorna true si la contrasenya passada com a paràmetre es igual a la del P.I.
     * @param  contrasenya El paràmetre contrasenya pren el nou valor.
     */
    public boolean isContrasenya(String contrasenya) {
        return Objects.equals(this.contrasenya, contrasenya);
    }
}

