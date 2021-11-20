import domini.classes.ConjuntItems;
import domini.classes.Id;
import domini.classes.Item;
import domini.classes.TipusItem;
import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import libs.consola;

import java.util.TreeMap;
import java.util.TreeSet;

public class DriverConjuntItems {
    public static void testConstructorTaula() throws Exception {
        String ubicacio = UtilitatsDeLectura.llegirUbicacioArxiu();
        try {
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(ubicacio);
            String tipusItem = consola.obtenirString("Introdueix el nom de tipusItem");
            ConjuntItems conjunt = new ConjuntItems(tipusItem, taula);
            System.out.println("Conjunt creat");
            UtilitatsDEscriptura.imprimirConjuntItems(conjunt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testConstructorBuit() {
        System.out.println("Test contructora buida");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            ConjuntItems conjuntItems = new ConjuntItems(tipusItem);
            System.out.println("S'ha creat el conjunt Items");
            UtilitatsDEscriptura.imprimirConjuntItems(conjuntItems);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void testConstructorMap() {
        System.out.println("Test contructor map");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            TreeMap<Id, Item> map = UtilitatsDeLectura.llegirMapItems();
            ConjuntItems conjunt = new ConjuntItems(tipusItem, map);
            System.out.println("S'ha creat la instancia");
            UtilitatsDEscriptura.imprimirConjuntItems(conjunt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEliminarAtribut() {
        System.out.println("Test contructor map");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            TreeMap<Id, Item> map = UtilitatsDeLectura.llegirMapItems();
            ConjuntItems conjunt = new ConjuntItems(tipusItem, map);
            System.out.println("S'ha creat la instancia");
            UtilitatsDEscriptura.imprimirConjuntItems(conjunt);

            TreeSet<String> atriibuts = UtilitatsDeLectura.llegirTreeSet();
            conjunt.esborrarAtributs(atriibuts);
            UtilitatsDEscriptura.imprimirConjuntItems(conjunt);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        System.out.println("Driver ConjuntItems");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test constructora a partir de CSVTable\n" +
                "2 - Test constructora buida\n" +
                "3 - Test contructura map\n" +
                "4 - Test eliminar atributs\n";
        String err = "Valor invalid: introdueix un enter entre 0 i 4";
        while (true) {
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 4);
                switch (i) {
                    case 0:
                        return;

                    case 1:
                        testConstructorTaula();
                        break;

                    case 2:
                        testConstructorBuit();
                        break;

                    case 3:
                        testConstructorMap();
                        break;

                    case 4:
                        testEliminarAtribut();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Tornar a provar.");
            }
        }
    }
}
