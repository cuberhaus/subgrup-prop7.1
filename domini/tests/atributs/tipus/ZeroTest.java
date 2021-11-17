package atributs.tipus;

import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.tipus.Zero;
import domini.classes.atributs.valors.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZeroTest {

    private final static double delta = 1e-10;
    private final static Zero zero = new Zero();

    @Test
    public void copy_HauriaDeRetornarCopia() {
        TipusAtribut copia = zero.copy();
        assertNotSame(copia, zero);
        assertEquals(copia, zero);
    }

    @Test(expected = IllegalArgumentException.class)
    public void admetValorAtribut_HauriaDEmetreExcepcio_Quan_Nul() {
        zero.admetValorAtribut(null);
    }

    @Test
    public void admetValorAtribut_HauriaDeRetornarCert() {
        assertTrue(zero.admetValorAtribut(new ValorBoolea(true)));
        assertTrue(zero.admetValorAtribut(new ValorCategoric("a")));
        assertTrue(zero.admetValorAtribut(new ValorNumeric(0.0)));
        assertTrue(zero.admetValorAtribut(new ValorTextual("a")));
        assertTrue(zero.admetValorAtribut(new ValorConjuntBoolea(new boolean[]{true})));
        assertTrue(zero.admetValorAtribut(new ValorConjuntCategoric(new String[]{"a"})));
        assertTrue(zero.admetValorAtribut(new ValorConjuntNumeric(new double[]{0.0})));
        assertTrue(zero.admetValorAtribut(new ValorConjuntTextual(new String[]{"a"})));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsAdmissiblesSonDeClassesDiferents() {
        zero.obtenirDistancia(new ValorBoolea(true), new ValorNumeric(1.0));
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarZero_Quan_ValorsBooleans() {
        assertEquals(zero.obtenirDistancia(new ValorBoolea(true), new ValorBoolea(false)), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarZero_Quan_ValorsCategorics() {
        assertEquals(zero.obtenirDistancia(new ValorCategoric("a"), new ValorCategoric("b")), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarZero_Quan_ValorsNumerics() {
        assertEquals(zero.obtenirDistancia(new ValorNumeric(1.0), new ValorNumeric(-1.0)), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarZero_Quan_ValorsTextuals() {
        assertEquals(zero.obtenirDistancia(new ValorTextual("a"), new ValorTextual("b")), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarZero_Quan_ValorConjuntBoolea() {
        assertEquals(zero.obtenirDistancia(new ValorConjuntBoolea(new boolean[]{true}),
                new ValorConjuntBoolea(new boolean[]{false})), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarZero_Quan_ValorConjuntCategoric() {
        assertEquals(zero.obtenirDistancia(new ValorConjuntCategoric(new String[]{"a"}),
                new ValorConjuntCategoric(new String[]{"b"})), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarZero_Quan_ValorConjuntNumeric() {
        assertEquals(zero.obtenirDistancia(new ValorConjuntNumeric(new double[]{1.0}),
                new ValorConjuntNumeric(new double[]{-1.0})), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarZero_Quan_ValorConjuntTextual() {
        assertEquals(zero.obtenirDistancia(new ValorConjuntTextual(new String[]{"a"}),
                new ValorConjuntTextual(new String[]{"b"})), 0.0, delta);
    }
}