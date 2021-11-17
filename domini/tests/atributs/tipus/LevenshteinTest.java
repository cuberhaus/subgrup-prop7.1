package domini.tests.atributs.tipus;

import domini.classes.atributs.tipus.Levenshtein;
import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.valors.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

@RunWith(Enclosed.class)
class LevenshteinTest {

    private final static double delta = 1e-10;
    private final static Levenshtein levenshtein = new Levenshtein();

    public static class NonParametrizedLevenshteinTest {
        @Test
        public void copy_HauriaDeRetornarCopia() {
            TipusAtribut copia = levenshtein.copy();
            assertNotSame(copia, levenshtein);
            assertEquals(copia, levenshtein);
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsSonDeClassesDiferents() {
            levenshtein.obtenirDistancia(new ValorBoolea(true), new ValorNumeric(1.0));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
            levenshtein.obtenirDistancia(new ValorBoolea(true), new ValorBoolea(false));
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedLevenshteinTest {
        private final ValorAtribut<?> valor1;
        private final ValorAtribut<?> valor2;
        private final double resultat;

        public ParametrizedLevenshteinTest(ValorAtribut<?> valor1, ValorAtribut<?> valor2, double resultat) {
            this.valor1 = valor1;
            this.valor2 = valor2;
            this.resultat = resultat;
        }

        @Parameterized.Parameters(name = "{index}: obtenirDistancia")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    // ValorCategoric
                    {new ValorCategoric("foobar"), new ValorCategoric("foobar"), 0.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("foobar0"), 1.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("foo"), 3.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("fobar"), 1.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("foofoobarbar"), 6.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("fOObar"), 2.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("foOba"), 2.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("1foOba"), 3.0},
                    // ValorTextual
                    {new ValorTextual("foobar"), new ValorTextual("foobar"), 0.0},
                    {new ValorTextual("foobar"), new ValorTextual("foobar0"), 1.0},
                    {new ValorTextual("foobar"), new ValorTextual("foo"), 3.0},
                    {new ValorTextual("foobar"), new ValorTextual("fobar"), 1.0},
                    {new ValorTextual("foobar"), new ValorTextual("foofoobarbar"), 6.0},
                    {new ValorTextual("foobar"), new ValorTextual("fOObar"), 2.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("foOba"), 2.0},
                    {new ValorCategoric("foobar"), new ValorCategoric("1foOba"), 3.0},
            });
        }

        @Test
        public void obtenirDistancia_HauriaDeRetornarDistancia() {
            assertEquals(levenshtein.obtenirDistancia(valor1, valor2), resultat, delta);
        }
    }
}