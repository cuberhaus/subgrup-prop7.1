import domini.classes.*;
import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import libs.consola;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Driver per la classe Usuari
 * @author pol.casacuberta
 */

public class DriverConjuntValoracions {
    public static void testConstructorBasic() {
        System.out.println("Test Constructora bàsica");
        ConjuntValoracions conjuntValoracions;
        System.out.println("S'ha construit un conjuntValoracions buit");
    }

    public static void testAfegirTaulaCSV() {
        System.out.println("Test afegir Taula CSV");
        try {
            TaulaCSV taulaRatings = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV("TaulaCSV1"));
            TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV("TaulaCSV1"));
            ConjuntValoracions conjuntValoracions = new ConjuntValoracions();
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taulaRatings);

            String tipusItem = consola.llegirString("Introdueix el nom de tipusItem");
            ConjuntItems conjuntItems = new ConjuntItems(tipusItem,taulaItems);
            conjuntValoracions.afegir(taulaRatings,conjuntItems,conjuntUsuaris);
            System.out.println("Conjunt creat");
            UtilitatsDEscriptura.imprimirConjuntValoracions(conjuntValoracions);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testConte() {
        System.out.println("Test conte valoracio amb usuari i item donats");
    }

    public static void testObte() {
        System.out.println("Test obte valoracio amb usuari i item donats");
    }

    public static void testEsborrarAmbUsuariItem() {
        System.out.println("Test esborra valoracio amb usuari i item donats");
    }

    public static void testEsborrarValoracio() {
        System.out.println("Test esborra valoracio donada una valoracio");
    }

    public static void testObteTotesValoracions() {
        System.out.println("Test obte totes les valoracions");
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor bàsic\n" +
                "2 - Test Afegir TaulaCSV\n" +
                "3 - Test Conte\n" +
                "4 - Test obte\n" +
                "5 - Test esborrar amb usuari i item\n" +
                "6 - Test esborrar valoracio\n" +
                "7 - Test test obte totes les valoracions\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 7";
        while(true){
            try {
                int i = consola.llegirInt(consulta, err, 0, 7);
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
                        testConte();
                        break;
                    case 4:
                        testObte();
                        break;
                    case 5:
                        testEsborrarAmbUsuariItem();
                        break;
                    case 6:
                        testEsborrarValoracio();
                        break;
                    case 7:
                        testObteTotesValoracions();
                        break;
                }
            }
            catch(Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}