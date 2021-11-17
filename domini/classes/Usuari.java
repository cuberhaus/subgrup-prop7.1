package domini.classes;

import domini.classes.metode_recomanador.Punt;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Representa un usuari.
 * @author pol.casacuberta
 */

public class Usuari {
    /** Conté l'identificador de l'usuari */
    private final Id id;

    /** Conté el nom de l'usuari */
    private String nom;

    /** Conté la contrasenya de l'usuari */
    private String contrasenya;

    /** Conté el conjunt d'items valorat per l'usuari */
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

    /** Constructora donat un id, un estat "actiu".
     * @param id representa l'id de l'usuari
     * @param actiu representa si l'usuari està actiu o no.
     */
    public Usuari(int id, boolean actiu) {
        this.id = new Id(id,actiu);
        this.nom = null;
        this.contrasenya = null;
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
     * Retorna true si s'ha afegit correctament, retorna false si ja hi era
     * @param  valoracio el paràmetre s'ha afegit al conjunt si no hi era abans.
     */
    public boolean afegirValoracio(Valoracio valoracio) throws IllegalArgumentException {
        if (valoracio == null) {
            throw new IllegalArgumentException("No es pot afegir una valoració nul·la.");
        }
        if (!this.equals(valoracio.getUsuari())) {
            throw new IllegalArgumentException("No es pot afegir a un usuari una valoració d'un altre usuari.");
        }
        if (valoracions.containsKey(valoracio.getItem())) {
            return false;
        }
        valoracions.put(valoracio.getItem(),valoracio);
        return true;
    }

    /**
     * Esborra una valoració del conjunt de valoracions.
     * Retorna true si s'ha esborrat correctament, retorna false si no hi era
     * @param  item la valoració amb l'item s'ha esborrat del conjunt, si hi era.
     */
    public boolean esborraValoracio(Item item) {
        if (item == null || !valoracions.containsKey(item)) {
            return false;
        }
        valoracions.remove(item);
        return true;
    }

    /**
     * Consultora d'una valoració feta per l'usuari a l'item passat com a parametre
     * @return El resultat és el valor del P.I.
     */
    public Valoracio obtenirValoracio(Item item) {
        return valoracions.get(item);
    }

    /**
     * Donat un conjunt d'items es retorna un punt de dimensio el cardinal del conjunt on cada coordenada correspon
     * a la valoració d'un item per l'usuari. Si l'usuari no ha valorat l'ítem es marca amb un -1.
     * @param conjuntItems Items considerats per la conversió a punt.
     * @return El <code>Punt</code> corresponent a l'usuari.
     */
    public Punt obteComPunt(ArrayList<Item> conjuntItems) {
        Punt res = new Punt();
        for (Item item : conjuntItems) {
            if (valoracions.containsKey(item)) {
                res.add(valoracions.get(item).getValor());
            } else res.add(-1.);
            // TODO: -1 per denotar que no esta vist. Potser seria millor agafar la mitjana
        }
        return res;
    }

    /**
     * Retorna true si la contrasenya passada com a paràmetre es igual a la del P.I.
     * @param  contrasenya El paràmetre contrasenya pren el nou valor.
     */
    public boolean isContrasenya(String contrasenya) {
        return Objects.equals(this.contrasenya, contrasenya);
    }
}

