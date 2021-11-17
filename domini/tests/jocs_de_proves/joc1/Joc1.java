package jocs_de_proves.joc1;

import domini.classes.*;
import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import domini.classes.metode_recomanador.MetodeRecomanadorCollaborative;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Joc1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV taula_items = lector.llegirCSV("jocs_de_proves/joc1/items.csv");
        taula_items.imprimir();
        // ConjuntItems items = new ConjuntItems(taula_items);
        Item[] items = new Item[0];

        Usuari[] usuaris = new Usuari[0];
        Valoracio[] valoracions = new Valoracio[0];
        MetodeRecomanadorCollaborative recomanador = new MetodeRecomanadorCollaborative(usuaris, items, valoracions);
        File inputQueries = new File("jocs_de_proves/joc1/inputqueries.txt");
        Scanner inputQueriesReader = new Scanner(inputQueries);
        int q = inputQueriesReader.nextInt();
        System.out.println(q);
        for (int i = 0; i < q; ++i) {
            int userid = inputQueriesReader.nextInt();
            int known = inputQueriesReader.nextInt();
            int unknown = inputQueriesReader.nextInt();
            int Q = inputQueriesReader.nextInt();
            //recomanador.obteRecomanacions()
        }

    }
}
