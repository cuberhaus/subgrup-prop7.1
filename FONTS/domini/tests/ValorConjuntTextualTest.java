package domini.tests;

import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorTextual;
import domini.classes.atributs.valors.ValorConjuntTextual;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests de la classe ValorConjuntTextual
 */

@RunWith(Enclosed.class)
public class ValorConjuntTextualTest {
    @RunWith(Parameterized.class)
    public static class ParametrizedConstructorStringTest {
        private final String valor;
        private final ArrayList<ValorAtribut<String>> esperat;

        public ParametrizedConstructorStringTest(String valor, ArrayList<ValorAtribut<String>>  esperat) {
            this.valor = valor;
            this.esperat = esperat;
        }

        @Parameterized.Parameters(name = "{index}: constructor amb String")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {null, null},
                    {"", new ArrayList<ValorAtribut<String>>(List.of(new ValorTextual("")))},
                    {"a;b;c;;abc;", new ArrayList<ValorAtribut<String>>(Arrays.asList(
                            new ValorTextual("a"), new ValorTextual("b"), new ValorTextual("c"), new ValorTextual(""),
                            new ValorTextual("abc")))}
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorConjuntTextual() {
            assertEquals(new ValorConjuntTextual(valor), new ValorConjuntTextual(esperat));
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedConstructorArrayStringsTest {
        private final String[] valor;
        private final ArrayList<ValorAtribut<String>> esperat;

        public ParametrizedConstructorArrayStringsTest(String[] valor, ArrayList<ValorAtribut<String>>  esperat) {
            this.valor = valor;
            this.esperat = esperat;
        }

        @Parameterized.Parameters(name = "{index}: constructor amb Array de Strings")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {null, null},
                    {new String[]{}, new ArrayList<ValorAtribut<String>>()},
                    {new String[]{"a", "b", "c", "abc"}, new ArrayList<ValorAtribut<String>>(Arrays.asList(
                            new ValorTextual("a"), new ValorTextual("b"), new ValorTextual("c"),
                            new ValorTextual("abc")))}
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorConjuntTextual() {
            assertEquals(new ValorConjuntTextual(valor), new ValorConjuntTextual(esperat));
        }
    }
}