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

    public static void testAfegirTaulaCSV() throws Exception {
        System.out.println("Test afegir Taula CSV");
        try {
            System.out.println("Introdueix la ruta del fitxer d'entrada de Ratings");
            TaulaCSV taulaRatings = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            System.out.println("Introdueix la ruta del fitxer d'entrada d'items");
            TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taulaRatings);
            String tipusItem = consola.llegirString("Introdueix el nom de tipusItem");
            ConjuntItems conjuntItems = new ConjuntItems(tipusItem,taulaItems);

            ConjuntValoracions conjuntValoracions = new ConjuntValoracions();
            conjuntValoracions.afegir(taulaRatings,conjuntItems,conjuntUsuaris);
            System.out.println("Conjunt creat");
            UtilitatsDEscriptura.imprimirConjuntValoracions(conjuntValoracions);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testConte() throws Exception {
        System.out.println("Test conte valoracio amb usuari i item donats");
        try {
            System.out.println("Introdueix la ruta del fitxer d'entrada de Ratings");
            TaulaCSV taulaRatings = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            System.out.println("Introdueix la ruta del fitxer d'entrada d'items");
            TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taulaRatings);
            String tipusItem = consola.llegirString("Introdueix el nom de tipusItem");
            ConjuntItems conjuntItems = new ConjuntItems(tipusItem,taulaItems);

            ConjuntValoracions conjuntValoracions = new ConjuntValoracions();
            conjuntValoracions.afegir(taulaRatings,conjuntItems,conjuntUsuaris);

            UtilitatsDEscriptura.imprimirConjuntValoracions(conjuntValoracions);

            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Item item = UtilitatsDeLectura.llegirItem();
            if (conjuntValoracions.conte(usuari,item)) {
                System.out.println("La valoració amb usuari, i item donats, existeix dins el conjunt");
            }
            else {
                System.out.println("La valoració amb usuari, i item donats, no existeix dins el conjunt");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObte() throws Exception {
        System.out.println("Test obte valoracio amb usuari i item donats");
        try {
            System.out.println("Introdueix la ruta del fitxer d'entrada de Ratings");
            TaulaCSV taulaRatings = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            System.out.println("Introdueix la ruta del fitxer d'entrada d'items");
            TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taulaRatings);
            String tipusItem = consola.llegirString("Introdueix el nom de tipusItem");
            ConjuntItems conjuntItems = new ConjuntItems(tipusItem,taulaItems);

            ConjuntValoracions conjuntValoracions = new ConjuntValoracions();
            conjuntValoracions.afegir(taulaRatings,conjuntItems,conjuntUsuaris);

            UtilitatsDEscriptura.imprimirConjuntValoracions(conjuntValoracions);

            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Item item = UtilitatsDeLectura.llegirItem();

            UtilitatsDEscriptura.imprimirValoracio(conjuntValoracions.obte(usuari,item));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEsborrarAmbUsuariItem() throws Exception {
        System.out.println("Test esborra valoracio amb usuari i item donats");

        try {
            System.out.println("Introdueix la ruta del fitxer d'entrada de Ratings");
            TaulaCSV taulaRatings = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            System.out.println("Introdueix la ruta del fitxer d'entrada d'items");
            TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taulaRatings);
            String tipusItem = consola.llegirString("Introdueix el nom de tipusItem");
            ConjuntItems conjuntItems = new ConjuntItems(tipusItem,taulaItems);

            ConjuntValoracions conjuntValoracions = new ConjuntValoracions();
            conjuntValoracions.afegir(taulaRatings,conjuntItems,conjuntUsuaris);

            UtilitatsDEscriptura.imprimirConjuntValoracions(conjuntValoracions);

            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Item item = UtilitatsDeLectura.llegirItem();
            conjuntValoracions.esborrar(usuari,item);

            if (conjuntValoracions.conte(usuari,item)) {
                System.out.println("La valoració amb usuari, i item donats, existeix dins el conjunt");
            }
            else {
                System.out.println("La valoració amb usuari, i item donats, no existeix dins el conjunt");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEsborrarValoracio() throws Exception {
        System.out.println("Test esborra valoracio donada una valoracio");

        try {
            System.out.println("Introdueix la ruta del fitxer d'entrada de Ratings");
            TaulaCSV taulaRatings = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            System.out.println("Introdueix la ruta del fitxer d'entrada d'items");
            TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taulaRatings);
            String tipusItem = consola.llegirString("Introdueix el nom de tipusItem");
            ConjuntItems conjuntItems = new ConjuntItems(tipusItem,taulaItems);

            ConjuntValoracions conjuntValoracions = new ConjuntValoracions();
            conjuntValoracions.afegir(taulaRatings,conjuntItems,conjuntUsuaris);

            UtilitatsDEscriptura.imprimirConjuntValoracions(conjuntValoracions);

            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Item item = UtilitatsDeLectura.llegirItem();

            Valoracio valoracio = new Valoracio(4,usuari,item);

            conjuntValoracions.esborrar(valoracio);

            if (conjuntValoracions.conte(usuari,item)) {
                System.out.println("La valoració amb usuari, i item donats, existeix dins el conjunt");
            }
            else {
                System.out.println("La valoració amb usuari, i item donats, no existeix dins el conjunt");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObteTotesValoracions() throws Exception {
        System.out.println("Test obte totes les valoracions");

        try {
            System.out.println("Introdueix la ruta del fitxer d'entrada de Ratings");
            TaulaCSV taulaRatings = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            System.out.println("Introdueix la ruta del fitxer d'entrada d'items");
            TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taulaRatings);
            String tipusItem = consola.llegirString("Introdueix el nom de tipusItem");
            ConjuntItems conjuntItems = new ConjuntItems(tipusItem,taulaItems);

            ConjuntValoracions conjuntValoracions = new ConjuntValoracions();
            conjuntValoracions.afegir(taulaRatings,conjuntItems,conjuntUsuaris);

            UtilitatsDEscriptura.imprimirConjuntValoracions(conjuntValoracions);

            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Item item = UtilitatsDeLectura.llegirItem();

            UtilitatsDEscriptura.imprimirTreeMapValoracions(conjuntValoracions.obteTotesValoracions());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
