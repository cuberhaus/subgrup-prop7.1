package persistencia.controladors;

import persistencia.classes.EscriptorDeCSV;
import persistencia.classes.LectorDeCSV;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * Controlador per la capa de Persistència.
 * @author edgar.moreno and pablo.vega
 */
public class ControladorPersistencia {

    private final Path direccioCarpetaItems = Paths.get("..", "EXE", "dades", "items");
    private final Path direccioCarpetaUsuaris = Paths.get("..", "EXE", "dades", "usuaris");
    private final Path direccioCarpetaValoracions = Paths.get("..", "EXE", "dades", "valoracions");
    private final Path direccioManual = Paths.get("..", "EXE", "dades", "ManualDUsuari.pdf");

    private static ControladorPersistencia instancia;
    private final EscriptorDeCSV escriptor;
    private final LectorDeCSV lector;

    private ControladorPersistencia() throws IOException {
        escriptor = new EscriptorDeCSV();
        lector = new LectorDeCSV();

        if (!direccioCarpetaItems.toFile().exists()) {
            Files.createDirectory(direccioCarpetaItems);
        }
        if (!direccioCarpetaUsuaris.toFile().exists()) {
            Files.createDirectory(direccioCarpetaUsuaris);
        }
        if (!direccioCarpetaValoracions.toFile().exists()) {
            Files.createDirectory(direccioCarpetaValoracions);
        }
    }

    private boolean borraDirectori(File directoriABorrar) {
        File[] contingut = directoriABorrar.listFiles();
        if (contingut != null) {
            for (File file : contingut) {
                borraDirectori(file);
            }
        }
        return directoriABorrar.delete();
    }

    public static ControladorPersistencia obtenirInstancia() throws IOException {
        if (instancia == null) {
            instancia = new ControladorPersistencia();
        }
        return instancia;
    }

    public ArrayList<String> obtenirNomsDeTotsElsTipusItems() {
        File carpetaItems = direccioCarpetaItems.toFile();
        File[] fitxersItems = carpetaItems.listFiles();
        ArrayList<String> noms = new ArrayList<>();
        if (fitxersItems != null) {
            for (File fitxersItem : fitxersItems) {
                String nom = fitxersItem.getName();
                noms.add(nom);
            }
        }
        return noms;
    }

    private Path obteCarpetaTipusItem(String nom) {
        return Paths.get(direccioCarpetaItems.toString(), nom);
    }

    private Path obteRutaDefinicioTipusItem(String nom) {
        return Paths.get(direccioCarpetaItems.toString(),nom, "definicio.csv");
    }

    public void guardarTipusItem(ArrayList<ArrayList<String>> tipusItem, String nom) throws IOException {
        Path path = obteCarpetaTipusItem(nom);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        else if (Files.exists(obteRutaDefinicioTipusItem(nom))) {
            obteRutaDefinicioTipusItem(nom).toFile().delete();
        }
        escriptor.escriureCSV(obteRutaDefinicioTipusItem(nom).toString(), tipusItem);
    }

    public ArrayList<ArrayList<String>> obtenirTipusItem(String nom) throws IOException {
        if(!Files.exists(obteRutaDefinicioTipusItem(nom)))
            return null;
        return lector.llegirCSV(obteRutaDefinicioTipusItem(nom).toString());
    }

    public void borrarTipusItem(String nom) {
        if (Files.exists(obteCarpetaTipusItem(nom))) {
            borraDirectori(obteCarpetaTipusItem(nom).toFile());
        }
    }

    public ArrayList<String> obtenirConjuntsItem(String tipusItem) {
        File carpetaConjunts = obteCarpetaTipusItem(tipusItem).toFile();
        File[] fitxersItems = carpetaConjunts.listFiles();
        ArrayList<String> noms = new ArrayList<>();
        if (fitxersItems != null) {
            for (File fitxersItem : fitxersItems) {
                String nom = fitxersItem.getName();
                if (!nom.equals("capsalera.csv")) {
                    String[] nomSeparat = nom.split("\\.");
                    noms.add(nomSeparat[0]);
                }
            }
        }
        return noms;
    }

    public void guardarConjuntItems(ArrayList<ArrayList<String>> conjunt, String tipusItem, String nom) throws IOException {
        nom += ".csv";
        Path path = Paths.get(obteCarpetaTipusItem(tipusItem).toString(), nom);
        if (Files.exists(path)) {
            path.toFile().delete();
        }
        escriptor.escriureCSV(path.toString(), conjunt);
    }
    public ArrayList<ArrayList<String>> obtenirConjuntItems(String tipusItem, String nom) throws IOException {
        nom += ".csv";
        if (!Files.exists(Paths.get(obteCarpetaTipusItem(tipusItem).toString(), nom))) {
            return new ArrayList<>();
        }
        return lector.llegirCSV(Paths.get(obteCarpetaTipusItem(tipusItem).toString(), nom).toString());
    }
    public void borrarConjuntItems( String tipusItem, String nom) throws IOException {
        nom += ".csv";
        if (Files.exists(Paths.get(obteCarpetaTipusItem(tipusItem).toString(), nom))) {
            borraDirectori(Paths.get(obteCarpetaTipusItem(tipusItem).toString(), nom).toFile());
        }
    }

    private Path obtePathConjuntUsuaris(String nom) {
        return Paths.get(direccioCarpetaUsuaris.toString(),nom+".csv");
    }

    public void guardarConjuntUsuaris(ArrayList<ArrayList<String>> conjunt, String nom) throws IOException {
        escriptor.escriureCSV(obtePathConjuntUsuaris(nom).toString(), conjunt);
    }

    public ArrayList<ArrayList<String>> obtenirConjuntUsuaris(String nom) throws IOException {
        Path path = obtePathConjuntUsuaris(nom);
        if (Files.exists(path)) {
            return lector.llegirCSV(path.toString());
        }
        return new ArrayList<>();
    }

    public void borrarConjuntUsuaris(String nom) throws IOException {
        Path conjunt = Paths.get(obtePathConjuntUsuaris(nom).toString());
        Files.delete(conjunt);
    }

    private Path obtePathConjuntValoracions(String nom) {
        return Paths.get(direccioCarpetaValoracions.toString(),nom+".csv");
    }
    public void guardarConjuntValoracions(ArrayList<ArrayList<String>> conjunt, String nom) throws IOException {
        Path path = obtePathConjuntValoracions(nom);
        if (Files.exists(path)) {
            path.toFile().delete();
        }
        escriptor.escriureCSV(path.toString(), conjunt);
    }
    public ArrayList<ArrayList<String>> obtenirConjuntValoracions(String nom) throws IOException {
        Path path = obtePathConjuntValoracions(nom);
        if (Files.exists(path)) {
            return lector.llegirCSV(path.toString());
        }
        return new ArrayList<>();
    }
    public void borrarConjuntValoracions(String nom) throws IOException {
        Path conjunt = Paths.get(obtePathConjuntValoracions(nom).toString());
        Files.delete(conjunt);
    }

    public ArrayList<ArrayList<String>> llegirCSVQualsevol(String ubicacio) throws IOException {
        return lector.llegirCSV(ubicacio);
    }

    public void escriureCSVQualsevol(String ubicacio, ArrayList<ArrayList<String>> taula) throws IOException {
        escriptor.escriureCSV(ubicacio, taula);
    }

    public void escriureCSVQualsevol(String ubicacio, ArrayList<ArrayList<String>> taula, String nom) throws IOException {
        escriptor.escriureCSV(ubicacio, taula, nom);
    }

    public void obreManual() throws IOException {
        File myFile = direccioManual.toFile();
        Desktop.getDesktop().open(myFile);
    }
}
