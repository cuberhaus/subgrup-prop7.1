package domini.classes.metode_recomanador;

import domini.classes.Id;
import domini.classes.Pair;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ConjuntRecomanacionsTest {
    double delta = 1e-5;
    @Test
    public void calculaDiscountedCumulativeGain() {
        // Ordre recomanat: 1 2 3 4 5
        // Valoracions:     2 3 8 7 5
        // DCG = (2^2-1) + (2^3-1)/log_2(3) + (2^8-1)/log_2(4) + (2^7-1)/log_2(5) + (2^5-1)/log_2(6) = 201.604868175
        ConjuntRecomanacions recomanacions = new ConjuntRecomanacions();
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(1, true), 5));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(2, true), 4));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(3, true), 3));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(4, true), 2));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(5, true), 1));

        ArrayList<Pair<Integer, Double>> valoracions = new ArrayList<>();
        valoracions.add(new Pair<>(1, 2.));
        valoracions.add(new Pair<>(2, 3.));
        valoracions.add(new Pair<>(3, 8.));
        valoracions.add(new Pair<>(4, 7.));
        valoracions.add(new Pair<>(5, 5.));
        assertEquals(201.604868175, recomanacions.calculaDiscountedCumulativeGain(valoracions), delta);
        assertEquals(201.604868175, recomanacions.calculaDiscountedCumulativeGain(valoracions, 5), delta);
    }

    @Test
    public void calculaIdealDiscountedCumulativeGain() {
        // Valoracions:     2 3 8 7 5
        // IDCG = (2^8-1) + (2^7-1)/log_2(3) + (2^5-1)/log_2(4) + (2^3-1)/log_2(5) + (2^2-1)/log_2(6) = 354.803373032
        ConjuntRecomanacions recomanacions = new ConjuntRecomanacions();
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(1, true), 5));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(2, true), 4));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(3, true), 3));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(4, true), 2));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(5, true), 1));

        ArrayList<Pair<Integer, Double>> valoracions = new ArrayList<>();
        valoracions.add(new Pair<>(1, 2.));
        valoracions.add(new Pair<>(2, 3.));
        valoracions.add(new Pair<>(3, 8.));
        valoracions.add(new Pair<>(4, 7.));
        valoracions.add(new Pair<>(5, 5.));
        assertEquals(354.803373032, recomanacions.calculaIdealDiscountedCumulativeGain(valoracions, 5), delta);
    }

    @Test
    public void calculaNormalizedDiscountedCumulativeGain() {
        // Ordre recomanat: 1 2 3 4 5
        // Valoracions:     2 3 8 7 5
        // NDCG = 201.604868175 / 354.803373032 = 0.56821575976
        ConjuntRecomanacions recomanacions = new ConjuntRecomanacions();
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(1, true), 5));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(2, true), 4));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(3, true), 3));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(4, true), 2));
        recomanacions.afegirRecomanacio(new Recomanacio(new Id(5, true), 1));

        ArrayList<Pair<Integer, Double>> valoracions = new ArrayList<>();
        valoracions.add(new Pair<>(1, 2.));
        valoracions.add(new Pair<>(2, 3.));
        valoracions.add(new Pair<>(3, 8.));
        valoracions.add(new Pair<>(4, 7.));
        valoracions.add(new Pair<>(5, 5.));
        assertEquals(0.56821575976, recomanacions.calculaNormalizedDiscountedCumulativeGain(valoracions), delta);
        assertEquals(0.56821575976, recomanacions.calculaNormalizedDiscountedCumulativeGain(valoracions, 5), delta);
        assertEquals(354.803373032, recomanacions.obteIdealDiscountedCumulativeGain(), delta);
        assertEquals(201.604868175, recomanacions.obteDiscountedCumulativeGain(), delta);
    }

}