import domini.classes.Programa;
import domini.classes.SessioIniciada;
import domini.classes.Usuari;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProgramaTest {

    @Test
    public void obtenirInstancia() {
        Programa p1 = Programa.obtenirInstancia();
        Programa p2 = Programa.obtenirInstancia();

        assertEquals(p1,p2);
        System.out.println(getClass(p1));
    }

    public String getClass(Programa p) {
        return "The class is of type Programa";
    }

    @Test
    public void tancarSessio() {
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3,true,"Pol","1234");
        p1.iniciarSessio(u1);
        p1.tancarSessio();
        assertFalse(p1.isSessioIniciada());
        System.out.println("La sessio esta iniciada: " + p1.isSessioIniciada());
    }

    @Test
    public void iniciarSessio() {
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3,true,"Pol","1234");
        p1.iniciarSessio(u1);
        assertTrue(p1.isSessioIniciada());
        System.out.println("La sessio esta iniciada: " + p1.isSessioIniciada());
    }

    @Test
    public void obtenirUsuariSessioIniciada() {
    }

    /**
     * Provem a preguntar si la sessió iniciada en els 3 casos possibles, quan encara
     * no hem executat cap funció, i després d'iniciar i tancar sessio.
     */
    @Test
    public void isSessioIniciada() {
        Programa p1 = Programa.obtenirInstancia();
        assertFalse(p1.isSessioIniciada());
        System.out.println("La sessio esta iniciada: " + p1.isSessioIniciada());

        Usuari u1 = new Usuari(3,true,"Pol","1234");
        p1.iniciarSessio(u1);
        assertTrue(p1.isSessioIniciada());
        System.out.println("La sessio esta iniciada: " + p1.isSessioIniciada());

        p1.tancarSessio();
        assertFalse(p1.isSessioIniciada());
        System.out.println("La sessio esta iniciada: " + p1.isSessioIniciada());
    }

    @Test
    public void afegirUsuari() {
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3,true,"Pol","1234");
        Usuari u2 = new Usuari(4,true,"Marta","4321");
        p1.afegirUsuari(u1);
        assertTrue(p1.conteUsuari(u1));
        assertFalse(p1.conteUsuari(u2));
    }

    @Test
    public void afegirTipusItem() {
    }

    @Test
    public void esborraUsuari() {
        Programa p1 = Programa.obtenirInstancia();
//        p1.
    }
}