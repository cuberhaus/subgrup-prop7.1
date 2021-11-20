import domini.classes.Id;
import org.junit.Test;

import static org.junit.Assert.*;

public class IdTest {

    private final static double delta = 1e-10;

    @Test
    public void equals_HauriaDeRetornarCert_Quan_IdsIguals() {
        Id id1 = new Id(0, true);
        Id id2 = new Id(0, true);
        assertEquals(id1, id2);
        assertEquals(id2, id1);
    }

    @Test
    public void equals_HauriaDeRetornarFals_Quan_IdsValorsDiferents() {
        Id id1 = new Id(0, true);
        Id id2 = new Id(2, true);
        assertNotEquals(id1, id2);
        assertNotEquals(id2, id1);
    }

    @Test
    public void equals_HauriaDeRetornarCert_Quan_IdsActiusDiferents() {
        Id id1 = new Id(0, true);
        Id id2 = new Id(0, false);
        assertEquals(id1, id2);
        assertEquals(id2, id1);
    }

    @Test
    public void copiar_HauriaDeCopiarId() {
        Id id = new Id(0, true);
        Id copiaId = id.copiar();
        assertNotSame(id, copiaId);
        assertEquals(id, copiaId);
    }

    @Test
    public void compareTo_HauriaDeRetornarZero_Quan_IdsAmbValorsIguals() {
        Id id1 = new Id(0, true);
        Id id2 = new Id(0, false);
        assertEquals(id1.compareTo(id2), 0);
        assertEquals(id2.compareTo(id1), 0);
    }

    @Test
    public void compareTo_HauriaDeRetornarNoZero_Quan_IdsAmbValorsDiferents() {
        Id id1 = new Id(0, true);
        Id id2 = new Id(1, false);
        assertTrue(id1.compareTo(id2) < 0);
        assertTrue(id2.compareTo(id1) > 0);
    }

    @Test
    public void obtenirValor_HauriaDeRetornarValor() {
        Id id = new Id(10, true);
        assertEquals(id.obtenirValor(), 10);
    }

    @Test
    public void esActiu_HauriaDeRetornarCert_Quan_IdActiu() {
        Id id = new Id(0, true);
        assertTrue(id.esActiu());
    }

    @Test
    public void esActiu_HauriaDeRetornarFals_Quan_IdInactiu() {
        Id id = new Id(0, false);
        assertFalse(id.esActiu());
    }

    @Test
    public void assignarActiu_HauriaDeFerIdActiu() {
        Id id = new Id(0, false);
        id.assignarActiu(true);
        assertTrue(id.esActiu());
    }

    @Test
    public void assignarActiu_HauriaDeFerIdInactiu() {
        Id id = new Id(0, true);
        id.assignarActiu(false);
        assertFalse(id.esActiu());
    }

    @Test
    public void assignarActiu_NoHauriaDeCanviarActiu_Quan_JaEsActiu() {
        Id id = new Id(0, true);
        id.assignarActiu(true);
        assertTrue(id.esActiu());
    }

    @Test
    public void assignarActiu_NoHauriaDeCanviarInactiu_Quan_JaEsInActiu() {
        Id id = new Id(0, false);
        id.assignarActiu(false);
        assertFalse(id.esActiu());
    }
}