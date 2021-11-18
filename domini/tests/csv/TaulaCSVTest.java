package domini.tests.csv;

import domini.classes.csv.TaulaCSV;
import org.junit.Test;

import java.util.ArrayList;

public class TaulaCSVTest {

    //Probem que no podem assignar dos cops els atributs a la taula
    @Test(expected = InterruptedException.class)
    public void introduirListaAtributs() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("hores");
        tauleta.introduirListaAtributs(atributs);
        tauleta.introduirListaAtributs(atributs);
    }

    //Si no hem inicialitzat amb atributs la taula abans excepcio
    @Test(expected = InterruptedException.class)
    public void introduirLlistaDeValors() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> valors = new ArrayList<>();
        valors.add("hola");
        valors.add("pol");
        tauleta.introduirLlistaDeValors(valors);
    }

    //Si no tenim la mateixa quantitat de valors que d'atributs
    @Test(expected = IllegalArgumentException.class)
    public void introduirLlistaDeValors1() throws InterruptedException {
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
    @Test(expected = InterruptedException.class)
    public void obtenirValorsAtribut() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsAtribut("id");
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsAtribut1() throws IllegalArgumentException, InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tauleta.introduirListaAtributs(atributs);
        tauleta.obtenirValorsAtribut("id");
    }

    //Dona excepcio si no s'han inicialitzat els atributs
    @Test(expected = InterruptedException.class)
    public void testObtenirValorsAtribut() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsAtribut(1);
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsAtribut2() throws IllegalArgumentException, InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tauleta.introduirListaAtributs(atributs);
        tauleta.obtenirValorsAtribut(1);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = InterruptedException.class)
    public void obtenirItem() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirItem(1);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = IllegalArgumentException.class)
    public void obtenirItem1() throws IllegalArgumentException, InterruptedException {
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
    @Test(expected = InterruptedException.class)
    public void obtenirLlistaAtributs() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirLlistaAtributs();
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = InterruptedException.class)
    public void obtenirValorsDeTotsElsItems() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsDeTotsElsItems();
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = InterruptedException.class)
    public void obtenirTaula() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirTaula();
    }

    //Si no s'ha inicialitzat excepcio
    @Test(expected = InterruptedException.class)
    public void imprimir() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.imprimir();
    }

    //Si no s'ha incialitzat excepcio
    @Test(expected = InterruptedException.class)
    public void obtenirValorsDeItemSeleccionat() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsDeItemSeleccionat("hola", "adios");
    }

    //Si no existeix un atribut amb aquell nom, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsDeItemSeleccionat1() throws IllegalArgumentException, InterruptedException {
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
    @Test(expected = InterruptedException.class)
    public void testObtenirValorsDeItemSeleccionat() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorsDeItemSeleccionat(1, "adios");
    }

    //Si l'index no té atribut, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void testObtenirValorsDeItemSeleccionat1() throws InterruptedException, IllegalArgumentException {
        TaulaCSV tauleta = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("edad");
        atributs.add("genere");
        tauleta.introduirListaAtributs(atributs);

        System.out.println("Arribo");
        tauleta.obtenirValorsDeItemSeleccionat(3, "hola");

    }

    //Si no estan els atributs inicialitzats, excepcio
    @Test(expected = InterruptedException.class)
    public void obtenirValorAtributItem() throws InterruptedException {
        TaulaCSV tauleta = new TaulaCSV();
        tauleta.obtenirValorAtributItem(1, "hola");
    }

    //Si no estan els atributs inicialitzats, excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorAtributItem1() throws IllegalArgumentException, InterruptedException {
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
    @Test(expected = IllegalStateException.class)
    public void obtenirValorAtributItem2() throws IllegalArgumentException, InterruptedException, IllegalStateException {
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