package domini.tests.csv;

import domini.classes.TaulaCSV;
import domini.classes.atributs.valors.ValorAtribut;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TaulaCSVTest {

    //Probem que no podem assignar dos cops els atributs a la taula
    @Test(expected = InterruptedException.class)
    public void introduirListaAtributs() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("hores");
        tablita.introduirListaAtributs(atributs);
        tablita.introduirListaAtributs(atributs);
    }

    //Si no hem inicialitzat amb atributs la taula abans excepcio
    @Test(expected = InterruptedException.class)
    public void introduirLlistaDeValors() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        ArrayList<String> valors = new ArrayList<>();
        valors.add("hola");
        valors.add("pol");
        tablita.introduirLlistaDeValors(valors);
    }

    //Si no tenim la mateixa quantitat de valors que d'atributs
    @Test(expected = IllegalArgumentException.class)
    public void introduirLlistaDeValors1() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        ArrayList<String> valors = new ArrayList<>();
        atributs.add("genere");
        valors.add("hola");
        valors.add("pol");
        tablita.introduirListaAtributs(atributs);
        tablita.introduirLlistaDeValors(valors);
    }

    //Dona excepcio si no s'ha inicialitzat la taula previament
    @Test(expected = InterruptedException.class)
    public void obtenirValorsAtribut() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.obtenirValorsAtribut("id");
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsAtribut1() throws IllegalArgumentException, InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tablita.introduirListaAtributs(atributs);
        tablita.obtenirValorsAtribut("id");
    }

    //Dona excepcio si no s'han inicialitzat els atributs
    @Test(expected = InterruptedException.class)
    public void testObtenirValorsAtribut() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.obtenirValorsAtribut(1);
    }

    //Dona excepcio si no existeix l'atribut
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsAtribut2() throws IllegalArgumentException, InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("hola");
        tablita.introduirListaAtributs(atributs);
        tablita.obtenirValorsAtribut(1);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = InterruptedException.class)
    public void obtenirItem() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.obtenirItem(1);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = IllegalArgumentException.class)
    public void obtenirItem1() throws IllegalArgumentException, InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("genere");
        atributs.add("edad");
        tablita.introduirListaAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("home");
        valors.add("20");
        tablita.introduirLlistaDeValors(valors);

        System.out.println("Llego");
        tablita.obtenirItem(1);
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = InterruptedException.class)
    public void obtenirLlistaAtributs() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.obtenirLlistaAtributs();
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = InterruptedException.class)
    public void obtenirValorsDeTotsElsItems() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.obtenirValorsDeTotsElsItems();
    }

    //Excepcio si no s'ha inicialitzat la taula
    @Test(expected = InterruptedException.class)
    public void obtenirTaula() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.obtenirTaula();
    }

    //Si no s'ha inicialitzat excepcio
    @Test(expected = InterruptedException.class)
    public void imprimir() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.imprimir();
    }

    //Si no s'ha incialitzat excepcio
    @Test(expected = InterruptedException.class)
    public void obtenirValorsDeItemSeleccionat() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.obtenirValorsDeItemSeleccionat("hola", "adios");
    }

    //Si no s'ha incialitzat excepcio
    @Test(expected = IllegalArgumentException.class)
    public void obtenirValorsDeItemSeleccionat1() throws IllegalArgumentException, InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("edad");
        atributs.add("genere");
        tablita.introduirListaAtributs(atributs);

        ArrayList<String> valors = new ArrayList<>();
        valors.add("16");
        valors.add("mujer");
        System.out.println("Llego");
        tablita.obtenirValorsDeItemSeleccionat("hola", "adios");
    }

    @Test
    public void testObtenirValorsDeItemSeleccionat() {
    }

    @Test
    public void obtenirValorAtributItem() {
    }

    @Test
    public void estaInicialitzat() {
    }

    @Test
    public void imprimirNumeroElements() {
    }

    @Test
    public void obtenirNumeroElements() {
    }

    @Test
    public void obtenirNumeroAtrib() {
    }
}