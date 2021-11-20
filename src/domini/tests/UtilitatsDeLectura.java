import domini.classes.*;
import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.distancia.*;
import domini.classes.atributs.valors.*;
import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import domini.classes.recomanador.metode_recomanador.Recomanacio;
import libs.consola;

import java.io.*;
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

    public static Usuari llegirUsuari() throws Exception {
        Scanner input = new Scanner(System.in);
        String contrasenya, nom;
        System.out.println("Escriu el teu nom:" );
        nom = input.nextLine();
        System.out.println("Escriu la teva contrasenya:" );
        contrasenya = input.nextLine();
        Id id = llegirId();
        return new Usuari(id,nom,contrasenya);
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

    public static Item llegirItem() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("Item", "Item1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            Id id = UtilitatsDeLectura.llegirId();
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            ArrayList<String> nomAtributs = new ArrayList<>(Arrays.asList(fitxer.readLine().split(",", 0)));
            ArrayList<String> valorAtributs = new ArrayList<>(Arrays.asList(fitxer.readLine().split(",", 0)));
            Item item = new Item(id, tipusItem, nomAtributs, valorAtributs);
            Map<Usuari, Valoracio> valoracions;
            int numValoracions = Integer.parseInt(fitxer.readLine());
            for (int i = 0; i < numValoracions; ++i) {
                // TODO(maria): afegir stubs
                Usuari usuari = new Usuari(new Id(Integer.parseInt(fitxer.readLine()), true));
                new Valoracio(Double.parseDouble(fitxer.readLine()), usuari, item);
            }
            return item;
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Fitxer invàlid.");
        }
    }

    public static String llegirUbicacioArxiu() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("Ubicacio", "Ubicacio1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            return fitxer.readLine();
        } catch (FileNotFoundException e) {
            throw new Exception((e.getMessage()));
        } catch (Exception e1) {
            throw new Exception("Fitxer invalid");
        }
    }

    public static double llegirDouble() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("double", "double1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            String linia = fitxer.readLine();
            try {
                return Double.parseDouble(linia);
            } catch (NumberFormatException e1) {
                throw new Exception("No es un double");
            }

        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e1) {
            throw new Exception("Fitxer invalid");
        }
    }

    public static Recomanacio llegirRecomanacio() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("Recomanacio", "Recomanacio1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            String linia1 = fitxer.readLine();
            int numId;
            try {
                numId = Integer.parseInt(linia1);
            } catch (NumberFormatException e) {
                throw new Exception("Id no es un int");
            }

            double seguretat;
            String linia2 = fitxer.readLine();
            try {
                seguretat = Double.parseDouble(linia2);
            } catch (NumberFormatException e) {
                throw new Exception("Seguretat no es un double");
            }

            Id id = new Id(numId, true);
            return new Recomanacio(id, seguretat);
        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e1) {
            throw new Exception("Fitxer invalid");
        }
    }

    public static String llegirUbicacioDestiArxiu() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("Desti", "Desti1");
        try {
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            return fitxer.readLine();
        } catch (FileNotFoundException e) {
            throw new Exception((e.getMessage()));
        } catch (Exception e1) {
            throw new Exception("Fitxer invalid");
        }
    }

    public static TreeMap<Id, Item> llegirMapItems() throws Exception {
        String ruta = obtenirRutaORutaPerDefecte("ConjuntItems", "ConjuntItems1");
        try {
            TreeMap<Id, Item> result = new TreeMap<>();
            FileReader lector = new FileReader(ruta);
            BufferedReader fitxer = new BufferedReader(lector);
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            ArrayList<String> valorAtributs = new ArrayList<>(Arrays.asList(fitxer.readLine().split(",", 0)));
            int numItems;
            try {
                numItems = Integer.parseInt(fitxer.readLine());
            } catch (NumberFormatException e) {
                throw new Exception("El numero de items no es un int.");
            }
            for (int i = 0; i < numItems; ++i) {
                int nid;
                try {
                    nid = Integer.parseInt(fitxer.readLine());
                } catch (NumberFormatException e) {
                    throw new Exception("El numero de items no es un int.");
                }

                Id id = new Id(nid, true);
                ArrayList<String> valorValors = new ArrayList<>(Arrays.asList(fitxer.readLine().split(",", 0)));
                Item item = new Item(id, tipusItem, valorAtributs, valorValors);

                result.put(id, item);
            }

            return result;

        } catch (FileNotFoundException e) {
            throw new Exception(e.getMessage());
        } catch (Exception e1) {
            throw new Exception("Fitxer invalid");
        }
    }
}
