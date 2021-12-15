package persistencia.controladors;

import domini.controladors.ControladorDomini;

import java.util.ArrayList;

public class ControladorPersistencia {

    private static ControladorPersistencia instancia;
    private ControladorPersistencia() {}
    public static ControladorPersistencia obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorPersistencia();
        }
        return instancia;
    }
    public ArrayList<String> obtenirNomsConjunts() {
        return null;
    }

    public void guardarConjuntItems(ArrayList<ArrayList<String>> conjunt, String nom) {
    }
    public ArrayList<String> obtenirConjuntItems(String nom) {
        return null;
    }
    public void borrarConjuntItems(String nom) {
    }


    public void guardarConjuntUsuaris(ArrayList<ArrayList<String>> conjunt, String nom) {
    }
    public ArrayList<String> obtenirConjuntUsuaris(String nom) {
        return null;
    }
    public void borrarConjuntUsuaris(String nom) {
    }

    public void guardarConjuntValoracions(ArrayList<ArrayList<String>> conjunt, String nom) {
    }
    public ArrayList<String> obtenirConjuntValoracions(String nom) {
        return null;
    }
    public void borrarConjuntValoracions(String nom) {
    }

}
