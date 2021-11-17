package atributs.tipus;

import domini.classes.atributs.tipus.Euclidia;
import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.valors.*;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

@RunWith(Enclosed.class)
public class EuclidiaTest {

    private final static double delta = 1e-10;
    private final static Euclidia euclidia = new Euclidia();

    public static class NonParametrizedEuclidiaTest {
        @Test
        public void copy_HauriaDeRetornarCopia() {
            TipusAtribut copia = euclidia.copy();
            assertNotSame(copia, euclidia);
            assertEquals(copia, euclidia);
        }

        @Test
        public void admetValorAtribut_HauriaDeRetornarCert() {
            assertTrue(euclidia.admetValorAtribut(new ValorNumeric(0.0)));
            assertTrue(euclidia.admetValorAtribut(new ValorConjuntNumeric(new double[]{0.0})));
        }

        @Test
        public void admetValorAtribut_HauriaDeRetornarFals() {
            assertFalse(euclidia.admetValorAtribut(new ValorBoolea(true)));
            assertFalse(euclidia.admetValorAtribut(new ValorCategoric("a")));
            assertFalse(euclidia.admetValorAtribut(new ValorTextual("a")));
            assertFalse(euclidia.admetValorAtribut(new ValorConjuntBoolea(new boolean[]{true})));
            assertFalse(euclidia.admetValorAtribut(new ValorConjuntCategoric(new String[]{"a"})));
            assertFalse(euclidia.admetValorAtribut(new ValorConjuntTextual(new String[]{"a"})));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsSonDeClassesDiferents() {
            euclidia.obtenirDistancia(new ValorBoolea(true), new ValorNumeric(1.0));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
            euclidia.obtenirDistancia(new ValorBoolea(true), new ValorBoolea(false));
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedEuclidiaTest {
        private final ValorAtribut<?> valor1;
        private final ValorAtribut<?> valor2;
        private final double resultat;

        public ParametrizedEuclidiaTest(ValorAtribut<?> valor1, ValorAtribut<?> valor2, double resultat) {
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
            assertEquals(euclidia.obtenirDistancia(valor1, valor2), resultat, delta);
        }
    }
}