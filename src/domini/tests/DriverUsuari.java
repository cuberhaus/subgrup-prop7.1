import domini.classes.*;
import libs.consola;


import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Driver per la classe Usuari
 * @author pol.casacuberta
 */

public class DriverUsuari {
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
        Usuari u1 = UtilitatsDeLectura.llegirUsuari();
        System.out.println("obtenirNom: " + u1.obtenirNom());
    }

    private static void testSetNom() throws Exception {
        System.out.println("Testejant setNom.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        Scanner input = new Scanner(System.in);
        String nouNom;
        nouNom = input.nextLine();
        usuari.setContrasenya(nouNom);
        System.out.println("SetNom: " + usuari.obtenirNom());
    }

    private static void testObtenirId() throws Exception {
        System.out.println("Testejant obtenirId.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        UtilitatsDEscriptura.imprimirId(usuari.obtenirId());
    }

    private static void testSetActiu() throws Exception {
        System.out.println("Testejant setActiu.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        Scanner input = new Scanner(System.in);
        String actiu;
        actiu = input.nextLine();
        if (Objects.equals(actiu, "true")) {
            usuari.setActiu(true);
        }
        else if (Objects.equals(actiu, "false")){
            usuari.setActiu(false);
        }
        UtilitatsDEscriptura.imprimirId(usuari.obtenirId());
    }

    public static void testIsActiu() throws Exception {
        System.out.println("Testejant isActiu.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        boolean b = usuari.isActiu();
        System.out.println("IsActiu: " + b);
    }

    public static void testIsContrasenya() throws Exception {
        System.out.println("Testejant isContrasenya.");
        Id id = UtilitatsDeLectura.llegirId();
        String nom = "Pablo";
        Usuari u1 = new Usuari(id);

        System.out.println("Escriu la contrasenya '1234'");
        Scanner input = new Scanner(System.in);
        String s;
        s = input.nextLine();
        u1.setContrasenya(s);

        System.out.println("IsContrasenya: " + u1.isContrasenya("1234"));
    }

    public static void testSetContrasenya() throws Exception {
        System.out.println("Testejant setContrasenya.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        String contrasenya;
        Scanner input = new Scanner(System.in);
        System.out.println("Torna a escriure la teva contrasenya:");
        contrasenya = input.nextLine();
        usuari.setContrasenya(contrasenya);

        if (usuari.isContrasenya(contrasenya)) {
            System.out.println("Has encertat la contrasenya");
        }
        else {
            System.out.println("T'has equivocat de contrasenya");
        }
    }

    public static void testAfegirValoracio() throws Exception {
        System.out.println("Testejant afegirValoracio.");
        try {

            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Id id = UtilitatsDeLectura.llegirId();
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
            ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
            Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

            UtilitatsDEscriptura.imprimirId(item.obtenirId());
            System.out.println("Escriu el valor de la valoració.");
            double valor = UtilitatsDeLectura.llegirDouble();
            Valoracio valoracio = new Valoracio(valor,usuari,item);
            usuari.afegirValoracio(valoracio);
            UtilitatsDEscriptura.imprimirValoracionsUsuari(usuari.obtenirValoracions());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEsborrarValoracio() throws Exception {
        System.out.println("Testejant esborrarValoracio.");
        try {
            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Id id = UtilitatsDeLectura.llegirId();
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
            ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
            Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

            UtilitatsDEscriptura.imprimirId(item.obtenirId());
            System.out.println("Escriu el valor de la valoració.");
            double valor = UtilitatsDeLectura.llegirDouble();
            Valoracio valoracio = new Valoracio(valor,usuari,item);
            usuari.afegirValoracio(valoracio);
            usuari.esborraValoracio(item);
            UtilitatsDEscriptura.imprimirValoracionsUsuari(usuari.obtenirValoracions());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObtenirValoracio() throws Exception {
        System.out.println("Testejant obtenirValoracio.");
        try {
            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Id id = UtilitatsDeLectura.llegirId();
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            ArrayList<String> nom_atributs = UtilitatsDeLectura.llegirNomAtributs();
            ArrayList<String> valor_atributs = UtilitatsDeLectura.llegirValorAtributs();
            Item item = new Item(id, tipusItem, nom_atributs, valor_atributs);

            UtilitatsDEscriptura.imprimirId(item.obtenirId());
            System.out.println("Escriu el valor de la valoració.");
            double valor = UtilitatsDeLectura.llegirDouble();
            Valoracio valoracio = new Valoracio(valor,usuari,item);
            usuari.afegirValoracio(valoracio);

            UtilitatsDEscriptura.imprimirValoracio(usuari.obtenirValoracio(item));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEquals() throws Exception {
        System.out.println("Testejant Equals.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        Usuari usuari2 = UtilitatsDeLectura.llegirUsuari();

        if (usuari.equals(usuari2)) {
            System.out.println("Els dos usuaris son iguals");
        }
        else {
            System.out.println("Els dos usuaris son diferents");
        }
    }

    public static void testCompareTo() throws Exception {
        System.out.println("Testejant CompareTo.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
    }

    public static void testCopy() throws Exception {
        System.out.println("Testejant Copy.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        Usuari usuari2 = usuari.copy();
        UtilitatsDEscriptura.imprimirUsuari(usuari2);
    }

    public static void testHashCode() throws Exception {
        System.out.println("Testejant HashCode.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        Usuari usuari2 = UtilitatsDeLectura.llegirUsuari();

        if (usuari.hashCode() == usuari2.hashCode()) {
            System.out.println("Els dos usuaris son iguals");
        }
        else {
            System.out.println("Els dos usuaris son diferents");
        }
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
                "15 - Test Copy\n" +
                "16 - Test HashCode\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 16";
        while(true){
            try {
                int i = consola.llegeixEnter(consulta, err, 0, 16);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testObtenirNom();
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
                        testSetActiu();
                        break;
                    case 7:
                        testIsActiu();
                        break;
                    case 8:
                        testIsContrasenya();
                        break;
                    case 9:
                        testSetContrasenya();
                        break;
                    case 10:
                        testAfegirValoracio();
                        break;
                    case 11:
                        testEsborrarValoracio();
                        break;
                    case 12:
                        testObtenirValoracio();
                        break;
                    case 13:
                        testEquals();
                        break;
                    case 14:
                        testCompareTo();
                        break;
                    case 15:
                        testCopy();
                        break;
                    case 16:
                        testHashCode();
                        break;
                    case 17:
                        testSetNom();
                        break;
                }
            }
            catch(Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}
