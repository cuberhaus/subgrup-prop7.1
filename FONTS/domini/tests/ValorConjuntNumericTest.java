package domini.tests;

import domini.classes.atributs.valors.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests de la classe ValorConjuntNumeric
 */

@RunWith(Enclosed.class)
public class ValorConjuntNumericTest {
    @RunWith(Parameterized.class)
    public static class ParametrizedConstructorStringTest {
        private final String valor;
        private final ArrayList<ValorAtribut<Double>> esperat;

        public ParametrizedConstructorStringTest(String valor, ArrayList<ValorAtribut<Double>>  esperat) {
            this.valor = valor;
            this.esperat = esperat;
        }

        @Parameterized.Parameters(name = "{index}: constructor amb String")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {null, null},
                    {"", new ArrayList<ValorAtribut<Double>>()},
                    {"0.0;1.111;;-11.0;", new ArrayList<ValorAtribut<Double>>(Arrays.asList(
                            new ValorNumeric(0.0), new ValorNumeric(1.111), new ValorNumeric((Double) null),
                            new ValorNumeric(-11.0)))}
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorConjuntNumeric() {
            assertEquals(new ValorConjuntNumeric(valor), new ValorConjuntNumeric(esperat));
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedConstructorArrayDoublesTest {
        private final double[] valor;
        private final ArrayList<ValorAtribut<Double>> esperat;

        public ParametrizedConstructorArrayDoublesTest(double[] valor, ArrayList<ValorAtribut<Double>>  esperat) {
            this.valor = valor;
            this.esperat = esperat;
        }

        @Parameterized.Parameters(name = "{index}: constructor amb Array de doubles")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {null, null},
                    {new double[]{}, new ArrayList<ValorAtribut<Double>>()},
                    {new double[]{0.0, 1.111, -11.0}, new ArrayList<ValorAtribut<Double>>(Arrays.asList(
                            new ValorNumeric(0.0), new ValorNumeric(1.111), new ValorNumeric(-11.0)))}
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorConjuntNumeric() {
            assertEquals(new ValorConjuntNumeric(valor), new ValorConjuntNumeric(esperat));
        }
    }
}