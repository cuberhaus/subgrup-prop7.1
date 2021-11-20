import domini.classes.ConjuntItems;
import domini.classes.Item;
import domini.classes.TipusItem;
import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import libs.consola;

import java.util.ArrayList;

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
    }

    public static void main(String[] args) {
        System.out.println("Driver ConjuntItems");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test constructora a partir de CSVTable\n" +
                "2 - Test constructora buida\n";
        String err = "Valor invalid: introdueix un enter entre 0 i x";
        while (true) {
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 2);
                switch (i) {
                    case 0:
                        return;

                    case 1:
                        testConstructorTaula();
                        break;

                    case 2:
                        testConstructorBuit();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Tornar a provar.");
            }
        }
    }
}
