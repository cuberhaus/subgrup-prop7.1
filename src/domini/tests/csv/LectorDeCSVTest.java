package csv;

import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class LectorDeCSVTest {

    //La taula llegida té tots els atributs i tots els items
    @Test
    public void llegirCSV() throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV tauleta = lector.llegirCSV("src/domini/tests/jocs_de_proves/joc1/items.csv");
        int tamanyItems = tauleta.obtenirNumItems();
        int tamanyAtributs = tauleta.obtenirNumAtributs();
        assertEquals(25, tamanyAtributs);
        assertEquals(250, tamanyItems);
    }

    //No existeix el fitxer
    @Test(expected = IOException.class)
    public void llegirCSVExcepcioNoExisteixFitxer() throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV tauleta = lector.llegirCSV("rc/domingo/tests/jocs_de_proves/joc1/items.domini.tests.csv");
    }


}