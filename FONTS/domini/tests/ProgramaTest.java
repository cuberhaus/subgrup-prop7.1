package domini.tests;

import domini.classes.Id;
import domini.classes.Programa;
import domini.classes.Usuari;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests de les classes Programa, Sessio, SessioIniciada i SesssioNoIniciada
 */

public class ProgramaTest {
    private final Programa p1 = Programa.obtenirInstancia();
    private final Programa p2 = Programa.obtenirInstancia();

    @Test
    public void obtenirInstancia() {
        p1.reset();
        p2.reset();
        Programa p1 = Programa.obtenirInstancia();
        Programa p2 = Programa.obtenirInstancia();

        assertEquals(p1, p2);
    }

    @Test
    public void tancarSessio() throws Exception {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        p1.iniciarSessio(u1);
        p1.tancarSessio();
        assertFalse(p1.isSessioIniciada());
    }

    @Test
    public void iniciarSessio() throws Exception {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        p1.iniciarSessio(u1);
        assertTrue(p1.isSessioIniciada());
    }

    /**
     * Provem a preguntar si la sessió està iniciada en els 3 casos possibles, quan encara
     * no hem executat cap funció, i després d'iniciar i tancar sessio.
     * @throws Exception si la sessio ja esta iniciada
     */
    @Test
    public void isSessioIniciada() throws Exception {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        assertFalse(p1.isSessioIniciada());

        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        p1.iniciarSessio(u1);
        assertTrue(p1.isSessioIniciada());

        p1.tancarSessio();
        assertFalse(p1.isSessioIniciada());
    }

    @Test
    public void obtenirUsuariSessioIniciadaIniciantSessioAbans() throws Exception {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        p1.iniciarSessio(u1);
        Usuari u2 = p1.obtenirUsuariSessioIniciada();
        assertEquals(u1, u2);
    }

    @Test(expected = Exception.class)
    public void obtenirUsuariSessioIniciadaDemanantUsuariAbansDiniciarSessio() throws Exception {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u2 = p1.obtenirUsuariSessioIniciada();
    }

    @Test(expected = Exception.class)
    public void obtenirUsuariSessioIniciadaDespresDeTancarSessio() throws Exception {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        p1.iniciarSessio(u1);
        p1.tancarSessio();
        Usuari u2 = p1.obtenirUsuariSessioIniciada();
    }

    @Test
    public void conteUsuari() {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        Usuari u2 = new Usuari(5, true, "Marta", "4321");
        p1.afegirUsuari(u1);
        assertTrue(p1.conteUsuari(u1.obtenirId()));
        assertFalse(p1.conteUsuari(u2.obtenirId()));
    }

    @Test
    public void obtenirUsuari() throws Exception {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        p1.afegirUsuari(u1);
        Usuari u2 = p1.obtenirUsuari(u1.obtenirId());
        assertEquals(u1, u2);
    }

    @Test
    public void afegirUsuari() {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        Usuari u2 = new Usuari(5, true, "Marta", "4321");
        p1.afegirUsuari(u1);
        assertTrue(p1.conteUsuari(u1.obtenirId()));
        assertFalse(p1.conteUsuari(u2.obtenirId()));
    }

    @Test
    public void afegirTipusItem() {
        p1.reset();
    }

    @Test
    public void esborraUsuari() throws Exception {
        p1.reset();
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3, true, "Pol", "1234");
        p1.afegirUsuari(u1);
        p1.esborraUsuari(u1.obtenirId());

        Usuari u2 = p1.obtenirUsuari(new Id(3, true));
        assertFalse(u2.isActiu());
    }
}