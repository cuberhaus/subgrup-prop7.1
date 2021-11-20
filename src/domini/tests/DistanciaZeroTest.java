import domini.classes.atributs.distancia.Distancia;
import domini.classes.atributs.distancia.DistanciaZero;
import domini.classes.atributs.valors.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class DistanciaZeroTest {

    private final static double delta = 1e-10;
    private final static DistanciaZero distanciaZero = new DistanciaZero();

    @Test
    public void copy_HauriaDeRetornarCopia() {
        Distancia copia = distanciaZero.copy();
        assertNotSame(copia, distanciaZero);
        assertEquals(copia, distanciaZero);
    }

    @Test(expected = IllegalArgumentException.class)
    public void admet_HauriaDEmetreExcepcio_Quan_Nul() {
        distanciaZero.admet(null);
    }

    @Test
    public void admet_HauriaDeRetornarCert() {
        assertTrue(distanciaZero.admet(new ValorBoolea(true)));
        assertTrue(distanciaZero.admet(new ValorCategoric("a")));
        assertTrue(distanciaZero.admet(new ValorNumeric(0.0)));
        assertTrue(distanciaZero.admet(new ValorTextual("a")));
        assertTrue(distanciaZero.admet(new ValorConjuntBoolea(new boolean[]{true})));
        assertTrue(distanciaZero.admet(new ValorConjuntCategoric(new String[]{"a"})));
        assertTrue(distanciaZero.admet(new ValorConjuntNumeric(new double[]{0.0})));
        assertTrue(distanciaZero.admet(new ValorConjuntTextual(new String[]{"a"})));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsAdmissiblesSonDeClassesDiferents() {
        distanciaZero.obtenir(new ValorBoolea(true), new ValorNumeric(1.0));
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorsBooleans() {
        assertEquals(distanciaZero.obtenir(new ValorBoolea(true), new ValorBoolea(false)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorsCategorics() {
        assertEquals(distanciaZero.obtenir(new ValorCategoric("a"), new ValorCategoric("b")), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorsNumerics() {
        assertEquals(distanciaZero.obtenir(new ValorNumeric(1.0), new ValorNumeric(-1.0)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorsTextuals() {
        assertEquals(distanciaZero.obtenir(new ValorTextual("a"), new ValorTextual("b")), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorConjuntBoolea() {
        assertEquals(distanciaZero.obtenir(new ValorConjuntBoolea(new boolean[]{true}),
                new ValorConjuntBoolea(new boolean[]{false})), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorConjuntCategoric() {
        assertEquals(distanciaZero.obtenir(new ValorConjuntCategoric(new String[]{"a"}),
                new ValorConjuntCategoric(new String[]{"b"})), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorConjuntNumeric() {
        assertEquals(distanciaZero.obtenir(new ValorConjuntNumeric(new double[]{1.0}),
                new ValorConjuntNumeric(new double[]{-1.0})), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorConjuntTextual() {
        assertEquals(distanciaZero.obtenir(new ValorConjuntTextual(new String[]{"a"}),
                new ValorConjuntTextual(new String[]{"b"})), 0.0, delta);
    }
}