package domini.tests;

import domini.classes.*;
import domini.classes.csv.TaulaCSV;
import domini.classes.recomanador.metode_recomanador.Punt;
import utilitats.consola;

import java.util.Objects;

/**
 * Driver per la classe Usuari
 *
 * @author pol.casacuberta
 */

public class DriverUsuari {
    public static void testConstructorIntBoolNomContra() throws Exception {
        System.out.println("Testejant el Constructor creador d'atributs.");
        Id id = UtilitatsDeLectura.llegirId();
        String nom = consola.llegirString("Escriu el nom de l'usuari:");
        String contrasenya = consola.llegirString("Escriu la contrasenya de l'usuari:");
        Usuari u1 = new Usuari(id.obtenirValor(), id.esActiu(), nom, contrasenya);
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
        String nom = consola.llegirString("Escriu el nom de l'usuari:");
        String contrasenya = consola.llegirString("Escriu la contrasenya de l'usuari:");
        Usuari u1 = new Usuari(id, nom, contrasenya);
        UtilitatsDEscriptura.imprimirUsuari(u1);
    }

    public static void testObtenirNom() throws Exception {
        System.out.println("Testejant obtenirNom.");
        Usuari u1 = UtilitatsDeLectura.llegirUsuari();
        System.out.println("obtenirNom: " + u1.obtenirNom());
    }

    private static void testObtenirId() throws Exception {
        System.out.println("Testejant obtenirId.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        UtilitatsDEscriptura.imprimirId(usuari.obtenirId());
    }

    private static void testSetActiu() throws Exception {
        System.out.println("Testejant setActiu.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        String actiu = consola.llegirString("Escriu 'true' o 'false'");
        if (Objects.equals(actiu, "true")) {
            usuari.setActiu(true);
        } else if (Objects.equals(actiu, "false")) {
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
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        String contrasenya = consola.llegirString("Reescriu la contrasenya:");
        usuari.setContrasenya(contrasenya);
        System.out.println("IsContrasenya: " + usuari.isContrasenya(contrasenya));
    }

    public static void testSetContrasenya() throws Exception {
        System.out.println("Testejant setContrasenya.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();

        String contrasenya = consola.llegirString("Reescriu la contrasenya:");
        usuari.setContrasenya(contrasenya);

        if (usuari.isContrasenya(contrasenya)) {
            System.out.println("La contrasenya s'ha canviat correctament");
        } else {
            System.out.println("La contrasenya no s'ha canviat correctament");
        }
    }

    public static void testAfegirValoracio() {
        System.out.println("Testejant afegirValoracio.");
        try {

            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Item item = UtilitatsDeLectura.llegirItem();

            double valor = consola.llegirDouble("Introdueix el valor de la valoració.",
                    "El valor introduït no és vàlid.", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            Valoracio valoracio = new Valoracio(valor, usuari, item);
            usuari.afegirValoracio(valoracio);
            UtilitatsDEscriptura.imprimirValoracionsUsuari(usuari.obtenirValoracions());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEsborrarValoracio() {
        System.out.println("Testejant esborrarValoracio.");
        try {
            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Item item = UtilitatsDeLectura.llegirItem();

            double valor = consola.llegirDouble("Introdueix el valor de la valoració.",
                    "El valor introduït no és vàlid.", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            Valoracio valoracio = new Valoracio(valor, usuari, item);
            usuari.afegirValoracio(valoracio);
            usuari.esborraValoracio(item);
            System.out.println("La valoració de l'usuari s'ha esborrat");
            UtilitatsDEscriptura.imprimirValoracionsUsuari(usuari.obtenirValoracions());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObtenirValoracio() {
        System.out.println("Testejant obtenirValoracio.");
        try {
            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            Item item = UtilitatsDeLectura.llegirItem();

            double valor = consola.llegirDouble("Introdueix el valor de la valoració.",
                    "El valor introduït no és vàlid.", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            Valoracio valoracio = new Valoracio(valor, usuari, item);
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
        } else {
            System.out.println("Els dos usuaris son diferents");
        }
    }

    public static void testCompareTo() throws Exception {
        System.out.println("Testejant CompareTo.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        Usuari usuari2 = UtilitatsDeLectura.llegirUsuari();
        int compare = usuari.compareTo(usuari2);
        if (compare < 0) {
            System.out.println("Usuari 1 es mes petit que l'usuari dos");
        } else if (compare > 0) {
            System.out.println("Usuari 1 es mes gran que l'usuari dos");
        } else {
            System.out.println("Usuari 1 i Usuari 2 son iguals");
        }
    }

    public static void testCopy() throws Exception {
        System.out.println("Testejant Copy.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        Usuari usuari2 = usuari.copiar();
        UtilitatsDEscriptura.imprimirUsuari(usuari2);
    }

    public static void testHashCode() throws Exception {
        System.out.println("Testejant HashCode.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        Usuari usuari2 = UtilitatsDeLectura.llegirUsuari();

        if (usuari.hashCode() == usuari2.hashCode()) {
            System.out.println("Els dos usuaris son iguals");
        } else {
            System.out.println("Els dos usuaris son diferents");
        }
    }

    private static void testSetNom() throws Exception {
        System.out.println("Testejant setNom.");
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        String nouNom = consola.llegirString("Escriu el nom de l'usuari:");
        usuari.setContrasenya(nouNom);
        System.out.println("SetNom: " + usuari.obtenirNom());
    }

    public static void testTransformaAPunt() throws Exception {
        Usuari usuari = UtilitatsDeLectura.llegirUsuari();
        TaulaCSV taulaItems = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV("TaulaCSV1"));
        String tipusItem = consola.llegirString("Introdueix el nom de tipusItem");
        ConjuntItems conjuntItems = new ConjuntItems(tipusItem, taulaItems);

        Punt punt = usuari.transformaAPunt(conjuntItems);
        for (double elem : punt) {
            System.out.println(elem);
        }
    }

    public static void main(String[] args) {
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
                "16 - Test HashCode\n" +
                "17 - Test setNom\n" +
                "18 - Test Transforma a punt\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 18";
        while (true) {
            try {
                int i = consola.llegirInt(consulta, err, 0, 18);
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
                    case 18:
                        testTransformaAPunt();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}
