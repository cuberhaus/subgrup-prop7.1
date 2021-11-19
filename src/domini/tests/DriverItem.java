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
                "6 - Test ObtenirDistancia\n" +
                "7 - Test AfegirValoracio\n" +
                "8 - Test EsborrarValoracio\n" +
                "9 - Test EsborrarAtributs\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 9";
        while(true){
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 9);
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
                        testObtenirDistancia();
                        break;
                    case 7:
                        testAfegirValoracio();
                        break;
                    case 8:
                        testEsborrarValoracio();
                        break;
                    case 9:
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
