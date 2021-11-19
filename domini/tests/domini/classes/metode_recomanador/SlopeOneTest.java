package domini.classes.metode_recomanador;

import org.junit.Test;

import static org.junit.Assert.*;

public class SlopeOneTest {

    double delta = 1e-5;
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

}