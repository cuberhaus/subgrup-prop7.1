import domini.classes.Id;
import domini.classes.TipusItem;
import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.distancia.*;
import domini.classes.atributs.valors.*;
import libs.consola;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * @author Tots.
 */
public class UtilitatsDeLectura {

    private static String obtenirRutaORutaPerDefecte(String nomObjecte, String nomObjectePerDefecte) {
        String ruta = consola.obtenirString("Introdueix ruta a " + nomObjecte + " (per defecte - " + nomObjectePerDefecte + "): ");
        if (ruta.isEmpty()) ruta = nomObjectePerDefecte;
        return "dades_tests/" + ruta;
    }

    public static Id llegirId() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("Id", "Id1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            int valor = Integer.parseInt(fitxer.readLine());
            boolean actiu = Boolean.parseBoolean(fitxer.readLine());
            return new Id(valor, actiu);
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Fitxer invàlid.");
        }
    }

    public static TipusItem llegirTipusItem() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("TipusItem", "TipusItem1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            String nom = fitxer.readLine();
            int numTipusAtributs = Integer.parseInt(fitxer.readLine());
            Map<String, TipusAtribut> tipusAtributs = new TreeMap<>();
            for (int i = 0; i < numTipusAtributs; ++i) {
                String nomTipusAtribut = fitxer.readLine();
                String valorAtribut = fitxer.readLine();
                String distancia = fitxer.readLine();
                tipusAtributs.put(nomTipusAtribut, new TipusAtribut(valorAtributDesDelNom(valorAtribut),
                        distanciaDesDelNom(distancia)));
            }
            return new TipusItem(nom, tipusAtributs);
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Fitxer invàlid.");
        }
    }

    private static ValorAtribut<?> valorAtributDesDelNom(String valorAtribut) {
        if (Objects.equals(valorAtribut, "ValorBoolea")) {
            return new ValorBoolea();
        } else if (Objects.equals(valorAtribut, "ValorCategoric")) {
            return new ValorCategoric();
        } else if (Objects.equals(valorAtribut, "ValorNumeric")) {
            return new ValorNumeric();
        } else if (Objects.equals(valorAtribut, "ValorTextual")) {
            return new ValorTextual();
        } else if (Objects.equals(valorAtribut, "ValorConjuntBoolea")) {
            return new ValorConjuntBoolea();
        } else if (Objects.equals(valorAtribut, "ValorConjuntCategoric")) {
            return new ValorConjuntCategoric();
        } else if (Objects.equals(valorAtribut, "ValorConjuntNumeric")) {
            return new ValorConjuntNumeric();
        } else if (Objects.equals(valorAtribut, "ValorConjuntTextual")) {
            return new ValorConjuntTextual();
        } else {
            throw new IllegalArgumentException("No hi ha cap ValorAtribut reconegut amb aquest nom.");
        }
    }

    private static Distancia distanciaDesDelNom(String distancia) throws IllegalArgumentException {
        if (Objects.equals(distancia, "DistanciaDiferenciaDeConjunts")) {
            return new DistanciaDiferenciaDeConjunts();
        } else if (Objects.equals(distancia, "DistanciaDiscreta")) {
            return new DistanciaDiscreta();
        } else if (Objects.equals(distancia, "DistanciaEuclidiana")) {
            return new DistanciaEuclidiana();
        } else if (Objects.equals(distancia, "DistanciaLevenshtein")) {
            return new DistanciaLevenshtein();
        } else if (Objects.equals(distancia, "DistanciaZero")) {
            return new DistanciaZero();
        } else {
            throw new IllegalArgumentException("No hi ha cap Distancia reconeguda amb aquest nom.");
        }
    }

    public static ArrayList<String> llegirNomAtributs() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("NomAtributs", "NomAtributs1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            String noms = fitxer.readLine();
            return new ArrayList<>(Arrays.asList(noms.split(",", 0)));
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Fitxer invàlid.");
        }
    }

    public static ArrayList<String> llegirValorAtributs() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("ValorAtributs", "ValorAtributs1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            String noms = fitxer.readLine();
            return new ArrayList<>(Arrays.asList(noms.split(",", 0)));
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Fitxer invàlid.");
        }
    }
}
