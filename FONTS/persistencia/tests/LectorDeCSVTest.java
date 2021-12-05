package persistencia.tests;

import persistencia.classes.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * JUnit de la classe LectorDeCSV
 * @author pablo.vega
 */

public class LectorDeCSVTest {

    //La taula llegida té tots els atributs i tots els items
    @Test
    public void llegirCSV() throws Exception {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV tauleta = new TaulaCSV(lector.llegirCSV("../EXE/dades_tests/items.csv"));
        int tamanyItems = tauleta.obtenirNumItems();
        int tamanyAtributs = tauleta.obtenirNumAtributs();
        assertEquals(25, tamanyAtributs);
        assertEquals(250, tamanyItems);
    }

    //No existeix el fitxer
    @Test(expected = IOException.class)
    public void llegirCSVExcepcioNoExisteixFitxer() throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        lector.llegirCSV("dades_tests/items_inexistent.csv");
    }


}