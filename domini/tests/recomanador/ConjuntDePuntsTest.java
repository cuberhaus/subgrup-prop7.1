package domini.tests.recomanador;

import domini.classes.recomanador.ConjuntDePunts;
import domini.classes.recomanador.Punt;

import org.junit.Test;

import static org.junit.Assert.*;

class ConjuntDePuntsTest {

    @Test
    public void getDimensio() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        assertEquals(-1, conjunt.obtenirDimensio());
        Punt p = new Punt();
        p.add(0.0);
        p.add(0.0);
        conjunt.add(p);
        assertEquals(2, conjunt.obtenirDimensio());
    }

    @Test
    public void getNumPunts() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        conjunt.add(new Punt());
        conjunt.add(new Punt());
        assertEquals(2, conjunt.obtenirNumPunts());
    }

    @Test
    public void add() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        Punt p = new Punt();
        p.add(0.0);
        p.add(0.0);
        conjunt.add(p);
        Punt q = new Punt();
        q.add(1.);
        q.add(0.);
        conjunt.add(q);
        assertEquals(1. , conjunt.get(1).get(0), 0.000001);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addThrows() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        Punt p = new Punt();
        p.add(0.0);
        p.add(0.0);
        conjunt.add(p);
        Punt q = new Punt();
        conjunt.add(q);
    }

    @Test
    public void getBaricentre() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        Punt p = new Punt();
        p.add(2.0);
        p.add(3.0);
        conjunt.add(p);

        Punt baricentre = conjunt.obtenirBaricentre();
        assertEquals(2., baricentre.get(0), 0.000001);
        assertEquals(3., baricentre.get(1), 0.000001);

        Punt q = new Punt();
        q.add(-1.);
        q.add(14.);
        conjunt.add(q);

        baricentre = conjunt.obtenirBaricentre();
        assertEquals(0.5, baricentre.get(0), 0.000001);
        assertEquals(8.5, baricentre.get(1), 0.000001);

        conjunt.remove(1);

        baricentre = conjunt.obtenirBaricentre();
        assertEquals(2., baricentre.get(0), 0.000001);
        assertEquals(3., baricentre.get(1), 0.000001);
    }

}