import domini.classes.ConjuntUsuaris;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ConjuntUsuarisTest {

    @Test
    public void inicialitzarUsuaris() throws IOException {
        String ubicacio = "./domini/tests/jocs/joc1/ratings.test.known.csv";
        ConjuntUsuaris c1 = new ConjuntUsuaris();
//        c1.inicialitzarUsuaris(ubicacio);
    }

    @Test
    public void esborraUsuari() {
    }
}