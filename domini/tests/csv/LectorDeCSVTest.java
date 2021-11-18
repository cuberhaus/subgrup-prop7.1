package csv;

import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class LectorDeCSVTest {

    //La taula llegida té tots els atributs i tots els items
    @Test
    public void llegirCSV() throws IOException, InterruptedException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV tablita = lector.llegirCSV("./domini/tests/jocs_de_proves/joc1/items.csv");
        int tamanoItems = tablita.obtenirNumeroElements();
        int tamanoAtributs = tablita.obtenirNumeroAtrib();
        assertEquals(25, tamanoAtributs);
        assertEquals(250, tamanoItems);
    }
}