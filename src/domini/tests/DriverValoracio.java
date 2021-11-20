import domini.classes.*;
import libs.consola;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Driver per la classe Valoració
 * @author pol.casacuberta
 */

public class DriverValoracio {
    public static void testConstructorValorUsuariItem() throws Exception {
        System.out.println("TestConstructora");
        double valor = UtilitatsDeLectura.llegirDouble();
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        Id id = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
        Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

        Valoracio valoracio = new Valoracio(valor, usuari, item);
        UtilitatsDEscriptura.imprimirValoracio(valoracio);

    }

    public static void testObtenirItem() throws Exception {
        System.out.println("Test obtenir item");

        double valor = UtilitatsDeLectura.llegirDouble();
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        Id id = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
        Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

        Valoracio valoracio = new Valoracio(valor, usuari, item);
        UtilitatsDEscriptura.imprimirItem(valoracio.obtenirItem());
    }

    public static void testObtenirUsuari() throws Exception {
        System.out.println("Test obtenir Usuari");

        double valor = UtilitatsDeLectura.llegirDouble();
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        Id id = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
        Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

        Valoracio valoracio = new Valoracio(valor, usuari, item);
        UtilitatsDEscriptura.imprimirUsuari(valoracio.obtenirUsuari());
    }

    public static void testObtenirValor() throws Exception {
        System.out.println("Test obtenir valor");

        double valor = UtilitatsDeLectura.llegirDouble();
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        Id id = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
        Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

        Valoracio valoracio = new Valoracio(valor, usuari, item);
        UtilitatsDEscriptura.imprimirUsuari(valoracio.obtenirUsuari());
    }

    private static void testEquals() throws Exception {
        System.out.println("Test equals");

        double valor = UtilitatsDeLectura.llegirDouble();
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        Id id = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
        Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

        Valoracio valoracio = new Valoracio(valor, usuari, item);

        double valor2 = UtilitatsDeLectura.llegirDouble();
        Usuari usuari2 = UtilitatsDeLectura.llegirUsuari();

        Id id2 = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem2 = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs2 = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs2 = UtilitatsDeLectura.llegirValorAtributs();
        Item item2 = new Item(id2, tipusItem2, nom_atributs2, valor_atributs2);

        Valoracio valoracio2 = new Valoracio(valor2, usuari2, item2);

        if (valoracio.equals(valoracio2)) {
            System.out.println("Les dues valoracions son iguals");
        }
        else {
            System.out.println("Les dues valoracions son diferents");
        }
    }

    private static void testHashCode() throws Exception {
        System.out.println("Testejant hashCode.");

        double valor = UtilitatsDeLectura.llegirDouble();
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        Id id = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
        Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

        Valoracio valoracio = new Valoracio(valor, usuari, item);

        double valor2 = UtilitatsDeLectura.llegirDouble();
        Usuari usuari2 = UtilitatsDeLectura.llegirUsuari();

        Id id2 = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem2 = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs2 = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs2 = UtilitatsDeLectura.llegirValorAtributs();
        Item item2 = new Item(id2, tipusItem2, nom_atributs2, valor_atributs2);

        Valoracio valoracio2 = new Valoracio(valor2, usuari2, item2);

        if (valoracio.hashCode() == valoracio2.hashCode()) {
            System.out.println("Les dues valoracions son iguals");
        }
        else {
            System.out.println("Les dues valoracions son diferents");
        }
    }

    private static void testCopy() throws Exception {
        System.out.println("Testejant copy.");

        double valor = UtilitatsDeLectura.llegirDouble();
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        Id id = UtilitatsDeLectura.llegirId();
        TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
        ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
        ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
        Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

        Valoracio valoracio = new Valoracio(valor, usuari, item);
        Valoracio valoracio2 = valoracio.copy();
        System.out.println("Copia de la valoracio:");
        UtilitatsDEscriptura.imprimirValoracio(valoracio2);
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test ConstructoraValorUsuariItem\n" +
                "2 - Test Obtenir Item\n" +
                "3 - Test Obtenir Usuari\n" +
                "4 - Test Obtenir Valor\n" +
                "5 - Test Equals\n" +
                "6 - Test HashCode\n" +
                "7 - Test Copy\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 7";
        while(true){
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 7);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testConstructorValorUsuariItem();
                        break;
                    case 2:
                        testObtenirItem();
                        break;
                    case 3:
                        testObtenirUsuari();
                        break;
                    case 4:
                        testObtenirValor();
                        break;
                    case 5:
                        testEquals();
                        break;
                    case 6:
                        testHashCode();
                        break;
                    case 7:
                        testCopy();
                        break;
                }
            }
            catch(Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}
