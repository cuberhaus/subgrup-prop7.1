package domini.tests;

import domini.classes.*;
import domini.classes.csv.TaulaCSV;
import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * JUnit de la classe ConjuntItems
 * @author pablo.vega
 */

public class ConjuntItemsTest {

    @Test (expected = Exception.class)
    public void testContructoraStringTaulaIdNoInt() throws Exception {
        TaulaCSV taula = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("id");
        atributs.add("color");
        taula.afegirConjuntAtributs(atributs);

        ArrayList<String> objecte1 = new ArrayList<>();
        objecte1.add("Hola");
        objecte1.add("blau");
        taula.afegirConjuntValors(objecte1);

        ArrayList<String> objecte2 = new ArrayList<>();
        objecte2.add("Adios");
        objecte2.add("vermell");
        taula.afegirConjuntValors(objecte2);

        ConjuntItems conjunt = new ConjuntItems("pelis", taula);
    }

    @Test (expected = Exception.class)
    public void testContructoraStringTaulaMateixosIds() throws Exception {
        TaulaCSV taula = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("id");
        atributs.add("color");
        taula.afegirConjuntAtributs(atributs);

        ArrayList<String> objecte1 = new ArrayList<>();
        objecte1.add("1");
        objecte1.add("blau");
        taula.afegirConjuntValors(objecte1);

        ArrayList<String> objecte2 = new ArrayList<>();
        objecte2.add("1");
        objecte2.add("vermell");
        taula.afegirConjuntValors(objecte2);

        ConjuntItems conjunt = new ConjuntItems("pelis", taula);
    }

    @Test
    public void testContructoraStringTaulaFuncionaBe() throws Exception {
        TaulaCSV taula = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("id");
        atributs.add("color");
        taula.afegirConjuntAtributs(atributs);

        ArrayList<String> objecte1 = new ArrayList<>();
        objecte1.add("1");
        objecte1.add("blau");
        taula.afegirConjuntValors(objecte1);

        ArrayList<String> objecte2 = new ArrayList<>();
        objecte2.add("2");
        objecte2.add("vermell");
        taula.afegirConjuntValors(objecte2);

        ConjuntItems conjunt = new ConjuntItems("pelis", taula);
    }

    @Test
    public void testConstructorTipusItemTreeMap() throws Exception {
        TaulaCSV taula = new TaulaCSV();
        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("id");
        atributs.add("color");
        taula.afegirConjuntAtributs(atributs);

        ArrayList<String> objecte1 = new ArrayList<>();
        objecte1.add("1");
        objecte1.add("blau");
        taula.afegirConjuntValors(objecte1);

        ArrayList<String> objecte2 = new ArrayList<>();
        objecte2.add("2");
        objecte2.add("vermell");
        taula.afegirConjuntValors(objecte2);

        TipusItem tipusItem = new TipusItem("peli", taula, taula.obtenirNumItems());
        Id id1 = new Id(1, true);
        Id id2 = new Id(2, true);
        Item item1 = new Item(id1, tipusItem, atributs, objecte1);
        Item item2 = new Item(id2, tipusItem, atributs, objecte2);

        TreeMap<Id, Item> elements = new TreeMap<>();
        elements.put(id1, item1);
        elements.put(id2, item2);

        ConjuntItems conjunt = new ConjuntItems(tipusItem, elements);
    }
}