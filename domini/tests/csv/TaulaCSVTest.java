package domini.tests.csv;

import domini.classes.TaulaCSV;
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

    @Test(expected = InterruptedException.class)
    public void obtenirValorsAtribut() throws InterruptedException {
        TaulaCSV tablita = new TaulaCSV();
        tablita.obtenirValorsAtribut("id");
    }

    @Test
    public void testObtenirValorsAtribut() {
    }

    @Test
    public void obtenirItem() {
    }

    @Test
    public void obtenirLlistaAtributs() {
    }

    @Test
    public void obtenirValorsDeTotsElsItems() {
    }

    @Test
    public void obtenirTaula() {
    }

    @Test
    public void imprimir() {
    }

    @Test
    public void obtenirValorsDeItemSeleccionat() {
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