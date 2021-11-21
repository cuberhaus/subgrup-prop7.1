import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.atributs.valors.ValorCategoric;
import domini.classes.atributs.valors.ValorConjuntCategoric;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class ValorConjuntCategoricTest {
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
                    {"", new ArrayList<ValorAtribut<String>>(List.of(new ValorCategoric("")))},
                    {"a;b;c;abc", new ArrayList<ValorAtribut<String>>(Arrays.asList(
                            new ValorCategoric("a"), new ValorCategoric("b"), new ValorCategoric("c"),
                            new ValorCategoric("abc")))}
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorBoolea() {
            assertEquals(new ValorConjuntCategoric(valor), new ValorConjuntCategoric(esperat));
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
                            new ValorCategoric("a"), new ValorCategoric("b"), new ValorCategoric("c"),
                            new ValorCategoric("abc")))}
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorBoolea() {
            assertEquals(new ValorConjuntCategoric(valor), new ValorConjuntCategoric(esperat));
        }
    }
}