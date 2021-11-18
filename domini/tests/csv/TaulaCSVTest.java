package domini.tests.csv;

import domini.classes.csv.TaulaCSV;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TaulaCSVTest {

    //Probem que no podem assignar dos cops els atributs a la taula
    @Test(expected = IllegalStateException.class)
    public void introduirListaAtributs() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("hores");
        tauleta.introduirListaAtributs(atributs);
        tauleta.introduirListaAtributs(atributs);
    }

    //Probem que no podem assignar dos cops els atributs a la taula, supose, que la funcio obtenir atributs funciona,
    //testejada a la part d'abaix
    @Test
    public void introduirListaAtributs2() {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("hores");
        tauleta.introduirListaAtributs(atributs);
        ArrayList<String> atributsTaula = tauleta.obtenirLlistaAtributs();
        assertEquals(atributsTaula, atributs);
    }

    //Si no hem inicialitzat amb atributs la taula abans excepcio
    @Test(expected = IllegalStateException.class)
    public void introduirLlistaDeValors() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> valors = new ArrayList<>();
        valors.add("hola");
        valors.add("pol");
        tauleta.introduirLlistaDeValors(valors);
    }

    //Si no tenim la mateixa quantitat de valors que d'atributs
    @Test(expected = IllegalArgumentException.class)
    public void introduirLlistaDeValors1() {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        ArrayList<String> valors = new ArrayList<>();
        atributs.add("genere");
        valors.add("hola");
        valors.add("pol");
        tauleta.introduirListaAtributs(atributs);
        tauleta.introduirLlistaDeValors(valors);
    }

    //Dona excepcio si no s'ha inicialitzat la taula previament
    @Test(expected = IllegalStateException.class)
    public void obtenirValorsAtribut() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsAtribut("id");
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsAtribut1() throws IllegalArgumentException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tauleta.introduirListaAtributs(atributs);
        tauleta.obtenirValorsAtribut("id");
    }

    //Dona excepcio si no s'han inicialitzat els atributs
    @Test(expected = IllegalStateException.class)
    public void testObtenirValorsAtribut() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsAtribut(1);
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsAtribut2() throws IllegalArgumentException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tauleta.introduirListaAtributs(atributs);
        tauleta.obtenirValorsAtribut(1);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = IllegalStateException.class)
    public void obtenirItem() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirItem(1);
    }

    //No existeix l'item, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirItem1() throws IllegalArgumentException, IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.introduirListaAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.introduirLlistaDeValors(valors);

        System.out.println("Arribo");
        tauleta.obtenirItem(1);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = IllegalStateException.class)
    public void obtenirLlistaAtributs() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirLlistaAtributs();
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = IllegalStateException.class)
    public void obtenirValorsDeTotsElsItems() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsDeTotsElsItems();
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = IllegalStateException.class)
    public void obtenirTaula() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirTaula();
    }

    //Si no s'ha inicialitzat excepcio
    @Test(expected = IllegalStateException.class)
    public void imprimir() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.imprimir();
    }

    //Si no s'ha incialitzat excepcio
    @Test(expected = IllegalStateException.class)
    public void obtenirValorsDeItemSeleccionat() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsDeItemSeleccionat("hola", "adios");
    }

    //Si no existeix un atribut amb aquell nom, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsDeItemSeleccionat1() throws IllegalArgumentException, IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("edad");
        atributs.add("genere");
        tauleta.introduirListaAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("16");
        valors.add("mujer");
        System.out.println("Arribo");
        tauleta.obtenirValorsDeItemSeleccionat("hola", "adios");
    }

    //Si no s'ha inicialitzat la taula excepcio
    @Test(expected = IllegalStateException.class)
    public void testObtenirValorsDeItemSeleccionat() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsDeItemSeleccionat(1, "adios");
    }

    //Si l'index no té atribut, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void testObtenirValorsDeItemSeleccionat1() throws IllegalStateException, IllegalArgumentException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("edad");
        atributs.add("genere");
        tauleta.introduirListaAtributs(atributs);

        System.out.println("Arribo");
        tauleta.obtenirValorsDeItemSeleccionat(3, "hola");

    }

    //Si no estan els atributs inicialitzats, excepcio
    @Test(expected = IllegalStateException.class)
    public void obtenirValorAtributItem() throws IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorAtributItem(1, "hola");
    }

    //Si no estan els atributs inicialitzats, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorAtributItem1() throws IllegalArgumentException, IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.introduirListaAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.introduirLlistaDeValors(valors);

        System.out.println("Arribo");
        tauleta.obtenirValorAtributItem(1, "hola");
    }

    //Si no existeix l'index de l'item, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorAtributItem2() throws IllegalArgumentException, IllegalStateException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tauleta.introduirListaAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tauleta.introduirLlistaDeValors(valors);

        System.out.println("Arribo");
        tauleta.obtenirValorAtributItem(3, "genere");
    }
}