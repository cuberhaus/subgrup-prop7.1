import domini.classes.Id;
import domini.classes.Item;
import domini.classes.TipusItem;
import libs.consola;

import java.util.ArrayList;

/**
 * Driver per la classe Item
 * @author maria.prat
 */
public class DriverItem {
    public static void testConstructorBasic() {
        System.out.println("Testejant el Constructor bàsic.");
    }

    public static void testConstructorCreadorDAtributs() {
        System.out.println("Testejant el Constructor creador d'atributs.");
        try {
            Id id = UtilitatsDeLectura.llegirId();
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
            ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
            Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);
            System.out.println("S'ha creat l'Item.");
            System.out.println();
            UtilitatsDEscriptura.imprimirItem(item);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testCopiar() {
        System.out.println("Testejant copiar.");
    }

    public static void testCompareTo() {
        System.out.println("Testejant compareTo.");
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
    }

    public static void testAfegirValoracio() {
        System.out.println("Testejant afegirValoracio.");
    }

    public static void testEsborrarValoracio() {
        System.out.println("Testejant esborrarValoracio.");
    }

    public static void testEsborrarAtributs() {
        System.out.println("Testejant esborrarAtributs.");
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor bàsic\n" +
                "2 - Test Constructor creador d'atributs\n" +
                "3 - Test Copiar\n" +
                "4 - Test CompareTo\n" +
                "5 - Test ObtenirId\n" +
                "6 - Test ObtenirTipusItem\n" +
                "7 - Test ObtenirAtributs\n" +
                "8 - Test ObtenirValoracions\n" +
                "9 - Test ObtenirDistancia\n" +
                "10 - Test AfegirValoracio\n" +
                "11 - Test EsborrarValoracio\n" +
                "12 - Test EsborrarAtributs\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 12";
        while(true){
            try {
                int i = consola.llegirInt(consulta, err, 0, 12);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testConstructorBasic();
                        break;
                    case 2:
                        testConstructorCreadorDAtributs();
                        break;
                    case 3:
                        testCopiar();
                        break;
                    case 4:
                        testCompareTo();
                        break;
                    case 5:
                        testObtenirId();
                        break;
                    case 6:
                        testObtenirTipusItem();
                        break;
                    case 7:
                        testObtenirAtributs();
                        break;
                    case 8:
                        testObtenirValoracions();
                        break;
                    case 9:
                        testObtenirDistancia();
                        break;
                    case 10:
                        testAfegirValoracio();
                        break;
                    case 11:
                        testEsborrarValoracio();
                        break;
                    case 12:
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