package atributs.distancia;

import domini.classes.atributs.distancia.DiferenciaDeConjunts;
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
public class DiferenciaDeConjuntsTest {

    private final static double delta = 1e-10;
    private final static DiferenciaDeConjunts diferenciaDeConjunts = new DiferenciaDeConjunts();

    public static class NonParametrizedDiferenciaDeConjuntsTest {
        @Test
        public void copy_HauriaDeRetornarCopia() {
            Distancia copia = diferenciaDeConjunts.copy();
            assertNotSame(copia, diferenciaDeConjunts);
            assertEquals(copia, diferenciaDeConjunts);
        }

        @Test(expected = IllegalArgumentException.class)
        public void admet_HauriaDEmetreExcepcio_Quan_Nul() {
            diferenciaDeConjunts.admet(null);
        }

        @Test
        public void admet_HauriaDeRetornarCert() {
            assertTrue(diferenciaDeConjunts.admet(new ValorConjuntBoolea(new boolean[]{true})));
            assertTrue(diferenciaDeConjunts.admet(new ValorConjuntCategoric(new String[]{"a"})));
            assertTrue(diferenciaDeConjunts.admet(new ValorConjuntNumeric(new double[]{0.0})));
            assertTrue(diferenciaDeConjunts.admet(new ValorConjuntTextual(new String[]{"a"})));
        }

        @Test
        public void admet_HauriaDeRetornarFals() {
            assertFalse(diferenciaDeConjunts.admet(new ValorBoolea(true)));
            assertFalse(diferenciaDeConjunts.admet(new ValorCategoric("a")));
            assertFalse(diferenciaDeConjunts.admet(new ValorNumeric(0.0)));
            assertFalse(diferenciaDeConjunts.admet(new ValorTextual("a")));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsSonDeClassesDiferents() {
            diferenciaDeConjunts.obtenir(new ValorConjuntBoolea(new boolean[]{true}),
                    new ValorConjuntNumeric(new double[]{0.0}));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
            diferenciaDeConjunts.obtenir(new ValorBoolea(true), new ValorBoolea(false));
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedDiferenciaDeConjuntsTest {
        private final ValorAtribut<?> valor1;
        private final ValorAtribut<?> valor2;
        private final double resultat;

        public ParametrizedDiferenciaDeConjuntsTest(ValorAtribut<?> valor1, ValorAtribut<?> valor2, double resultat) {
            this.valor1 = valor1;
            this.valor2 = valor2;
            this.resultat = resultat;
        }

        @Parameterized.Parameters(name = "{index}: obtenirDistancia")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    // ValorConjuntBoolea
                    {new ValorConjuntBoolea(new boolean[]{}), new ValorConjuntBoolea(new boolean[]{}), 0.0},
                    {new ValorConjuntBoolea(new boolean[]{true}), new ValorConjuntBoolea(new boolean[]{}), 1.0},
                    {new ValorConjuntBoolea(new boolean[]{}), new ValorConjuntBoolea(new boolean[]{false}), 1.0},
                    {new ValorConjuntBoolea(new boolean[]{true}), new ValorConjuntBoolea(new boolean[]{true}), 0.0},
                    {new ValorConjuntBoolea(new boolean[]{false}), new ValorConjuntBoolea(new boolean[]{false}), 0.0},
                    {new ValorConjuntBoolea(new boolean[]{true}), new ValorConjuntBoolea(new boolean[]{false}), 2.0},
                    {new ValorConjuntBoolea(new boolean[]{true, false}), new ValorConjuntBoolea(new boolean[]{true}), 1.0},
                    {new ValorConjuntBoolea(new boolean[]{true, true, false}), new ValorConjuntBoolea(new boolean[]{false}), 1.0},
                    // ValorConjuntCategoric
                    {new ValorConjuntCategoric(new String[]{}), new ValorConjuntCategoric(new String[]{}), 0.0},
                    {new ValorConjuntCategoric(new String[]{"a"}), new ValorConjuntCategoric(new String[]{}), 1.0},
                    {new ValorConjuntCategoric(new String[]{}), new ValorConjuntCategoric(new String[]{"b"}), 1.0},
                    {new ValorConjuntCategoric(new String[]{"a"}), new ValorConjuntCategoric(new String[]{"a"}), 0.0},
                    {new ValorConjuntCategoric(new String[]{"a".repeat(100)}), new ValorConjuntCategoric(new String[]{"b".repeat(100)}), 2.0},
                    {new ValorConjuntCategoric(new String[]{"a", "b"}), new ValorConjuntCategoric(new String[]{"a"}), 1.0},
                    {new ValorConjuntCategoric(new String[]{"a", "a", "b", "c", "d"}), new ValorConjuntCategoric(new String[]{"a", "b"}), 2.0},
                    // ValorConjuntNumeric
                    {new ValorConjuntNumeric(new double[]{}), new ValorConjuntNumeric(new double[]{}), 0.0},
                    {new ValorConjuntNumeric(new double[]{0.0}), new ValorConjuntNumeric(new double[]{0.1}), 2.0},
                    {new ValorConjuntNumeric(new double[]{0.0}), new ValorConjuntNumeric(new double[]{}), 1.0},
                    {new ValorConjuntNumeric(new double[]{}), new ValorConjuntNumeric(new double[]{0.0}), 1.0},
                    {new ValorConjuntNumeric(new double[]{0.0}), new ValorConjuntNumeric(new double[]{0.0}), 0.0},
                    {new ValorConjuntNumeric(new double[]{0.0}), new ValorConjuntNumeric(new double[]{0.0, 0.0}), 0.0},
                    {new ValorConjuntNumeric(new double[]{0.0, 0.1, 0.2, 10.0}), new ValorConjuntNumeric(new double[]{0.2, 0.2}), 3.0},
                    {new ValorConjuntNumeric(new double[]{-1.0}), new ValorConjuntNumeric(new double[]{1.0}), 2.0},
                    // ValorConjuntTextual
                    {new ValorConjuntCategoric(new String[]{}), new ValorConjuntCategoric(new String[]{}), 0.0},
                    {new ValorConjuntCategoric(new String[]{"a"}), new ValorConjuntCategoric(new String[]{}), 1.0},
                    {new ValorConjuntCategoric(new String[]{}), new ValorConjuntCategoric(new String[]{"b"}), 1.0},
                    {new ValorConjuntCategoric(new String[]{"a"}), new ValorConjuntCategoric(new String[]{"a"}), 0.0},
                    {new ValorConjuntCategoric(new String[]{"a".repeat(100)}), new ValorConjuntCategoric(new String[]{"b".repeat(100)}), 2.0},
                    {new ValorConjuntCategoric(new String[]{"a", "b"}), new ValorConjuntCategoric(new String[]{"a"}), 1.0},
                    {new ValorConjuntCategoric(new String[]{"a", "a", "b", "c", "d"}), new ValorConjuntCategoric(new String[]{"a", "b"}), 2.0},
            });
        }

        @Test
        public void obtenir_HauriaDeRetornarDistancia() {
            assertEquals(diferenciaDeConjunts.obtenir(valor1, valor2), resultat, delta);
        }
    }
}