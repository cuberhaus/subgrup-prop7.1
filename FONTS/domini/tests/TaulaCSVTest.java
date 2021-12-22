package domini.tests;

import domini.classes.csv.TaulaCSV;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * JUnit de la classe TaulaCSV
 * @author pablo.vega
 */

public class TaulaCSVTest {

    //Probem que no podem assignar dos cops els atributs a la taula
    @Test(expected = Exception.class)
    public void introduirListaAtributsDosCops() {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("hores");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.afegirConjuntAtributs(atributs);
    }

    //Probem que funciona correctament
    @Test
    public void introduirListaAtributsFuncionaCorrectament() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("hores");
        tauleta.afegirConjuntAtributs(atributs);
        ArrayList<String> atributsTaula = tauleta.obtenirNomsAtributs();
        assertEquals(atributsTaula, atributs);
    }

    //Si no hem inicialitzat amb atributs la taula abans excepcio
    @Test(expected = Exception.class)
    public void introduirLlistaDeValorsLlencaExcepcioSiNoInicialitzat() {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> valors = new ArrayList<>();
        valors.add("hola");
        valors.add("pol");
        tauleta.afegirConjuntValors(valors);
    }

    //Si no tenim la mateixa quantitat de valors que d'atributs
    @Test(expected = Exception.class)
    public void introduirLlistaDeValorsExepcioSiNoQuantitatAtributsCorrecte() {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        ArrayList<String> valors = new ArrayList<>();
        atributs.add("genere");
        valors.add("hola");
        valors.add("pol");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.afegirConjuntValors(valors);
    }

    //Comparem que hem afegit una fila correctament
    @Test
    public void introduirLlistaDeValorsFuncionaCorrectament() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        ArrayList<String> valors = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.afegirConjuntValors(valors);
        ArrayList<String> comparar = tauleta.obtenirItem(0);
        assertEquals(valors, comparar);
    }

    //Dona excepcio si no s'ha inicialitzat la taula previament
    @Test(expected = Exception.class)
    public void obtenirValorsAtributTaulaNoInicialitzada() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsAtribut("id");
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = Exception.class)
    public void obtenirValorsAtributExcepcioSiAtributNoExisteix() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.obtenirValorsAtribut("id");
    }

    //Funciona correctament
    @Test
    public void obtenirValorsAtributFuncionaCorrectament() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        tauleta.afegirConjuntAtributs(atributs);
        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        tauleta.afegirConjuntValors(valors);
        ArrayList<String> comparar = tauleta.obtenirValorsAtribut("genere");
        assertEquals(comparar, valors);
    }


    //Dona excepcio si no s'han inicialitzat els atributs
    @Test(expected = Exception.class)
    public void testObtenirValorsAtributExcepcioSiNoInicialitzada() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsAtribut(1);
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = Exception.class)
    public void obtenirValorsAtributExcepcioSiIndexNoCorresponAtribut() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.obtenirValorsAtribut(1);
    }

    //Funciona correctament
    @Test
    public void obtenirValorsAtributFuncionaCorrectamentAmbIndex() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        ArrayList<String> valors = new ArrayList<>();
        valors.add("dona");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.afegirConjuntValors(valors);
        ArrayList<String> compara = tauleta.obtenirValorsAtribut(0);
        assertEquals(valors, compara);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = Exception.class)
    public void obtenirItemExcepcioSiNoInicialitzat() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirItem(1);
    }

    //No existeix l'item, excepcio
    @Test(expected = Exception.class)
    public void obtenirItemExepcioSiIndexNoCorresponItem() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntValors(valors);

        tauleta.obtenirItem(1);
    }

    //Existeix l'item, excepcio
    @Test
    public void obtenirItemFuncionaCorrectament() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntValors(valors);

        ArrayList<String> compara = tauleta.obtenirItem(0);
        assertEquals(valors, compara);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = Exception.class)
    public void obtenirLlistaAtributsExcepcioSiNoInicialitzat() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirNomsAtributs();
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = Exception.class)
    public void obtenirValorsDeTotsElsItemsExcepcioSiNoInicialitzat() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsDeTotsElsItems();
    }

    //Funciona correctament
    @Test
    public void obtenirValorsDeTotsElsItemsFuncionaCorrectament() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors1 = new ArrayList<>();
        valors1.add("home");
        valors1.add("20");

        ArrayList<String> valors2 = new ArrayList<>();
        valors2.add("dona");
        valors2.add("21");

        ArrayList<ArrayList<String>> conjuntValors = new ArrayList<>();
        conjuntValors.add(valors1);
        conjuntValors.add(valors2);

        tauleta.afegirConjuntValors(valors1);
        tauleta.afegirConjuntValors(valors2);
        ArrayList<ArrayList<String>> compara = tauleta.obtenirValorsDeTotsElsItems();

        assertEquals(conjuntValors, compara);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = Exception.class)
    public void obtenirTaulaExcepcioTaulaNoInicialitzada() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirTaula();
    }

    //Funciona correctament
    @Test
    public void obtenirTaulaFuncionaCorrectament() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors1 = new ArrayList<>();
        valors1.add("home");
        valors1.add("20");

        ArrayList<String> valors2 = new ArrayList<>();
        valors2.add("dona");
        valors2.add("21");

        ArrayList<ArrayList<String>> conjuntValors = new ArrayList<>();
        conjuntValors.add(atributs);
        conjuntValors.add(valors1);
        conjuntValors.add(valors2);

        tauleta.afegirConjuntValors(valors1);
        tauleta.afegirConjuntValors(valors2);
        ArrayList<ArrayList<String>> compara = tauleta.obtenirTaula();

        assertEquals(conjuntValors, compara);
    }

    //Si no s'ha inicialitzat excepcio
    @Test(expected = Exception.class)
    public void imprimirExepcioTaulaNoInicialitzada() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.imprimir();
    }

    //Si no estan els atributs inicialitzats, excepcio
    @Test(expected = Exception.class)
    public void obtenirValorAtributItemExcepcioSiTaulaNoInicialitzada() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorAtribut(1, "hola");
    }

    //Si no estan els atributs inicialitzats, excepcio
    @Test(expected = Exception.class)
    public void obtenirValorAtributItemExcepcioSiNoExisteixLobjecte() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntValors(valors);

        tauleta.obtenirValorAtribut(1, "hola");
    }

    //Si no existeix l'index de l'item, excepcio
    @Test(expected = Exception.class)
    public void obtenirValorAtributItemExcepcioSiNoIndexAtribut() throws Exception {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntValors(valors);

        tauleta.obtenirValorAtribut(3, "genere");
    }
}