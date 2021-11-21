package domini.tests;

import domini.classes.*;
import libs.consola;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Driver per la classe Item
 * @author maria.prat
 */
public class DriverItem {
    public static void testConstructorCreadorDAtributs() {
        System.out.println("Testejant el Constructor creador d'atributs.");
        try {
            Id id = UtilitatsDeLectura.llegirId();
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs("NomAtributs2");
            ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
            Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);
            System.out.println("S'ha creat l'Item.");
            System.out.println();
            UtilitatsDEscriptura.imprimirItem(item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObtenirId() {
        System.out.println("Testejant obtenirId.");
        try {
            Item item = UtilitatsDeLectura.llegirItem();
            UtilitatsDEscriptura.imprimirId(item.obtenirId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testObtenirValoracions() {
        System.out.println("Testejant obtenirValoracions.");
        try {
            Item item = UtilitatsDeLectura.llegirItem();
            UtilitatsDEscriptura.imprimirValoracions(item.obtenirValoracions());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testObtenirAtributs() {
        System.out.println("Testejant obtenirAtributs.");
        try {
            Item item = UtilitatsDeLectura.llegirItem();
            UtilitatsDEscriptura.imprimirAtributs(item.obtenirAtributs());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testObtenirTipusItem() {
        System.out.println("Testejant obtenirTipusItem.");
        try {
            Item item = UtilitatsDeLectura.llegirItem();
            UtilitatsDEscriptura.imprimirTipusItem(item.obtenirTipusItem());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObtenirDistancia() {
        System.out.println("Testejant obtenirDistancia.");
        try {
            System.out.println("Llegint Item1.");
            Item item1 = UtilitatsDeLectura.llegirItem();
            System.out.println("Llegint Item2.");
            Item item2 = UtilitatsDeLectura.llegirItem();
            System.out.println();
            System.out.println("La distància entre aquests dos ítems és: " + item1.obtenirDistancia(item2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testAfegirValoracio() {
        System.out.println("Testejant afegirValoracio.");
        try {
            Item item = UtilitatsDeLectura.llegirItem();
            System.out.println("Llegint Id de l'Usuari de la valoració.");
            Id idUsuari = UtilitatsDeLectura.llegirId();
            double valor = consola.llegirDouble("Introdueix el valor de la valoració",
                    "El valor introduït no és vàlid", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            // Es crida item.afegirValoracioQuanEsCreaValoracio
            Valoracio valoracio = new Valoracio(valor, new Usuari(idUsuari), item);
            System.out.println("S'ha afegit la valoració a l'ítem.");
            UtilitatsDEscriptura.imprimirItem(item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEsborrarAtributs() {
        System.out.println("Testejant esborrarAtributs.");
        try {
            Item item = UtilitatsDeLectura.llegirItem();
            System.out.println("Llegint Id de l'Usuari de la valoració.");
            TreeSet<String> nomAtributsPerEsborrar = new TreeSet<>(UtilitatsDeLectura.llegirNomAtributs("NomAtributs2"));
            item.esborrarAtributs(nomAtributsPerEsborrar);
            System.out.println("S'han esborrat els atributs indicats.");
            UtilitatsDEscriptura.imprimirItem(item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor creador d'atributs\n" +
                "2 - Test ObtenirId\n" +
                "3 - Test ObtenirTipusItem\n" +
                "4 - Test ObtenirAtributs\n" +
                "5 - Test ObtenirValoracions\n" +
                "6 - Test ObtenirDistancia\n" +
                "7 - Test AfegirValoracio\n" +
                "8 - Test EsborrarAtributs\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 8";
        while(true){
            try {
                int i = consola.llegirInt(consulta, err, 0, 8);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testConstructorCreadorDAtributs();
                        break;
                    case 2:
                        testObtenirId();
                        break;
                    case 3:
                        testObtenirTipusItem();
                        break;
                    case 4:
                        testObtenirAtributs();
                        break;
                    case 5:
                        testObtenirValoracions();
                        break;
                    case 6:
                        testObtenirDistancia();
                        break;
                    case 7:
                        testAfegirValoracio();
                        break;
                    case 8:
                        testEsborrarAtributs();
                        break;
                }
            }
            catch(Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}