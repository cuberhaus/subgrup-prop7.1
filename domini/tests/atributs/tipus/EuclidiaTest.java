package domini.tests.atributs.tipus;

import domini.classes.atributs.tipus.Euclidia;
import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.valors.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Enclosed.class)
class EuclidiaTest {

    static Euclidia euclidia = new Euclidia();

    public static class NonParametrizedTest {
        @Test
        public void copy_HauriaDeRetornarCopia() {
            TipusAtribut copia = euclidia.copy();
            assertNotSame(copia, euclidia);
            assertEquals(copia, euclidia);
        }

        @Test
        public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsSonDeClassesDiferents() {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    euclidia.obtenirDistancia(new ValorBoolea(true), new ValorNumeric(1.0)));
            assertEquals("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.",
                    exception.getMessage());
        }

        @Test
        public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    euclidia.obtenirDistancia(new ValorBoolea(true), new ValorBoolea(false)));
            assertEquals("El TipusAtribut no admet el tipus dels ValorAtributs donats.", exception.getMessage());
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedTest {
        private final ValorAtribut<?> valor1;
        private final ValorAtribut<?> valor2;
        private final double resultat;

        public ParametrizedTest(ValorAtribut<?> valor1, ValorAtribut<?> valor2, double resultat) {
            this.valor1 = valor1;
            this.valor2 = valor2;
            this.resultat = resultat;
        }

        @Parameterized.Parameters(name = "{index}: obtenirDistancia")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    // ValorNumeric
                    {new ValorNumeric(0.0), new ValorNumeric(0.0), 0.0},
                    {new ValorNumeric(-1.0), new ValorNumeric(-1.0), 0.0},
                    {new ValorNumeric(0.0), new ValorNumeric(1.0), 1.0},
                    {new ValorNumeric(-1.0), new ValorNumeric(-3.0), 2.0},
                    // ValorConjuntNumeric
                    {new ValorConjuntNumeric(new double[]{0.0, 0.0}), new ValorConjuntNumeric(new double[]{0.0, 0.0}), 0.0},
                    {new ValorConjuntNumeric(new double[]{0.0, 0.0}), new ValorConjuntNumeric(new double[]{1.0, 1.0}), Math.sqrt(2)},
                    {new ValorConjuntNumeric(new double[]{0.0, 0.0}), new ValorConjuntNumeric(new double[]{-1.0, -1.0}), Math.sqrt(2)},
                    {new ValorConjuntNumeric(new double[]{1.0, 1.0, 1.0}), new ValorConjuntNumeric(new double[]{-1.0, -1.0, -1.0}), 2.0*Math.sqrt(3)},
            });
        }

        @Test
        public void obtenirDistancia_HauriaDeRetornarDistancia() {
            assertEquals(euclidia.obtenirDistancia(valor1, valor2), resultat);
        }
    }
}