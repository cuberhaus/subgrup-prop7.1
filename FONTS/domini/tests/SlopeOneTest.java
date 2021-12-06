package domini.tests;

import domini.classes.recomanador.metode_recomanador.SlopeOne;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Unit tests de la classe SlopeOne
 */

public class SlopeOneTest {
    private final double delta = 1e-5;
    private final int generadorRNG = 12345678;
    private final int modulRNG = 100000007;
    private int numeroActual = 1;

    private int seguentNumero() {
        return numeroActual = (numeroActual * generadorRNG)%modulRNG;
    }

    @Test
    public void getPrediccio() {
        // 1 2 3 4
        // 1 2 3 4
        // 1 2 3 4
        // Tots els usuaris igual
        Double[][] valoracions = new Double[3][4];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j)
                valoracions[i][j] = j+1.;
        }

        SlopeOne slopeOne = new SlopeOne(valoracions);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j)
                assertEquals(j+1, slopeOne.getPrediccio(i, j), delta);
        }

        // 1 1 1 1
        // 2 2 2 2
        // 3 3 3 3

        Double[][] valoracions2 = new Double[3][4];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j)
                valoracions2[i][j] = i+1.;
        }

        SlopeOne slopeOne2 = new SlopeOne(valoracions2);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j)
                assertEquals(i+1, slopeOne2.getPrediccio(i, j), delta);
        }
    }
    @Test
    public void getPrediccioAmbNulls() {

        // 1 1 1 1 null
        // 2 2 2 2 null
        // 3 3 3 3 null
        Double[][] valoracions3 = new Double[3][5];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j)
                valoracions3[i][j] = i+1.;
        }
        for (int i = 0; i < 3; ++i)
            valoracions3[i][4] = null;

        SlopeOne slopeOne3 = new SlopeOne(valoracions3);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j)
                assertEquals(i+1, slopeOne3.getPrediccio(i, j), delta);
        }

        // 1 1 1 1
        // 2 2 2 2
        // 3 3 3 3
        // null null null null
        Double[][] valoracions4 = new Double[4][4];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j)
                valoracions4[i][j] = i+1.;
        }
        for (int i = 0; i < 4; ++i)
            valoracions4[3][i] = null;

        SlopeOne slopeOne4 = new SlopeOne(valoracions4);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 4; ++j)
                assertEquals(i+1, slopeOne4.getPrediccio(i, j), delta);
        }
    }
    @Test
    public void casGranPrecomputat() throws IOException {
        Double[][] valoracions = new Double[100][100];
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                valoracions[i][j] = (double) ((seguentNumero() % 10 + 10) % 10);
            }
        }
        SlopeOne slopeOne = new SlopeOne(valoracions);
        File prediccionsGuardades = new File("../EXE/dades_tests/outputSlopeOneTest");
        Scanner prediccionsGuardadesReader = new Scanner(prediccionsGuardades);
        for (int i = 0; i < 100; ++i) {
            for (int j = 0; j < 100; ++j) {
                String nextDouble = prediccionsGuardadesReader.next();
                assertEquals(slopeOne.getPrediccio(i,j), Double.parseDouble(nextDouble), delta);
            }
        }
    }

}