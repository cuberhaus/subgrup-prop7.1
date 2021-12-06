package domini.tests;

import domini.classes.*;
import domini.classes.atributs.TipusAtribut;
import domini.classes.csv.TaulaCSV;
import utilitats.consola;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;

/**
 * Driver per la classe TipusItem
 * @author maria.prat
 */
public class DriverTipusItem {
    private static void testConstructorTaulaCSVNCandidats() {
        System.out.println("Testejant Constructor amb TaulaCSV i N candidats.");
        try {
            String nomTipusItem = consola.llegirString("Introdueix el nom del TipusItem");
            MockTaulaCSV taulaCSV = new MockTaulaCSV(UtilitatsDeLectura.llegirTaulaCSV("MockTaulaCSV1"));
            System.out.println("S'ha llegit TaulaCSV amb " + taulaCSV.obtenirNumAtributs() + " atributs i " +
                    taulaCSV.obtenirNumItems() + " ítems.");
            int numCandidats = consola.llegirInt(
                    "Introdueix el nombre de candidats a considerar per deduir el TipusItem de la taula",
                    "Valor invàlid",
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE);
            TipusItem tipusItem = new TipusItem(nomTipusItem, taulaCSV, numCandidats);
            System.out.println("S'ha creat el TipusItem.");
            UtilitatsDEscriptura.imprimirTipusItem(tipusItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testAssignarNom() {
        System.out.println("Testejant assignarNom.");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            String nom = consola.llegirString("Introdueix el nou nom del TipusItem");
            tipusItem.assignarNom(nom);
            System.out.println("S'ha assignat el nom del TipusItem.");
            UtilitatsDEscriptura.imprimirTipusItem(tipusItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testObtenirNom() {
        System.out.println("Testejant obtenirNom.");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            System.out.println("El nom del TipusItem és " + tipusItem.obtenirNom());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testAssignarTipusAtributs() {
        System.out.println("Testejant assignarTipusAtributs.");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            Map<String, TipusAtribut> tipusAtributs = UtilitatsDeLectura.llegirTipusAtributs();
            tipusItem.assignarTipusAtributs(tipusAtributs);
            System.out.println("S'han assignat els TipusAtributs.");
            UtilitatsDEscriptura.imprimirTipusItem(tipusItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testObtenirTipusAtributs() {
        System.out.println("Testejant obtenirTipusAtributs.");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            System.out.println("Els TipusAtributs del TipusItem són:");
            UtilitatsDEscriptura.imprimirTipusAtributs(tipusItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testAfegirTipusAtribut() {
        System.out.println("Testejant afegirTipusAtribut.");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            String nom = consola.llegirString("Introdueix el nom del nou TipusAtribut");
            TipusAtribut tipusAtribut = UtilitatsDeLectura.llegirTipusAtribut();
            tipusItem.afegirTipusAtribut(nom, tipusAtribut);
            System.out.println("S'ha afegit el TipusAtribut.");
            UtilitatsDEscriptura.imprimirTipusItem(tipusItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testEsborrarAtributs() {
        System.out.println("Testejant esborrarAtributs.");
        try {
            TipusItem tipusItem = UtilitatsDeLectura.llegirTipusItem();
            TreeSet<String> nomAtributsPerEsborrar = new TreeSet<>(UtilitatsDeLectura.llegirNomAtributs("NomAtributs2"));
            tipusItem.esborrarAtributs(nomAtributsPerEsborrar);
            System.out.println("S'han esborrat els atributs indicats.");
            UtilitatsDEscriptura.imprimirTipusItem(tipusItem);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)  {
        System.out.println("Driver per la classe Item");
        String consulta = "\n0 - Sortir\n" +
                "1 - Test Constructor amb TaulaCSV i N candidats\n" +
                "2 - Test AssignarNom\n" +
                "3 - Test ObtenirNom\n" +
                "4 - Test AssignarTipusAtributs\n" +
                "5 - Test ObtenirTipusAtributs\n" +
                "6 - Test AfegirTipusAtribut\n" +
                "7 - Test EsborrarAtributs\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 7";
        while(true){
            try {
                int i = consola.llegirInt(consulta, err, 0, 7);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        testConstructorTaulaCSVNCandidats();
                        break;
                    case 2:
                        testAssignarNom();
                        break;
                    case 3:
                        testObtenirNom();
                        break;
                    case 4:
                        testAssignarTipusAtributs();
                        break;
                    case 5:
                        testObtenirTipusAtributs();
                        break;
                    case 6:
                        testAfegirTipusAtribut();
                        break;
                    case 7:
                        testEsborrarAtributs();
                        break;
                }
            }
            catch(Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }

    private static class MockTaulaCSV extends TaulaCSV {
        private final ArrayList<ArrayList<String>> taula;
        private final int numItems;
        private final int numAtributs;

        public MockTaulaCSV(ArrayList<ArrayList<String>> taula) {
            this.taula = taula;
            this.numItems = taula.size() - 1;
            this.numAtributs = taula.get(0).size();
        }

        @Override
        public int obtenirNumItems() {
            return numItems;
        }

        @Override
        public int obtenirNumAtributs() {
            return numAtributs;
        }

        @Override
        public ArrayList<String> obtenirNomsAtributs() throws IllegalStateException {
            return taula.get(0);
        }

        @Override
        public String obtenirValorAtribut(int indexItem, String atribut) throws IllegalStateException, IllegalArgumentException {
            for (int i = 0; i < taula.get(0).size(); ++i) {
                if (taula.get(0).get(i).equals(atribut)) {
                    return taula.get(indexItem + 1).get(i);
                }
            }
            throw new IllegalStateException("No s'ha trobat l'atribut en aquesta taula.");
        }
    }
}