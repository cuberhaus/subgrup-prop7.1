package metode_recomanador;

import domini.classes.metode_recomanador.Punt;

import org.junit.Test;

import static org.junit.Assert.*;

public class PuntTest {
    @Test
    public void getDimensio() {
        Punt p = new Punt();
        assertEquals(0, p.obtenirDimensio());
        p.add(-1.);
        p.add(-2.);
        assertEquals(2, p.obtenirDimensio());
    }

    @Test
    public void afegir() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = new Punt();
        q.add(2.);
        q.add(6.);
        p.afegir(q);
        assertEquals(1., p.get(0), 0.000001);
        assertEquals(4., p.get(1), 0.000001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void afegirThrows() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = new Punt();
        p.afegir(q);
    }
}