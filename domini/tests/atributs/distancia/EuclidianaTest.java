package atributs.distancia;

import domini.classes.atributs.distancia.Euclidiana;
import domini.classes.atributs.distancia.Distancia;
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
public class EuclidianaTest {

    private final static double delta = 1e-10;
    private final static Euclidiana euclidiana = new Euclidiana();

    public static class NonParametrizedEuclidianaTest {
        @Test
        public void copy_HauriaDeRetornarCopia() {
            Distancia copia = euclidiana.copy();
            assertNotSame(copia, euclidiana);
            assertEquals(copia, euclidiana);
        }

        @Test(expected = IllegalArgumentException.class)
        public void admet_HauriaDEmetreExcepcio_Quan_Nul() {
            euclidiana.admet(null);
        }

        @Test
        public void admet_HauriaDeRetornarCert() {
            assertTrue(euclidiana.admet(new ValorNumeric(0.0)));
            assertTrue(euclidiana.admet(new ValorConjuntNumeric(new double[]{0.0})));
        }

        @Test
        public void admet_HauriaDeRetornarFals() {
            assertFalse(euclidiana.admet(new ValorBoolea(true)));
            assertFalse(euclidiana.admet(new ValorCategoric("a")));
            assertFalse(euclidiana.admet(new ValorTextual("a")));
            assertFalse(euclidiana.admet(new ValorConjuntBoolea(new boolean[]{true})));
            assertFalse(euclidiana.admet(new ValorConjuntCategoric(new String[]{"a"})));
            assertFalse(euclidiana.admet(new ValorConjuntTextual(new String[]{"a"})));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsSonDeClassesDiferents() {
            euclidiana.obtenir(new ValorBoolea(true), new ValorNumeric(1.0));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
            euclidiana.obtenir(new ValorBoolea(true), new ValorBoolea(false));
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenirNorma_HauriaDEmetreExcepcio_Quan_Nul() {
            euclidiana.obtenirNorma(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void obtenirNorma_HauriaDEmetreExcepcio_Quan_ValorAtributNoEsAdmissible() {
            euclidiana.obtenirNorma(new ValorBoolea(true));
        }

        @Test(expected = IllegalArgumentException.class)
        public void actualitzarFactorDeNormalitzacio_HauriaDEmetreExcepcio_Quan_Nul() {
            euclidiana.actualitzarFactorDeNormalitzacio(null);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedObtenirEuclidianaTest {
        private final ValorAtribut<?> valor1;
        private final ValorAtribut<?> valor2;
        private final double resultat;

        public ParametrizedObtenirEuclidianaTest(ValorAtribut<?> valor1, ValorAtribut<?> valor2, double resultat) {
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
            assertEquals(euclidiana.obtenir(valor1, valor2), resultat, delta);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedObtenirNormaEuclidianaTest {
        private final ValorAtribut<?> valor;
        private final double resultat;

        public ParametrizedObtenirNormaEuclidianaTest(ValorAtribut<?> valor, double resultat) {
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
            assertEquals(euclidiana.obtenirNorma(valor), resultat, delta);
        }
    }
}