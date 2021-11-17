package atributs.tipus;

import domini.classes.atributs.tipus.Discret;
import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.valors.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscretTest {

    private final static double delta = 1e-10;
    private final static Discret discret = new Discret();

    @Test
    public void copy_HauriaDeRetornarCopia() {
        TipusAtribut copia = discret.copy();
        assertNotSame(copia, discret);
        assertEquals(copia, discret);
    }

    @Test
    public void admetValorAtribut_HauriaDeRetornarCert() {
        assertTrue(discret.admetValorAtribut(new ValorBoolea(true)));
        assertTrue(discret.admetValorAtribut(new ValorCategoric("a")));
        assertTrue(discret.admetValorAtribut(new ValorNumeric(0.0)));
        assertTrue(discret.admetValorAtribut(new ValorTextual("a")));
    }

    @Test
    public void admetValorAtribut_HauriaDeRetornarFals() {
        assertFalse(discret.admetValorAtribut(new ValorConjuntBoolea(new boolean[]{true})));
        assertFalse(discret.admetValorAtribut(new ValorConjuntCategoric(new String[]{"a"})));
        assertFalse(discret.admetValorAtribut(new ValorConjuntNumeric(new double[]{0.0})));
        assertFalse(discret.admetValorAtribut(new ValorConjuntTextual(new String[]{"a"})));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsAdmissiblesSonDeClassesDiferents() {
        discret.obtenirDistancia(new ValorBoolea(true), new ValorNumeric(1.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
        discret.obtenirDistancia(new ValorConjuntBoolea(new boolean[]{true}),
                        new ValorConjuntBoolea(new boolean[]{true}));
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsBooleansCertsSonIguals() {
        assertEquals(discret.obtenirDistancia(new ValorBoolea(true), new ValorBoolea(true)), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsBooleansFalsosSonIguals() {
        assertEquals(discret.obtenirDistancia(new ValorBoolea(false), new ValorBoolea(false)), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsBooleansSonDiferents() {
        assertEquals(discret.obtenirDistancia(new ValorBoolea(true), new ValorBoolea(false)), 1.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonIguals() {
        assertEquals(discret.obtenirDistancia(new ValorCategoric("a"), new ValorCategoric("a")), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonDiferents() {
        assertEquals(discret.obtenirDistancia(new ValorCategoric("a"), new ValorCategoric("b")), 1.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonIguals() {
        assertEquals(discret.obtenirDistancia(new ValorNumeric(10.0), new ValorNumeric(10.0)), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonDiferents() {
        assertEquals(discret.obtenirDistancia(new ValorNumeric(10.0), new ValorNumeric(-10.0)), 1.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonIguals() {
        assertEquals(discret.obtenirDistancia(new ValorTextual("foo"), new ValorTextual("foo")), 0.0, delta);
    }

    @Test
    public void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonDiferents() {
        assertEquals(discret.obtenirDistancia(new ValorTextual("foo"), new ValorTextual("bar")), 1.0, delta);
    }
}