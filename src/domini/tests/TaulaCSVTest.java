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
    @Test(expected = IllegalStateException.class)
    public void introduirListaAtributsDosCops() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("hores");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.afegirConjuntAtributs(atributs);
    }

    //Probem que funciona correctament
    @Test
    public void introduirListaAtributsFuncionaCorrectament() {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("hores");
        tauleta.afegirConjuntAtributs(atributs);
        ArrayList<String> atributsTaula = tauleta.obtenirNomsAtributs();
        assertEquals(atributsTaula, atributs);
    }

    //Si no hem inicialitzat amb atributs la taula abans excepcio
    @Test(expected = IllegalStateException.class)
    public void introduirLlistaDeValorsLlencaExcepcioSiNoInicialitzat() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> valors = new ArrayList<>();
        valors.add("hola");
        valors.add("pol");
        tauleta.afegirConjuntValors(valors);
    }

    //Si no tenim la mateixa quantitat de valors que d'atributs
    @Test(expected = IllegalArgumentException.class)
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
    public void introduirLlistaDeValorsFuncionaCorrectament() {
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
    @Test(expected = IllegalStateException.class)
    public void obtenirValorsAtributTaulaNoInicialitzada() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsAtribut("id");
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsAtributExcepcioSiAtributNoExisteix() throws IllegalArgumentException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.obtenirValorsAtribut("id");
    }

    //Funciona correctament
    @Test
    public void obtenirValorsAtributFuncionaCorrectament(){
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
    @Test(expected = IllegalStateException.class)
    public void testObtenirValorsAtributExcepcioSiNoInicialitzada() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsAtribut(1);
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsAtributExcepcioSiIndexNoCorresponAtribut() throws IllegalArgumentException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tauleta.afegirConjuntAtributs(atributs);
        tauleta.obtenirValorsAtribut(1);
    }

    //Funciona correctament
    @Test
    public void obtenirValorsAtributFuncionaCorrectamentAmbIndex() {
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
    @Test(expected = IllegalStateException.class)
    public void obtenirItemExcepcioSiNoInicialitzat() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirItem(1);
    }

    //No existeix l'item, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirItemExepcioSiIndexNoCorresponItem() throws IllegalArgumentException, IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntValors(valors);

        System.out.println("Arribo");
        tauleta.obtenirItem(1);
    }

    //Existeix l'item, excepcio
    @Test
    public void obtenirItemFuncionaCorrectament() {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntValors(valors);

        System.out.println("Arribo");
        ArrayList<String> compara = tauleta.obtenirItem(0);
        assertEquals(valors, compara);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = IllegalStateException.class)
    public void obtenirLlistaAtributsExcepcioSiNoInicialitzat() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirNomsAtributs();
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = IllegalStateException.class)
    public void obtenirValorsDeTotsElsItemsExcepcioSiNoInicialitzat() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsDeTotsElsItems();
    }

    //Funciona correctament
    @Test
    public void obtenirValorsDeTotsElsItemsFuncionaCorrectament() {
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
    @Test(expected = IllegalStateException.class)
    public void obtenirTaulaExcepcioTaulaNoInicialitzada() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirTaula();
    }

    //Funciona correctament
    @Test
    public void obtenirTaulaFuncionaCorrectament() {
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
    @Test(expected = IllegalStateException.class)
    public void imprimirExepcioTaulaNoInicialitzada() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.imprimir();
    }

    //Si no estan els atributs inicialitzats, excepcio
    @Test(expected = IllegalStateException.class)
    public void obtenirValorAtributItemExcepcioSiTaulaNoInicialitzada() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorAtribut(1, "hola");
    }

    //Si no estan els atributs inicialitzats, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorAtributItemExcepcioSiNoExisteixLobjecte() throws IllegalArgumentException, IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntValors(valors);

        System.out.println("Arribo");
        tauleta.obtenirValorAtribut(1, "hola");
    }

    //Si no existeix l'index de l'item, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorAtributItemExcepcioSiNoIndexAtribut() throws IllegalArgumentException, IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.afegirConjuntAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.afegirConjuntValors(valors);

        System.out.println("Arribo");
        tauleta.obtenirValorAtribut(3, "genere");
    }
}