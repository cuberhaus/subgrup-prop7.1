package domini.classes;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ConjuntUsuarisTest {

    @Test
    public void inicialitzarUsuaris() throws IOException {
        String ubicacio = "/home/pol/fib/PROP/Projecte/BD/250/ratings.db.csv";
        ConjuntUsuaris c1 = new ConjuntUsuaris();
        c1.inicialitzarUsuaris(ubicacio);
    }

    @Test
    public void esborraUsuari() {
    }
}