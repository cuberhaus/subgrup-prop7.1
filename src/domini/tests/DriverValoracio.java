import domini.classes.*;
import libs.consola;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Driver per la classe Usuari
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
    }

    public static void testObtenirUsuari() throws Exception {
        System.out.println("Test obtenir Usuari");
    }

    public static void testObtenirValor() throws Exception {
        System.out.println("Test obtenir valor");
    }

    private static void testEquals() throws Exception {
        System.out.println("Test equals");
    }

    private static void testHashCode() throws Exception {
        System.out.println("Testejant hashCode.");
    }

    private static void testCopy() throws Exception {
        System.out.println("Testejant copy.");
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor bàsic\n" +
                "2 - Test Constructor amb int bool nom i contrasenya\n" +
                "3 - Test Constructor amb Id\n" +
                "4 - Test Constructor amb Id nom i contrasenya\n" +
                "5 - Test ObtenirId\n" +
                "6 - Test SetActiu\n" +
                "7 - Test IsActiu\n" +
                "8 - Test IsContrasenya\n" +
                "9 - Test SetContrasenya\n" +
                "10 - Test AfegirValoracio\n" +
                "11 - Test EsborrarValoracio\n" +
                "12 - Test ObtenirValoracio\n" +
                "13 - Test Equals\n" +
                "14 - Test CompareTo\n" +
                "15 - Test Copy\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 12";
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
