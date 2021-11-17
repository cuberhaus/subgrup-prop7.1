package domini.tests.recomanador;

import domini.classes.recomanador.ConjuntDePunts;
import domini.classes.recomanador.Punt;

import static org.junit.jupiter.api.Assertions.*;

class ConjuntDePuntsTest {

    @org.junit.jupiter.api.Test
    void getDimensio() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        assertEquals(-1, conjunt.obtenirDimensio());
        Punt p = new Punt();
        p.add(0.0);
        p.add(0.0);
        conjunt.add(p);
        assertEquals(2, conjunt.obtenirDimensio());
    }

    @org.junit.jupiter.api.Test
    void getNumPunts() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        conjunt.add(new Punt());
        conjunt.add(new Punt());
        assertEquals(2, conjunt.obtenirNumPunts());
    }

    @org.junit.jupiter.api.Test
    void add() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        Punt p = new Punt();
        p.add(0.0);
        p.add(0.0);
        conjunt.add(p);
        Punt q = new Punt();
        assertThrows(IllegalArgumentException.class, () -> conjunt.add(q));
        q.add(1.);
        q.add(0.);
        assertDoesNotThrow(() -> conjunt.add(q) );
        assertEquals(1. , conjunt.get(1).get(0));
    }

    @org.junit.jupiter.api.Test
    void getBaricentre() {
        ConjuntDePunts conjunt = new ConjuntDePunts();
        Punt p = new Punt();
        p.add(2.0);
        p.add(3.0);
        conjunt.add(p);

        Punt baricentre = conjunt.obtenirBaricentre();
        assertEquals(2., baricentre.get(0));
        assertEquals(3., baricentre.get(1));

        Punt q = new Punt();
        q.add(-1.);
        q.add(14.);
        conjunt.add(q);

        baricentre = conjunt.obtenirBaricentre();
        assertEquals(0.5, baricentre.get(0));
        assertEquals(8.5, baricentre.get(1));

        conjunt.remove(1);

        baricentre = conjunt.obtenirBaricentre();
        assertEquals(2., baricentre.get(0));
        assertEquals(3., baricentre.get(1));
    }

}