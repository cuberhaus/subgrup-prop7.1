import domini.classes.*;
import libs.consola;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Driver per la classe Usuari
 * @author pol.casacuberta
 */

public class DriverConjuntUsuaris {
    public static void testConstructorBasic() {
        System.out.println("Test Constructora bàsica");
    }

    public static void testAfegirTaulaCSV() {
        System.out.println("Test afegir Taula CSV");
    }

    public static void esborrarId() {
        System.out.println("Test esborrar amb un Id");
    }
    public static void esborrarUsuari() {
        System.out.println("Test esborrar Usuari");
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor bàsic\n" +
                "2 - Test Afegir TaulaCSV\n" +
                "3 - Test esborra Id\n" +
                "4 - Test esborrarUsuari\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 4";
        while(true){
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 4);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testConstructorBasic();
                        break;
                    case 2:
                        testAfegirTaulaCSV();
                        break;
                    case 3:
                        esborrarId();
                        break;
                    case 4:
                        esborrarUsuari();
                        break;
                }
            }
            catch(Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}
