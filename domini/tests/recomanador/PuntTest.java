package domini.tests.recomanador;

import domini.classes.recomanador.Punt;

import static org.junit.jupiter.api.Assertions.*;

class PuntTest {
    @org.junit.jupiter.api.Test
    void getDimensio() {
        Punt p = new Punt();
        assertEquals(0, p.obtenirDimensio());
        p.add(-1.);
        p.add(-2.);
        assertEquals(2, p.obtenirDimensio());
    }

    @org.junit.jupiter.api.Test
    void afegir() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = new Punt();
        assertThrows(IllegalArgumentException.class, () -> p.afegir(q));
        q.add(2.);
        q.add(6.);
        assertDoesNotThrow(()-> p.afegir(q));
        assertEquals(1., p.get(0));
        assertEquals(4., p.get(1));
    }
}