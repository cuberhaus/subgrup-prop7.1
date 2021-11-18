package jocs.joc1;

import domini.classes.*;
import domini.classes.csv.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import domini.classes.metode_recomanador.ConjuntRecomanacions;
import domini.classes.metode_recomanador.MetodeRecomanadorCollaborative;
import domini.classes.metode_recomanador.MetodeRecomanadorContentBased;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Joc1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV taula_items = lector.llegirCSV("./domini/tests/jocs_de_proves/joc1/items.csv");
        ConjuntItems items = new ConjuntItems("pelis", taula_items);
        ConjuntUsuaris usuaris = new ConjuntUsuaris();
        LectorDeCSV lector2 = new LectorDeCSV();
        TaulaCSV taula_valoracions = lector2.llegirCSV("./domini/tests/jocs_de_proves/joc1/ratings.test.known.csv");
        usuaris.afegir(taula_valoracions);
        ConjuntValoracions valoracions = new ConjuntValoracions();
        valoracions.afegir(taula_valoracions, items, usuaris);

        LectorDeCSV lector3 = new LectorDeCSV();
        TaulaCSV taula_valoracions_un = lector2.llegirCSV("./domini/tests/jocs_de_proves/joc1/ratings.test.unknown.csv");
        ConjuntValoracions unknown_conjunt = new ConjuntValoracions();
        unknown_conjunt.afegir(taula_valoracions_un, items, usuaris);

        MetodeRecomanadorCollaborative recomanadorCollaborative = new MetodeRecomanadorCollaborative(usuaris, items, valoracions);
        MetodeRecomanadorContentBased recomanadorContentBased = new MetodeRecomanadorContentBased(usuaris, items, valoracions);
        File inputQueries = new File("./domini/tests/jocs_de_proves/joc1/inputqueries.txt");

        Scanner inputQueriesReader = new Scanner(inputQueries);
        int q = inputQueriesReader.nextInt();
        System.out.println(q);
        double totalDCG = 0.;
        double totalIDCG = 0.;
        double totalNDCG = 0.;
        double totalDCG2 = 0.;
        double totalIDCG2 = 0.;
        double totalNDCG2 = 0.;
        for (int i = 0; i < q; ++i) {
            int userid = inputQueriesReader.nextInt();
            int known = inputQueriesReader.nextInt();
            int unknown = inputQueriesReader.nextInt();
            int Q = inputQueriesReader.nextInt();
            Usuari usuari = usuaris.obte(new Id(userid, true));
            ConjuntValoracions val_usuari = new ConjuntValoracions();
            for (int j = 0; j < known; ++j) {
                int itemid = inputQueriesReader.nextInt();
                String nextDouble = inputQueriesReader.next();
                double rating = Double.parseDouble(nextDouble);
                val_usuari.afegir(new Valoracio(rating, usuari, items.obte(new Id(itemid, true))));
            }
            ConjuntItems items_recomanables = new ConjuntItems(items.obteTipusItem());
            ArrayList<Pair<Integer, Double>> valoracions_unk = new ArrayList<>();
            for (int j = 0; j < unknown; ++j) {
                int itemid = inputQueriesReader.nextInt();
                items_recomanables.afegir(items.obte(new Id(itemid, true)));
                valoracions_unk.add(new Pair<>(itemid, unknown_conjunt.obte(usuari, items.obte(new Id(itemid, true))).getValor()));
            }
            ConjuntRecomanacions recomanacionsCollab = recomanadorCollaborative.obteRecomanacions(usuari, items_recomanables, val_usuari, Q);
            ConjuntRecomanacions recomanacionsContent = recomanadorContentBased.obteRecomanacions(usuari, items_recomanables, val_usuari, Q);

            totalDCG += recomanacionsCollab.calculaDiscountedCumulativeGain(valoracions_unk);
            totalDCG2 += recomanacionsContent.calculaDiscountedCumulativeGain(valoracions_unk);

        }
        System.out.println(totalDCG + " " + totalDCG2);
    }
}
