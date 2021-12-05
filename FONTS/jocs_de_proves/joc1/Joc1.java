package jocs_de_proves.joc1;

import domini.classes.*;
import persistencia.classes.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import domini.classes.recomanador.ConjuntRecomanacions;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanadorCollaborative;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanadorContentBased;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Joc1 {
    public static void main(String[] args) throws Exception {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV taula_items = new TaulaCSV(lector.llegirCSV("../EXE/jocs_de_proves/joc1/items.csv"));
        ConjuntItems items = new ConjuntItems("pelis", taula_items);
        ConjuntUsuaris usuaris = new ConjuntUsuaris();

        LectorDeCSV lector2 = new LectorDeCSV();
        TaulaCSV taula_valoracions = new TaulaCSV(lector2.llegirCSV("../EXE/jocs_de_proves/joc1/ratings.test.known.csv"));
        usuaris.afegir(taula_valoracions);
        ConjuntValoracions valoracions = new ConjuntValoracions();
        valoracions.afegir(taula_valoracions, items, usuaris);

        LectorDeCSV lector3 = new LectorDeCSV();
        TaulaCSV taula_valoracions_un = new TaulaCSV(lector3.llegirCSV("../EXE/jocs_de_proves/joc1/ratings.test.unknown.csv"));
        ConjuntValoracions unknown_conjunt = new ConjuntValoracions();
        unknown_conjunt.afegir(taula_valoracions_un, items, usuaris);

        MetodeRecomanadorCollaborative recomanadorCollaborative = new MetodeRecomanadorCollaborative(usuaris, items, valoracions);
        MetodeRecomanadorContentBased recomanadorContentBased = new MetodeRecomanadorContentBased(usuaris, items, valoracions);
        File inputQueries = new File("../EXE/jocs_de_proves/joc1/inputqueries.txt");

        Scanner inputQueriesReader = new Scanner(inputQueries);
        int q = inputQueriesReader.nextInt();

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
            Usuari usuari = usuaris.obtenir(new Id(userid, true));
            ConjuntValoracions val_usuari = new ConjuntValoracions();
            for (int j = 0; j < known; ++j) {
                int itemid = inputQueriesReader.nextInt();
                String nextDouble = inputQueriesReader.next();
                double rating = Double.parseDouble(nextDouble);
                val_usuari.afegir(new Valoracio(rating, usuari, items.obtenir(new Id(itemid, true))));
            }
            ConjuntItems items_recomanables = new ConjuntItems(items.obteTipusItem());
            ArrayList<Pair<Integer, Double>> valoracions_unk = new ArrayList<>();
            for (int j = 0; j < unknown; ++j) {
                int itemid = inputQueriesReader.nextInt();
                items_recomanables.afegir(items.obtenir(new Id(itemid, true)));
                valoracions_unk.add(new Pair<>(itemid, unknown_conjunt.obte(usuari, items.obtenir(new Id(itemid, true))).obtenirValor()));
            }

            ConjuntRecomanacions recomanacionsCollab = recomanadorCollaborative.obteRecomanacions(usuari, items_recomanables, val_usuari, Q);
            ConjuntRecomanacions recomanacionsContent = recomanadorContentBased.obteRecomanacions(usuari, items_recomanables, val_usuari, Q);

            totalDCG += recomanacionsCollab.calculaDiscountedCumulativeGain(valoracions_unk);
            totalIDCG += recomanacionsCollab.calculaIdealDiscountedCumulativeGain(valoracions_unk, Q);
            totalNDCG += recomanacionsCollab.obteDiscountedCumulativeGain()/recomanacionsCollab.obteIdealDiscountedCumulativeGain();

            totalDCG2 += recomanacionsContent.calculaDiscountedCumulativeGain(valoracions_unk);
            totalIDCG2 += recomanacionsContent.calculaIdealDiscountedCumulativeGain(valoracions_unk, Q);
            totalNDCG2 += recomanacionsContent.obteDiscountedCumulativeGain()/recomanacionsContent.obteIdealDiscountedCumulativeGain();
        }
        System.out.println("Recomanador colaboratiu:\nDGC mitja: " + totalDCG/q + ", IDCG mitja: " + totalIDCG/q +
                ", NDCG mitja: " + totalNDCG/q);
        System.out.println("Recomanador per contingut:\nDGC mitja: " + totalDCG2/q + ", IDCG mitja: " + totalIDCG2/q +
                ", NDCG mitja: " + totalNDCG2/q);
    }
}
