import domini.classes.Id;
import domini.classes.Item;
import domini.classes.TipusItem;
import domini.classes.Usuari;
import libs.consola;

import java.util.ArrayList;

/**
 * Driver per la classe Usuari
 * @author pol.casacuberta
 */

public class DriverUsuari {
    public static void testConstructorBasic() {
        System.out.println("Testejant el Constructor bàsic.");
    }

    public static void testConstructorIntBoolNomContra() throws Exception {
        System.out.println("Testejant el Constructor creador d'atributs.");
        Id id = UtilitatsDeLectura.llegirId();
        String contrasenya = "1234";
        String nom = "Pablo";
        Usuari u1 = new Usuari(id.obtenirValor(),true, nom, contrasenya);
        UtilitatsDEscriptura.imprimirUsuari(u1);
    }

    public static void testConstructorId() throws Exception {
        System.out.println("Testejant el Constructor creador d'atributs.");
        Id id = UtilitatsDeLectura.llegirId();
        Usuari u1 = new Usuari(id);
        UtilitatsDEscriptura.imprimirUsuari(u1);
    }
    public static void testConstructorIdNomContra() throws Exception {
        System.out.println("Testejant el Constructor creador d'atributs.");
        Id id = UtilitatsDeLectura.llegirId();
        String contrasenya = "1234";
        String nom = "Pablo";
        Usuari u1 = new Usuari(id,nom,contrasenya);
        UtilitatsDEscriptura.imprimirUsuari(u1);
    }

    public static void testObtenirNom() throws Exception {
        System.out.println("Testejant obtenirNom.");
        Id id = UtilitatsDeLectura.llegirId();
        String nom = "Pablo";
        Usuari u1 = new Usuari(id);
        System.out.println("obtenirNom: " + u1.obtenirNom());
    }

    private static void testSetNom() throws Exception {
        System.out.println("Testejant setNom.");
        Id id = UtilitatsDeLectura.llegirId();
        String nom = "Pablo";
        Usuari u1 = new Usuari(id);
        u1.setNom("Juanjo");
        System.out.println("SetNom: " + u1.obtenirNom());
    }

    private static void testObtenirId() throws Exception {
        System.out.println("Testejant obtenirId.");
        Id id = UtilitatsDeLectura.llegirId();
        String nom = "Pablo";
        Usuari u1 = new Usuari(id);
        UtilitatsDEscriptura.imprimirId(u1.obtenirId());
    }

    private static void testSetActiu() throws Exception {
        System.out.println("Testejant setActiu.");
        Id id = UtilitatsDeLectura.llegirId();
        String nom = "Pablo";
        Usuari u1 = new Usuari(id);
        u1.setActiu(false);
        UtilitatsDEscriptura.imprimirId(u1.obtenirId());
    }

    public static void testIsActiu() throws Exception {
        System.out.println("Testejant isActiu.");
        Id id = UtilitatsDeLectura.llegirId();
        String nom = "Pablo";
        Usuari u1 = new Usuari(id);
        boolean b = u1.isActiu();
        System.out.println("IsActiu: " + b);
    }

    public static void testSetContrasenya() {
        System.out.println("Testejant setContrasenya.");
    }

    public static void testAfegirValoracio() {
        System.out.println("Testejant afegirValoracio.");
    }

    public static void testEsborrarValoracio() {
        System.out.println("Testejant esborrarValoracio.");
    }

    public static void testObtenirValoracio() {
        System.out.println("Testejant obtenirValoracio.");
    }

    public static void testEquals() {
        System.out.println("Testejant Equals.");
    }

    public static void testCompareTo() {
        System.out.println("Testejant CompareTo.");
    }

    public static void testCopy() {
        System.out.println("Testejant Copy.");
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor bàsic\n" +
                "2 - Test Constructor amb int bool nom i contrasenya\n" +
                "3 - Test Constructor amb Id\n" +
                "4 - Test Constructor amb Id nom i contrasenya\n" +
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
                int i = consola.llegeixEnter(consulta, err, 0, 12);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testConstructorBasic();
                        break;
                    case 2:
                        testConstructorIntBoolNomContra();
                        break;
                    case 3:
                        testConstructorId();
                        break;
                    case 4:
                        testConstructorIdNomContra();
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
