import domini.classes.atributs.valors.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Unit tests de la classe ValorBoolea
 */

@RunWith(Enclosed.class)
public class ValorBooleaTest {
    @RunWith(Parameterized.class)
    public static class ParametrizedConstructorStringTest {
        private final String valor;
        private final Boolean esperat;

        public ParametrizedConstructorStringTest(String valor, Boolean esperat) {
            this.valor = valor;
            this.esperat = esperat;
        }

        @Parameterized.Parameters(name = "{index}: constructor amb String")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {null, null},
                    {"", null},
                    {"true", true},
                    {"TRUE", true},
                    {"True", true},
                    {"TrUe", true},
                    {"false", false},
                    {"FALSE", false},
                    {"False", false},
                    {"FalSe", false},
                    {"this is not a boolean", false},
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorBoolea() {
            assertEquals(new ValorBoolea(valor), new ValorBoolea(esperat));
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedEsBooleaTest {
        private final String valor;
        private final boolean esperat;

        public ParametrizedEsBooleaTest(String valor, boolean esperat) {
            this.valor = valor;
            this.esperat = esperat;
        }

        @Parameterized.Parameters(name = "{index}: esBoolea")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {null, false},
                    {"true", true},
                    {"TRUE", true},
                    {"True", true},
                    {"TrUe", true},
                    {"false", true},
                    {"FALSE", true},
                    {"False", true},
                    {"FalSe", true},
                    {"fals", false},
                    {"Fals", false},
                    {"tru3", false},
                    {"", false},
            });
        }

        @Test
        public void esBoolea_HauriaDeRetornar_SiStringEsBoolea() {
            assertEquals(ValorBoolea.esBoolea(valor), esperat);
        }
    }
}