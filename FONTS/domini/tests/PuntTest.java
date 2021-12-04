package domini.tests;

import domini.classes.recomanador.metode_recomanador.Punt;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests de la classe Punt
 */

public class PuntTest {
    private final double delta = 1e-5;
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
        assertEquals(1., p.get(0), delta);
        assertEquals(4., p.get(1), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void afegirThrows() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = new Punt();
        p.afegir(q);
    }

    @Test
    public void resta() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = new Punt();
        q.add(2.);
        q.add(6.);
        p.resta(q);
        assertEquals(-3., p.get(0), delta);
        assertEquals(-8., p.get(1), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void restaThrows() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = new Punt();
        p.resta(q);
    }

    @Test
    public void mult() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        p.mult(2.5);
        assertEquals(-2.5, p.get(0), delta);
        assertEquals(-5., p.get(1), delta);
    }

    @Test
    public void testClone() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = (Punt) p.clone();
        q.set(0, 0.);
        assertEquals(-1., p.get(0), delta);
    }

    @Test
    public void norma() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        assertEquals(5., p.norma()*p.norma(), delta);
    }
    @Test
    public void distancia() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = new Punt();
        q.add(2.);
        q.add(6.);
        assertEquals(Math.sqrt(73), p.distancia(q), delta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void distanciaThrows() {
        Punt p = new Punt();
        p.add(-1.);
        p.add(-2.);
        Punt q = new Punt();
        p.distancia(q);
    }
}