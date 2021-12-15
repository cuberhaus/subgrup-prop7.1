package persistencia.controladors;

import persistencia.classes.EscriptorDeCSV;
import persistencia.classes.LectorDeCSV;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ControladorPersistencia {

    private final Path direccioCarpetaItems = Paths.get("..", "EXE", "dades");
    private final Path direccioCarpetaUsuaris = Paths.get("..", "EXE", "usuaris");
    private final Path direccioCarpetaValoracions = Paths.get("..", "EXE", "valoracions");
    private static ControladorPersistencia instancia;
    private final EscriptorDeCSV escriptor;
    private final LectorDeCSV lector;
    private ControladorPersistencia() {
        escriptor = new EscriptorDeCSV();
        lector = new LectorDeCSV();
    }

    public static ControladorPersistencia obtenirInstancia() {
        if (instancia == null) {
            instancia = new ControladorPersistencia();
        }
        return instancia;
    }
    public ArrayList<String> obtenirNomsConjunts() {
        File carpetaItems = direccioCarpetaItems.toFile();
        File[] fitxersItems = carpetaItems.listFiles();
        ArrayList<String> noms = new ArrayList<>();
        if (fitxersItems != null) {
            for (File fitxersItem : fitxersItems) {
                String[] nomPartit = fitxersItem.getName().split("\\.");
                noms.add(nomPartit[0]);
                System.out.println();
            }
        }
        return noms;
    }

    private String obtePathConjuntItems(String nom) {
        return direccioCarpetaItems + "/" + nom;
    }
    public void guardarConjuntItems(ArrayList<ArrayList<String>> conjunt, String nom) throws IOException {
        escriptor.escriureCSV(obtePathConjuntItems(nom), conjunt);
    }
    public ArrayList<ArrayList<String>> obtenirConjuntItems(String nom) throws IOException {
        return lector.llegirCSV(obtePathConjuntItems(nom));
    }
    public void borrarConjuntItems(String nom) throws IOException {
        Path conjunt = Paths.get(obtePathConjuntItems(nom));
        Files.delete(conjunt);
    }

    private String obtePathConjuntUsuaris(String nom) {
        return direccioCarpetaUsuaris + "/" + nom;
    }
    public void guardarConjuntUsuaris(ArrayList<ArrayList<String>> conjunt, String nom) throws IOException {
        escriptor.escriureCSV(obtePathConjuntUsuaris(nom), conjunt);
    }
    public ArrayList<ArrayList<String>> obtenirConjuntUsuaris(String nom) throws IOException {
        return lector.llegirCSV(obtePathConjuntUsuaris(nom));
    }
    public void borrarConjuntUsuaris(String nom) throws IOException {
        Path conjunt = Paths.get(obtePathConjuntUsuaris(nom));
        Files.delete(conjunt);
    }

    private String obtePathConjuntValoracions(String nom) {
        return direccioCarpetaValoracions + "/" + nom;
    }
    public void guardarConjuntValoracions(ArrayList<ArrayList<String>> conjunt, String nom) throws IOException {
        escriptor.escriureCSV(obtePathConjuntValoracions(nom), conjunt);
    }
    public ArrayList<ArrayList<String>> obtenirConjuntValoracions(String nom) throws IOException {
        return lector.llegirCSV(obtePathConjuntValoracions(nom));
    }
    public void borrarConjuntValoracions(String nom) throws IOException {
        Path conjunt = Paths.get(obtePathConjuntValoracions(nom));
        Files.delete(conjunt);
    }

}
