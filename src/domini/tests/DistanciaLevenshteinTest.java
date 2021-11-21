import domini.classes.atributs.distancia.DistanciaLevenshtein;
import domini.classes.atributs.distancia.Distancia;
import domini.classes.atributs.valors.*;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class DistanciaLevenshteinTest {

    private final static double delta = 1e-10;
    private final static DistanciaLevenshtein distanciaLevenshtein = new DistanciaLevenshtein();

    public static class NonParametrizedDistanciaLevenshteinTest {
        @Test
        public void copiar_HauriaDeRetornarCopia() {
            Distancia copia = distanciaLevenshtein.copiar();
            assertNotSame(copia, distanciaLevenshtein);
            assertEquals(copia, distanciaLevenshtein);
        }

        @Test(expected = IllegalArgumentException.class)
        public void admet_HauriaDEmetreExcepcio_Quan_Nul() {
            distanciaLevenshtein.admet(null);
        }

        @Test
        public void admet_HauriaDeRetornarCert() {
            assertTrue(distanciaLevenshtein.admet(new ValorCategoric("a")));
            assertTrue(distanciaLevenshtein.admet(new ValorTextual("a")));
        }

        @Test
        public void admet_HauriaDeRetornarFals() {
            assertFalse(distanciaLevenshtein.admet(new ValorBoolea(true)));
            assertFalse(distanciaLevenshtein.admet(new ValorNumeric(0.0)));
            assertFalse(distanciaLevenshtein.admet(new ValorConjuntBoolea(new boolean[]{true})));
            assertFalse(distanciaLevenshtein.admet(new ValorConjuntCategoric(new String[]{"a"})));
            assertFalse(distanciaLevenshtein.admet(new ValorConjuntNumeric(new double[]{0.0})));
            assertFalse(distanciaLevenshtein.admet(new ValorConjuntTextual(new String[]{"a"})));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsSonDeClassesDiferents() {
            distanciaLevenshtein.obtenir(new ValorBoolea(true), new ValorNumeric(1.0));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
            distanciaLevenshtein.obtenir(new ValorBoolea(true), new ValorBoolea(false));
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedDistanciaLevenshteinTest {
        private final ValorAtribut<?> valor1;
        private final ValorAtribut<?> valor2;
        private final double resultat;

        public ParametrizedDistanciaLevenshteinTest(ValorAtribut<?> valor1, ValorAtribut<?> valor2, double resultat) {
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
        public void obtenir_HauriaDeRetornarDistancia() {
            assertEquals(distanciaLevenshtein.obtenir(valor1, valor2), resultat, delta);
        }
    }
}