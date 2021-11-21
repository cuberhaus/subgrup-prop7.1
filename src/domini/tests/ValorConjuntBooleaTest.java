import domini.classes.atributs.valors.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class ValorConjuntBooleaTest {
    @RunWith(Parameterized.class)
    public static class ParametrizedConstructorStringTest {
        private final String valor;
        private final ArrayList<ValorAtribut<Boolean>> esperat;

        public ParametrizedConstructorStringTest(String valor, ArrayList<ValorAtribut<Boolean>>  esperat) {
            this.valor = valor;
            this.esperat = esperat;
        }

        @Parameterized.Parameters(name = "{index}: constructor amb String")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {null, null},
                    {"", new ArrayList<ValorAtribut<Boolean>>()},
                    {"True;False;;true;", new ArrayList<ValorAtribut<Boolean>>(Arrays.asList(
                            new ValorBoolea(true), new ValorBoolea(false), new ValorBoolea(),
                            new ValorBoolea(true)))}
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorConjuntBoolea() {
            assertEquals(new ValorConjuntBoolea(valor), new ValorConjuntBoolea(esperat));
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedConstructorArrayBooleansTest {
        private final boolean[] valor;
        private final ArrayList<ValorAtribut<Boolean>> esperat;

        public ParametrizedConstructorArrayBooleansTest(boolean[] valor, ArrayList<ValorAtribut<Boolean>>  esperat) {
            this.valor = valor;
            this.esperat = esperat;
        }

        @Parameterized.Parameters(name = "{index}: constructor amb Array de booleans")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {null, null},
                    {new boolean[]{}, new ArrayList<ValorAtribut<Double>>()},
                    {new boolean[]{true, false, true}, new ArrayList<ValorAtribut<Boolean>>(Arrays.asList(
                            new ValorBoolea(true), new ValorBoolea(false), new ValorBoolea(true)))}
            });
        }

        @Test
        public void constructorAmbString_HauriaDeCrearValorConjuntBoolea() {
            assertEquals(new ValorConjuntBoolea(valor), new ValorConjuntBoolea(esperat));
        }
    }
}