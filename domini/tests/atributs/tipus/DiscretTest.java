package domini.tests.atributs.tipus;

import domini.classes.atributs.tipus.Discret;
import domini.classes.atributs.valors.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscretTest {

    // TODO(maria): add more tests for other types and null values

    Discret discret = new Discret();

    @Test
    void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsAdmissiblesSonDeClassesDiferents() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                discret.obtenirDistancia(new ValorBoolea(true), new ValorNumeric(1.0)));
        assertEquals("Els dos ValorAtributs donats han de ser instàncies de la mateixa classe.",
                exception.getMessage());
    }

    @Test
    void obtenirDistancia_HauriaDEmetreExcepcio_Quan_ValorAtributsDeLaMateixaClasseNoSonAdmissibles() {
        ValorConjuntBoolea valor1 = new ValorConjuntBoolea(new ArrayList<>(List.of(new ValorBoolea(true))));
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                discret.obtenirDistancia(new ValorConjuntBoolea(new ArrayList<>(List.of(new ValorBoolea(true)))),
                        new ValorConjuntBoolea(new ArrayList<>(List.of(new ValorBoolea(false))))));
        assertEquals("El TipusAtribut no admet el tipus dels ValorAtributs donats.", exception.getMessage());
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
}