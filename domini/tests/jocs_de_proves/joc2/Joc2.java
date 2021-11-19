package jocs_de_proves.joc2;

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

public class Joc2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV taula_items = lector.llegirCSV("./domini/tests/jocs_de_proves/joc2/items.csv");
        ConjuntItems items = new ConjuntItems("Series", taula_items);
        ConjuntUsuaris usuaris = new ConjuntUsuaris();

        LectorDeCSV lector2 = new LectorDeCSV();
        TaulaCSV taula_valoracions = lector2.llegirCSV("./domini/tests/jocs_de_proves/joc2/ratings.test.known.csv");
        usuaris.afegir(taula_valoracions);
        ConjuntValoracions valoracions = new ConjuntValoracions();
        valoracions.afegir(taula_valoracions, items, usuaris);

        LectorDeCSV lector3 = new LectorDeCSV();
        TaulaCSV taula_valoracions_un = lector3.llegirCSV("./domini/tests/jocs_de_proves/joc2/ratings.test.unknown.csv");
        ConjuntValoracions unknown_conjunt = new ConjuntValoracions();
        unknown_conjunt.afegir(taula_valoracions_un, items, usuaris);

        MetodeRecomanadorCollaborative recomanadorCollaborative = new MetodeRecomanadorCollaborative(usuaris, items, valoracions);
        MetodeRecomanadorContentBased recomanadorContentBased = new MetodeRecomanadorContentBased(usuaris, items, valoracions);

        double totalDCG = 0.;
        double totalIDCG = 0.;
        double totalNDCG = 0.;
        double totalDCG2 = 0.;
        double totalIDCG2 = 0.;
        double totalNDCG2 = 0.;

        for (Usuari us : usuaris.obtenirTotsElsElements().values()) {
            ConjuntItems items_recomanables = new ConjuntItems(items.obteTipusItem());
            ArrayList<Pair<Integer, Double>> valoracions_unk = new ArrayList<>();
            for (Valoracio val : unknown_conjunt.obteTotesValoracions().values()) {
                if (val.getUsuari().equals(us)) {
                    items_recomanables.afegir(val.getItem());
                    valoracions_unk.add(new Pair<>(val.getItem().obtenirId().getValor(), val.getValor()));
                }
            }
            ConjuntRecomanacions recomanacionsCollab = recomanadorCollaborative.obteRecomanacions(us, items_recomanables, (int)Math.sqrt(items_recomanables.mida()));
            //ConjuntRecomanacions recomanacionsContent = recomanadorContentBased.obteRecomanacions(us, items_recomanables, val_usuari, Q);
            totalDCG += recomanacionsCollab.calculaDiscountedCumulativeGain(valoracions_unk);
            totalIDCG += recomanacionsCollab.calculaIdealDiscountedCumulativeGain(valoracions_unk, (int)Math.sqrt(items_recomanables.mida()));
            totalNDCG += recomanacionsCollab.obteDiscountedCumulativeGain()/recomanacionsCollab.obteIdealDiscountedCumulativeGain();

        }
        System.out.println(totalDCG/usuaris.mida() + " " + totalIDCG/usuaris.mida() + " " + totalNDCG/usuaris.mida());


    }
}
