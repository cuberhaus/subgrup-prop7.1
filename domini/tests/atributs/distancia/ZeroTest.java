package atributs.distancia;

import domini.classes.atributs.distancia.Distancia;
import domini.classes.atributs.distancia.Zero;
import domini.classes.atributs.valors.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZeroTest {

    private final static double delta = 1e-10;
    private final static Zero zero = new Zero();

    @Test
    public void copy_HauriaDeRetornarCopia() {
        Distancia copia = zero.copy();
        assertNotSame(copia, zero);
        assertEquals(copia, zero);
    }

    @Test(expected = IllegalArgumentException.class)
    public void admet_HauriaDEmetreExcepcio_Quan_Nul() {
        zero.admet(null);
    }

    @Test
    public void admet_HauriaDeRetornarCert() {
        assertTrue(zero.admet(new ValorBoolea(true)));
        assertTrue(zero.admet(new ValorCategoric("a")));
        assertTrue(zero.admet(new ValorNumeric(0.0)));
        assertTrue(zero.admet(new ValorTextual("a")));
        assertTrue(zero.admet(new ValorConjuntBoolea(new boolean[]{true})));
        assertTrue(zero.admet(new ValorConjuntCategoric(new String[]{"a"})));
        assertTrue(zero.admet(new ValorConjuntNumeric(new double[]{0.0})));
        assertTrue(zero.admet(new ValorConjuntTextual(new String[]{"a"})));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsAdmissiblesSonDeClassesDiferents() {
        zero.obtenir(new ValorBoolea(true), new ValorNumeric(1.0));
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorsBooleans() {
        assertEquals(zero.obtenir(new ValorBoolea(true), new ValorBoolea(false)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorsCategorics() {
        assertEquals(zero.obtenir(new ValorCategoric("a"), new ValorCategoric("b")), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorsNumerics() {
        assertEquals(zero.obtenir(new ValorNumeric(1.0), new ValorNumeric(-1.0)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorsTextuals() {
        assertEquals(zero.obtenir(new ValorTextual("a"), new ValorTextual("b")), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorConjuntBoolea() {
        assertEquals(zero.obtenir(new ValorConjuntBoolea(new boolean[]{true}),
                new ValorConjuntBoolea(new boolean[]{false})), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorConjuntCategoric() {
        assertEquals(zero.obtenir(new ValorConjuntCategoric(new String[]{"a"}),
                new ValorConjuntCategoric(new String[]{"b"})), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorConjuntNumeric() {
        assertEquals(zero.obtenir(new ValorConjuntNumeric(new double[]{1.0}),
                new ValorConjuntNumeric(new double[]{-1.0})), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarZero_Quan_ValorConjuntTextual() {
        assertEquals(zero.obtenir(new ValorConjuntTextual(new String[]{"a"}),
                new ValorConjuntTextual(new String[]{"b"})), 0.0, delta);
    }
}