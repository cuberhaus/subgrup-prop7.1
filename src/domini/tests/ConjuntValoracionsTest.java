import domini.classes.*;
import domini.classes.csv.TaulaCSV;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class ConjuntValoracionsTest {

    @Test
    public void afegir() {
        TaulaCSV taulaCSVratings = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>(Arrays.asList("userId", "itemId", "rating"));
        ArrayList<String> valors = new ArrayList<>(Arrays.asList("1", "10", "5"));
        taulaCSVratings.afegirConjuntAtributs(atributs);
        taulaCSVratings.afegirConjuntValors(valors);
        ConjuntUsuaris conjuntUsuaris = new ConjuntUsuaris();
        conjuntUsuaris.afegir(taulaCSVratings);

        TaulaCSV taulaItems = new TaulaCSV();
        ArrayList<String> atributs2 = new ArrayList<>(Arrays.asList("id", "title"));
        ArrayList<String> valors2 = new ArrayList<>(Arrays.asList("10", "Pirata"));
        taulaItems.afegirConjuntAtributs(atributs2);
        taulaItems.afegirConjuntValors(valors2);
        ConjuntItems conjuntItems = new ConjuntItems("pelis",taulaItems);

        ConjuntValoracions conjuntValoracions = new ConjuntValoracions();
        conjuntValoracions.afegir(taulaCSVratings,conjuntItems,conjuntUsuaris);
        Usuari u1 = new Usuari(new Id(1,true));
        TreeMap<Pair<Usuari, Item>, Valoracio> valoracions = conjuntValoracions.obteTotesValoracions();
    }

    @Test
    public void conte() {
    }

    @Test
    public void obte() {
    }

    @Test
    public void testAfegir() {
    }

    @Test
    public void esborrar() {
    }

    @Test
    public void testEsborrar() {
    }

    @Test
    public void obteTotesValoracions() {
    }
}