package domini.tests;

import domini.classes.Programa;
import domini.classes.Usuari;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramaTest {

    @Test
    void obtenirInstancia() {
        Programa p1 = Programa.obtenirInstancia();
        Programa p2 = Programa.obtenirInstancia();

        assertEquals(p1,p2);
        System.out.println(getClass(p1));
    }

    private String getClass(Programa p) {
        return "The class is of type Programa";
    }

    @Test
    void tancarSessio() {
    }

    @Test
    void iniciarSessio() {
        Programa p1 = Programa.obtenirInstancia();
        Usuari u1 = new Usuari(3,true,"Pol","1234");
        p1.iniciarSessio(u1);
        System.out.println("La sessio esta iniciada: " + p1.isSessioIniciada());
        System.out.println("La sessio esta iniciada: " + p1.());
    }

    @Test
    void cambiarEstat() {
    }

    @Test
    void isSessioIniciada() {
    }

    @Test
    void afegirUsuari() {
    }

    @Test
    void afegirTipusItem() {
    }

    @Test
    void esborraUsuari() {
    }
}