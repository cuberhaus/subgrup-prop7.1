import domini.classes.ConjuntItems;
import domini.classes.Item;
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
            ArrayList<Item> items = conjunt.getItems();
            for (Item it : items) {
                UtilitatsDEscriptura.imprimirItem(it);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testConstructorBuit() {

    }

    public static void main(String[] args) {
        System.out.println("Driver ConjuntItems");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test constructora a partir de CSVTable\n" +
                "2 - Test constructora buida\n";
        String err = "Valor invalid: introdueix un enter entre 0 i x";
        while (true) {
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 1);
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
