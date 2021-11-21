import domini.classes.atributs.distancia.DistanciaDiscreta;
import domini.classes.atributs.distancia.Distancia;
import domini.classes.atributs.valors.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class DistanciaDiscretaTest {

    private final static double delta = 1e-10;
    private final static DistanciaDiscreta distanciaDiscreta = new DistanciaDiscreta();

    @Test
    public void copiar_HauriaDeRetornarCopia() {
        Distancia copia = distanciaDiscreta.copiar();
        assertNotSame(copia, distanciaDiscreta);
        assertEquals(copia, distanciaDiscreta);
    }

    @Test(expected = IllegalArgumentException.class)
    public void admet_HauriaDEmetreExcepcio_Quan_Nul() {
        distanciaDiscreta.admet(null);
    }

    @Test
    public void admet_HauriaDeRetornarCert() {
        assertTrue(distanciaDiscreta.admet(new ValorBoolea(true)));
        assertTrue(distanciaDiscreta.admet(new ValorCategoric("a")));
        assertTrue(distanciaDiscreta.admet(new ValorNumeric(0.0)));
        assertTrue(distanciaDiscreta.admet(new ValorTextual("a")));
    }

    @Test
    public void admet_HauriaDeRetornarFals() {
        assertFalse(distanciaDiscreta.admet(new ValorConjuntBoolea(new boolean[]{true})));
        assertFalse(distanciaDiscreta.admet(new ValorConjuntCategoric(new String[]{"a"})));
        assertFalse(distanciaDiscreta.admet(new ValorConjuntNumeric(new double[]{0.0})));
        assertFalse(distanciaDiscreta.admet(new ValorConjuntTextual(new String[]{"a"})));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsAdmissiblesSonDeClassesDiferents() {
        distanciaDiscreta.obtenir(new ValorBoolea(true), new ValorNumeric(1.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenir_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
        distanciaDiscreta.obtenir(new ValorConjuntBoolea(new boolean[]{true}),
                        new ValorConjuntBoolea(new boolean[]{true}));
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsBooleansCertsSonIguals() {
        assertEquals(distanciaDiscreta.obtenir(new ValorBoolea(true), new ValorBoolea(true)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsBooleansFalsosSonIguals() {
        assertEquals(distanciaDiscreta.obtenir(new ValorBoolea(false), new ValorBoolea(false)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsBooleansSonDiferents() {
        assertEquals(distanciaDiscreta.obtenir(new ValorBoolea(true), new ValorBoolea(false)), 1.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonIguals() {
        assertEquals(distanciaDiscreta.obtenir(new ValorCategoric("a"), new ValorCategoric("a")), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonDiferents() {
        assertEquals(distanciaDiscreta.obtenir(new ValorCategoric("a"), new ValorCategoric("b")), 1.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonIguals() {
        assertEquals(distanciaDiscreta.obtenir(new ValorNumeric(10.0), new ValorNumeric(10.0)), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonDiferents() {
        assertEquals(distanciaDiscreta.obtenir(new ValorNumeric(10.0), new ValorNumeric(-10.0)), 1.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonIguals() {
        assertEquals(distanciaDiscreta.obtenir(new ValorTextual("foo"), new ValorTextual("foo")), 0.0, delta);
    }

    @Test
    public void obtenir_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonDiferents() {
        assertEquals(distanciaDiscreta.obtenir(new ValorTextual("foo"), new ValorTextual("bar")), 1.0, delta);
    }
}