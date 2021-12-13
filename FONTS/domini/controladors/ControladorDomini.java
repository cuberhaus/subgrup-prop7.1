package domini.controladors;

import persistencia.classes.EscriptorDeCSV;
import persistencia.classes.LectorDeCSV;

import java.io.IOException;
import java.util.ArrayList;

/** Classe que representa el controlador de domini - gestor de disc */
public class ControladorDomini {
    public ControladorDomini() {}

    /**
     * Funció que donada la ubicació de l'arxiu CSV, et retorna el contingut en forma de llista.
     * @param ubicacio <code>String</code> que conté la ubicació de l'arxiu.
     * @return <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> del contingut del fitxer CSV
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> llegirCSV(String ubicacio) throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        return lector.llegirCSV(ubicacio);
    }

    /**
     * Funció que donada la ubicació on es vol escriure el CSV i el seu contingut, escriu el fitxer.
     * @param ubicacio ubicacio <code>String</code> que conté el destí de les dades.
     * @param taula <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> del contingut del fitxer CSV a escriure.
     * @throws IOException
     */
    public void escriptorCSV(String ubicacio, ArrayList<ArrayList<String>> taula) throws IOException {
        EscriptorDeCSV escriptor = new EscriptorDeCSV();
        escriptor.escriureCSV(ubicacio, taula);
    }

    /**
     * Obté l'id de l'usuari que ha iniciat la sessió
     * @return retorna 0 en cas que no hi hagi sessió iniciada, altrament retorna l'id de l'usuari
     */
    public int obtenirSessio() {
        // TODO: add logic
        return 0;
    }

    /**
     * Inicia sessió amb l'usuari de id idSessio si la contrasenya és correcte
     * @param idSessio id de l'usuari que inicia la sessió
     * @param contrasenya contrasenya de l'usuari
     */
    public void iniciarSessio(int idSessio, String contrasenya) {
        // TODO: add logic
    }

    public boolean existeixUsuari(int id) {
        //TODO:
        return false;
    }

    /**
     * Afegeix un Usuari, falta donar un paràmetre del conjunt on es vol afegir aquest usuari
     * @param id
     * @param contrasenya
     * @param nom
     */
    public void afegirUsuari(String id, String contrasenya, String nom) {
        //TODO:
    }

    /**
     * Esborra usuari, falta parametre del conjunt
     * @param id
     */
    public void esborrarUsuari(String id) {
        //TODO:
    }

    /**
     * Tanca la sessio de programa
     */
    public void tancarSessio() {
        //TODO:
    }

    public void afegirValoracio(String usuariId, String itemId, String valor) {
        //TODO:
    }

    public boolean existeixValoracio(String usuariId, String itemId) {
        //TODO:
        return false;
    }

    public void esborraValoracio(String usuariId, String itemId) {
        //TODO:
    }

    public void editarValoracio(String usuariId, String itemId, String valor) {
        //TODO:
    }

    public void carregaConjuntValoracions(String pathAbsolut) {
        //TODO:
    }

    public String[] obtenirLlistaConjunts() {
        return new String[0];
    }
}
