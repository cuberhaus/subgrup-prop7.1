package domini.tests;

import domini.classes.ConjuntUsuaris;
import domini.classes.Id;
import domini.classes.Usuari;
import domini.classes.csv.TaulaCSV;
import utilitats.consola;

/**
 * Driver per la classe ConjuntUsuaris
 *
 * @author pol.casacuberta
 */

public class DriverConjuntUsuaris {
    /**
     * Retorna un conjunt d'usuaris donada una taula per defecte
     *
     * @param taulaPerDefecte Taula per defecte
     * @return Retorna un Conjunt d'usuaris
     * @throws Exception No s'ha pogut crear el conjunt
     */
    public static ConjuntUsuaris creaConjuntUsuaris(String taulaPerDefecte) throws Exception {
        TaulaCSV taula = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV(taulaPerDefecte));
        ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
        conjuntUsuaris.afegir(taula);
        System.out.println("Conjunt creat");
        UtilitatsDEscriptura.imprimirConjuntUsuaris(conjuntUsuaris);
        return conjuntUsuaris;
    }

    public static void testConstructorBasic() {
        System.out.println("Test Constructora bàsica");
        ConjuntUsuaris conjuntUsuaris;
        System.out.println("S'ha construit un conjuntUsuaris buit");
    }

    public static void testAfegirTaulaCSV() {
        System.out.println("Test afegir Taula CSV");
        try {
            ConjuntUsuaris conjuntUsuaris = creaConjuntUsuaris("ratings");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void testEsborrarId() {
        System.out.println("Test esborrar amb un Id");
        try {
            ConjuntUsuaris conjuntUsuaris = creaConjuntUsuaris("ratings");

            Id id = UtilitatsDeLectura.llegirId();
            conjuntUsuaris.esborrar(id);
            System.out.println("Conjunt després de borrar l'usuari desitjat");
            UtilitatsDEscriptura.imprimirConjuntUsuaris(conjuntUsuaris);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEsborrarUsuari() {
        System.out.println("Test esborrar Usuari");
        try {
            ConjuntUsuaris conjuntUsuaris = creaConjuntUsuaris("ratings");

            Usuari usuari = UtilitatsDeLectura.llegirUsuari();
            conjuntUsuaris.esborrar(usuari);
            System.out.println("Conjunt després de borrar l'usuari desitjat");
            UtilitatsDEscriptura.imprimirConjuntUsuaris(conjuntUsuaris);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testObtenirUsuaris() {
        System.out.println("Test esborrar Usuari");
        try {
            TaulaCSV taula = new TaulaCSV(UtilitatsDeLectura.llegirTaulaCSV("ratings"));
            ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
            conjuntUsuaris.afegir(taula);
            System.out.println("Conjunt creat");
            UtilitatsDEscriptura.imprimirArrayDUsuaris(conjuntUsuaris.obtenirUsuaris());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor bàsic\n" +
                "2 - Test Afegir TaulaCSV\n" +
                "3 - Test esborra Id\n" +
                "4 - Test esborrarUsuari\n" +
                "5 - Test Obtenir Usuaris\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 5";
        while (true) {
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
            } catch (Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}
