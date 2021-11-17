import domini.classes.Programa;
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

    @Test
    public void cambiarEstat() {
    }

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
    }

    @Test
    public void afegirTipusItem() {
    }

    @Test
    public void esborraUsuari() {
    }
}