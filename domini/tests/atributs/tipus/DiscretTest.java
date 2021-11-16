package domini.tests.atributs.tipus;

import domini.classes.atributs.tipus.Discret;
import domini.classes.atributs.tipus.TipusAtribut;
import domini.classes.atributs.valors.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
                discret.obtenirDistancia(new ValorConjuntBoolea(new ArrayList<>(List.of(new ValorBoolea(true)))),
                        new ValorConjuntBoolea(new ArrayList<>(List.of(new ValorBoolea(false))))));
        assertEquals("El TipusAtribut no admet el tipus dels ValorAtributs donats.", exception.getMessage());
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsBooleansSonCerts() {
        ValorBoolea valor1 = new ValorBoolea(true);
        ValorBoolea valor2 = new ValorBoolea(true);
        assertEquals(discret.obtenirDistancia(valor1, valor2), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsBooleansSonFalsos() {
        ValorBoolea valor1 = new ValorBoolea(false);
        ValorBoolea valor2 = new ValorBoolea(false);
        assertEquals(discret.obtenirDistancia(valor1, valor2), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsBooleansSonDiferents() {
        ValorBoolea valor1 = new ValorBoolea(true);
        ValorBoolea valor2 = new ValorBoolea(false);
        assertEquals(discret.obtenirDistancia(valor1, valor2), 1.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonIguals() {
        ValorCategoric valor1 = new ValorCategoric("a");
        ValorCategoric valor2 = new ValorCategoric("a");
        assertEquals(discret.obtenirDistancia(valor1, valor2), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsCategoricsSonDiferents() {
        ValorCategoric valor1 = new ValorCategoric("a");
        ValorCategoric valor2 = new ValorCategoric("b");
        assertEquals(discret.obtenirDistancia(valor1, valor2), 1.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonIguals() {
        ValorNumeric valor1 = new ValorNumeric(10.0);
        ValorNumeric valor2 = new ValorNumeric(10.0);
        assertEquals(discret.obtenirDistancia(valor1, valor2), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsNumericsSonDiferents() {
        ValorNumeric valor1 = new ValorNumeric(10.0);
        ValorNumeric valor2 = new ValorNumeric(-10.0);
        assertEquals(discret.obtenirDistancia(valor1, valor2), 1.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonIguals() {
        ValorTextual valor1 = new ValorTextual("foo");
        ValorTextual valor2 = new ValorTextual("foo");
        assertEquals(discret.obtenirDistancia(valor1, valor2), 0.0);
    }

    @Test
    void obtenirDistancia_HauriaDeRetornarDistancia_Quan_ValorsTextualsSonDiferents() {
        ValorTextual valor1 = new ValorTextual("foo");
        ValorTextual valor2 = new ValorTextual("bar");
        assertEquals(discret.obtenirDistancia(valor1, valor2), 1.0);
    }
}