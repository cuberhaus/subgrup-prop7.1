package domini.tests.atributs.tipus;

import domini.classes.atributs.tipus.Discret;
import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.valors.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DiscretTest {

    Discret discret = new Discret();

    @Test
    void copy_HauriaDeRetornarCopia() {
        TipusAtribut copia = discret.copy();
        assertNotSame(copia, discret);
        assertEquals(copia, discret);
    }

    @Test
    void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsAdmissiblesSonDeClassesDiferents() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                discret.obtenirDistancia(new ValorBoolea(true), new ValorNumeric(1.0)));
        assertEquals("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.",
                exception.getMessage());
    }

    @Test
    void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                discret.obtenirDistancia(new ValorConjuntBoolea(new boolean[]{true}),
                        new ValorConjuntBoolea(new boolean[]{true})));
        assertEquals("El TipusAtribut no admet el tipus dels ValorAtributs donats.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsBooleansSonIguals(boolean valor) {
        assertEquals(discret.obtenirDistancia(new ValorBoolea(valor), new ValorBoolea(valor)), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsBooleansSonDiferents() {
        assertEquals(discret.obtenirDistancia(new ValorBoolea(true), new ValorBoolea(false)), 1.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonIguals() {
        assertEquals(discret.obtenirDistancia(new ValorCategoric("a"), new ValorCategoric("a")), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonDiferents() {
        assertEquals(discret.obtenirDistancia(new ValorCategoric("a"), new ValorCategoric("b")), 1.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonIguals() {
        assertEquals(discret.obtenirDistancia(new ValorNumeric(10.0), new ValorNumeric(10.0)), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonDiferents() {
        assertEquals(discret.obtenirDistancia(new ValorNumeric(10.0), new ValorNumeric(-10.0)), 1.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonIguals() {
        assertEquals(discret.obtenirDistancia(new ValorTextual("foo"), new ValorTextual("foo")), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonDiferents() {
        assertEquals(discret.obtenirDistancia(new ValorTextual("foo"), new ValorTextual("bar")), 1.0);
    }
}