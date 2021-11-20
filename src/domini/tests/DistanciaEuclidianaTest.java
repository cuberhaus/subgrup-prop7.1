import domini.classes.atributs.distancia.DistanciaEuclidiana;
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
public class DistanciaEuclidianaTest {

    private final static double delta = 1e-10;
    private final static DistanciaEuclidiana distanciaEuclidiana = new DistanciaEuclidiana();

    public static class NonParametrizedDistanciaEuclidianaTest {
        @Test
        public void copy_HauriaDeRetornarCopia() {
            Distancia copia = distanciaEuclidiana.copy();
            assertNotSame(copia, distanciaEuclidiana);
            assertEquals(copia, distanciaEuclidiana);
        }

        @Test(expected = IllegalArgumentException.class)
        public void admet_HauriaDEmetreExcepcio_Quan_Nul() {
            distanciaEuclidiana.admet(null);
        }

        @Test
        public void admet_HauriaDeRetornarCert() {
            assertTrue(distanciaEuclidiana.admet(new ValorNumeric(0.0)));
            assertTrue(distanciaEuclidiana.admet(new ValorConjuntNumeric(new double[]{0.0})));
        }

        @Test
        public void admet_HauriaDeRetornarFals() {
            assertFalse(distanciaEuclidiana.admet(new ValorBoolea(true)));
            assertFalse(distanciaEuclidiana.admet(new ValorCategoric("a")));
            assertFalse(distanciaEuclidiana.admet(new ValorTextual("a")));
            assertFalse(distanciaEuclidiana.admet(new ValorConjuntBoolea(new boolean[]{true})));
            assertFalse(distanciaEuclidiana.admet(new ValorConjuntCategoric(new String[]{"a"})));
            assertFalse(distanciaEuclidiana.admet(new ValorConjuntTextual(new String[]{"a"})));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsSonDeClassesDiferents() {
            distanciaEuclidiana.obtenir(new ValorBoolea(true), new ValorNumeric(1.0));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
            distanciaEuclidiana.obtenir(new ValorBoolea(true), new ValorBoolea(false));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenirNorma_HauriaDEmetreExcepcio_Quan_Nul() {
            distanciaEuclidiana.obtenirNorma(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenirNorma_HauriaDEmetreExcepcio_Quan_ValorAtributNoEsAdmissible() {
            distanciaEuclidiana.obtenirNorma(new ValorBoolea(true));
        }

        @Test(expected = IllegalArgumentException.class)
        public void actualitzarFactorDeNormalitzacio_HauriaDEmetreExcepcio_Quan_Nul() {
            distanciaEuclidiana.actualitzarFactorDeNormalitzacio(null);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedObtenirDistanciaEuclidianaTest {
        private final ValorAtribut<?> valor1;
        private final ValorAtribut<?> valor2;
        private final double resultat;

        public ParametrizedObtenirDistanciaEuclidianaTest(ValorAtribut<?> valor1, ValorAtribut<?> valor2, double resultat) {
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
        public void obtenir_HauriaDeRetornarDistancia() {
            assertEquals(distanciaEuclidiana.obtenir(valor1, valor2), resultat, delta);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedObtenirNormaDistanciaEuclidianaTest {
        private final ValorAtribut<?> valor;
        private final double resultat;

        public ParametrizedObtenirNormaDistanciaEuclidianaTest(ValorAtribut<?> valor, double resultat) {
            this.valor = valor;
            this.resultat = resultat;
        }

        @Parameterized.Parameters(name = "{index}: obtenirNorma")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    // ValorNumeric
                    {new ValorNumeric(0.0), 0.0},
                    {new ValorNumeric(-1.0), 1.0},
                    {new ValorNumeric(1.0), 1.0},
                    {new ValorNumeric(2.0), 2.0},
                    // ValorConjuntNumeric
                    {new ValorConjuntNumeric(new double[]{0.0, 0.0}), 0.0},
                    {new ValorConjuntNumeric(new double[]{1.0, 1.0}), Math.sqrt(2)},
                    {new ValorConjuntNumeric(new double[]{-1.0, -1.0}), Math.sqrt(2)},
                    {new ValorConjuntNumeric(new double[]{-1.0, -1.0, -1.0}), Math.sqrt(3)},
            });
        }

        @Test
        public void obtenirNorma_HauriaDeRetornarNorma() {
            assertEquals(distanciaEuclidiana.obtenirNorma(valor), resultat, delta);
        }
    }
}