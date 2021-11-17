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

    @Test
    public void introduirLlistaDeValors() {
    }

    @Test
    public void obtenirValorsAtribut() {
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