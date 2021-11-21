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
        System.out.println("S'ha construit un conjuntUsuaris buit");
    }

    public static void testAfegirTaulaCSV() throws Exception {
        System.out.println("Test afegir Taula CSV");
//        String ubicacioRatings = consola.llegirString("Introdueix la ruta del fitxer d'entrada de Ratings");
//        String ubicacioItems = consola.llegirString("Introdueix la ruta del fitxer d'entrada d'items");
        try {
            TaulaCSV taulaRatings = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
            TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV());
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

    public static void testEsborrarId() throws Exception {
        System.out.println("Test esborrar amb un Id");
        String ubicacio = consola.llegirString("Introdueix la ruta del fitxer d'entrada");
        try {
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(ubicacio);
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taula);
            System.out.println("Conjunt creat");
            UtilitatsDEscriptura.imprimirConjuntUsuaris(conjuntUsuaris);
            Id id = UtilitatsDeLectura.llegirId();
            conjuntUsuaris.esborrar(id);
            System.out.println("Conjunt després de borrar l'usuari desitjat");
            UtilitatsDEscriptura.imprimirConjuntUsuaris(conjuntUsuaris);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEsborrarUsuari() throws Exception {
        System.out.println("Test esborrar Usuari");
        String ubicacio = consola.llegirString("Introdueix la ruta del fitxer d'entrada");
        try {
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(ubicacio);
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taula);
            System.out.println("Conjunt creat");
            UtilitatsDEscriptura.imprimirConjuntUsuaris(conjuntUsuaris);
            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            conjuntUsuaris.esborrar(usuari);
            System.out.println("Conjunt després de borrar l'usuari desitjat");
            UtilitatsDEscriptura.imprimirConjuntUsuaris(conjuntUsuaris);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObtenirUsuaris() throws Exception {
        System.out.println("Test esborrar Usuari");
        String ubicacio = consola.llegirString("Introdueix la ruta del fitxer d'entrada");
        try {
            LectorDeCSV lector = new LectorDeCSV();
            TaulaCSV taula = lector.llegirCSV(ubicacio);
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taula);
            System.out.println("Conjunt creat");
            UtilitatsDEscriptura.imprimirArrayDUsuaris(conjuntUsuaris.obtenirUsuaris());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor bàsic\n" +
                "2 - Test Afegir TaulaCSV\n" +
                "3 - Test esborra Id\n" +
                "4 - Test esborrarUsuari\n" +
                "5 - Test Obtenir Usuaris\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 5";
        while(true){
            try {
                int i = consola.llegirInt(consulta, err, 0, 5);
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
                        testEsborrarId();
                        break;
                    case 4:
                        testEsborrarUsuari();
                        break;
                    case 5:
                        testObtenirUsuaris();
                        break;
                }
            }
            catch(Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}
