import domini.classes.recomanador.metode_recomanador.ConjuntPunts;
import domini.classes.recomanador.metode_recomanador.Punt;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConjuntPuntsTest {
    double delta = 1e-5;
    @Test
    public void getDimensio() {
        ConjuntPunts conjunt = new ConjuntPunts();
        assertEquals(-1, conjunt.obtenirDimensio());
        Punt p = new Punt();
        p.add(0.0);
        p.add(0.0);
        conjunt.add(p);
        assertEquals(2, conjunt.obtenirDimensio());
    }

    @Test
    public void getNumPunts() {
        ConjuntPunts conjunt = new ConjuntPunts();
        conjunt.add(new Punt());
        conjunt.add(new Punt());
        assertEquals(2, conjunt.obtenirNumPunts());
    }

    @Test
    public void add() {
        ConjuntPunts conjunt = new ConjuntPunts();
        Punt p = new Punt();
        p.add(0.0);
        p.add(0.0);
        conjunt.add(p);
        Punt q = new Punt();
        q.add(1.);
        q.add(0.);
        conjunt.add(q);
        assertEquals(1. , conjunt.get(1).get(0), delta);
    }
    @Test(expected = IllegalArgumentException.class)
    public void addThrows() {
        ConjuntPunts conjunt = new ConjuntPunts();
        Punt p = new Punt();
        p.add(0.0);
        p.add(0.0);
        conjunt.add(p);
        Punt q = new Punt();
        conjunt.add(q);
    }

    @Test
    public void getBaricentre() {
        ConjuntPunts conjunt = new ConjuntPunts();
        Punt p = new Punt();
        p.add(2.0);
        p.add(3.0);
        conjunt.add(p);

        Punt baricentre = conjunt.obtenirBaricentre();
        assertEquals(2., baricentre.get(0), delta);
        assertEquals(3., baricentre.get(1), delta);

        Punt q = new Punt();
        q.add(-1.);
        q.add(14.);
        conjunt.add(q);

        baricentre = conjunt.obtenirBaricentre();
        assertEquals(0.5, baricentre.get(0), delta);
        assertEquals(8.5, baricentre.get(1), delta);

        conjunt.remove(1);

        baricentre = conjunt.obtenirBaricentre();
        assertEquals(2., baricentre.get(0), delta);
        assertEquals(3., baricentre.get(1), delta);
    }

}