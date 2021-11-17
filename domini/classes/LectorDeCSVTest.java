package domini.classes;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class LectorDeCSVTest {

    @Test
    public void llegirCSV() throws IOException, InterruptedException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV tablita = lector.llegirCSV("C:/Users/pable/Desktop/items.csv");
        int tamanoItems = tablita.obtenirNumeroElements();
        int tamanoAtributs = tablita.obtenirNumeroAtrib();
        assertEquals(25, tamanoAtributs);
        assertEquals(250, tamanoItems);
    }
}