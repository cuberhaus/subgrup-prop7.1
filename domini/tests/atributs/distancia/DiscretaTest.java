package atributs.distancia;

import domini.classes.atributs.distancia.Discreta;
import domini.classes.atributs.distancia.Distancia;
import domini.classes.atributs.valors.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscretaTest {

    private final static double delta = 1e-10;
    private final static Discreta discreta = new Discreta();

    @Test
    public void copy_HauriaDeRetornarCopia() {
        Distancia copia = discreta.copy();
        assertNotSame(copia, discreta);
        assertEquals(copia, discreta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void admet_HauriaDEmetreExcepcio_Quan_Nul() {
        discreta.admet(null);
    }

    @Test
    public void admet_HauriaDeRetornarCert() {
        assertTrue(discreta.admet(new ValorBoolea(true)));
        assertTrue(discreta.admet(new ValorCategoric("a")));
        assertTrue(discreta.admet(new ValorNumeric(0.0)));
        assertTrue(discreta.admet(new ValorTextual("a")));
    }

    @Test
    public void admet_HauriaDeRetornarFals() {
        assertFalse(discreta.admet(new ValorConjuntBoolea(new boolean[]{true})));
        assertFalse(discreta.admet(new ValorConjuntCategoric(new String[]{"a"})));
        assertFalse(discreta.admet(new ValorConjuntNumeric(new double[]{0.0})));
        assertFalse(discreta.admet(new ValorConjuntTextual(new String[]{"a"})));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsAdmissiblesSonDeClassesDiferents() {
        discreta.obtenir(new ValorBoolea(true), new ValorNumeric(1.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
        discreta.obtenir(new ValorConjuntBoolea(new boolean[]{true}),
                        new ValorConjuntBoolea(new boolean[]{true}));
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsBooleansCertsSonIguals() {
        assertEquals(discreta.obtenir(new ValorBoolea(true), new ValorBoolea(true)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsBooleansFalsosSonIguals() {
        assertEquals(discreta.obtenir(new ValorBoolea(false), new ValorBoolea(false)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsBooleansSonDiferents() {
        assertEquals(discreta.obtenir(new ValorBoolea(true), new ValorBoolea(false)), 1.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonIguals() {
        assertEquals(discreta.obtenir(new ValorCategoric("a"), new ValorCategoric("a")), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonDiferents() {
        assertEquals(discreta.obtenir(new ValorCategoric("a"), new ValorCategoric("b")), 1.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonIguals() {
        assertEquals(discreta.obtenir(new ValorNumeric(10.0), new ValorNumeric(10.0)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonDiferents() {
        assertEquals(discreta.obtenir(new ValorNumeric(10.0), new ValorNumeric(-10.0)), 1.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonIguals() {
        assertEquals(discreta.obtenir(new ValorTextual("foo"), new ValorTextual("foo")), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonDiferents() {
        assertEquals(discreta.obtenir(new ValorTextual("foo"), new ValorTextual("bar")), 1.0, delta);
    }
}