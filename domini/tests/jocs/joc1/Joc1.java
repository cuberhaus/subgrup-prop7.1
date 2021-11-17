package jocs.joc1;

import domini.classes.*;
import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.recomanador.RecomanadorCollaborative;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Joc1 {
    public static void main(String[] args) throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV taula_items = lector.llegirCSV("./domini/tests/jocs/joc1/items.csv");
        taula_items.imprimir();
        Item[] items = new Item[taula_items.obtenirNumeroElements()];
        TipusItem tipusItem = new TipusItem("pelicula", new TreeMap<>());
        int a = taula_items.obtenirNumeroAtrib();
        ArrayList<String> noms_atrib = taula_items.obtenirLlistaAtributs();
        for (int i = 0; i < taula_items.obtenirNumeroElements(); ++i) {
            Map<String, ValorAtribut<?>> valors = new TreeMap<>();
            for (String atribut : noms_atrib) {
                //valors.put(atribut, taula_items.obtenirValorsDeItemSeleccionat(i, atribut));
            }
           // items[i] = new Item(taula_items.obtenirValorsDeItemSeleccionat(i, "id"), tipusItem, taula_items.obtenirValorsAtribut(i));
        }


        Usuari[] usuaris = new Usuari[0];
        Valoracio[] valoracions = new Valoracio[0];
        RecomanadorCollaborative recomanador = new RecomanadorCollaborative(usuaris, items, valoracions);
        File inputQueries = new File("./domini/tests/jocs/joc1/inputqueries.txt");
        Scanner inputQueriesReader = new Scanner(inputQueries);
        int q = inputQueriesReader.nextInt();
        String s = inputQueriesReader.nextLine();
        System.out.println(s);
    }
}
