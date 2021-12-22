package jocs_de_proves.joc2;

import domini.classes.*;
import persistencia.classes.LectorDeCSV;
import domini.classes.csv.TaulaCSV;
import domini.classes.recomanador.ConjuntRecomanacions;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanadorCollaborative;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanadorContentBased;
import utilitats.Pair;

import java.util.ArrayList;

public class Joc2 {
    public static void main(String[] args) throws Exception {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV taulaItems = new TaulaCSV(lector.llegirCSV("../EXE/jocs_de_proves/joc2/items.csv"));
        ConjuntItems items = new ConjuntItems("Series", taulaItems);
        ConjuntUsuaris usuaris = new ConjuntUsuaris();

        LectorDeCSV lector2 = new LectorDeCSV();
        TaulaCSV taulaValoracions = new TaulaCSV(lector2.llegirCSV("../EXE/jocs_de_proves/joc2/ratings.test.known.csv"));
        usuaris.afegir(taulaValoracions);
        ConjuntValoracions valoracions = new ConjuntValoracions();
        valoracions.afegir(taulaValoracions, items, usuaris);

        LectorDeCSV lector3 = new LectorDeCSV();
        TaulaCSV taulaValoracionsUn = new TaulaCSV(lector3.llegirCSV("../EXE/jocs_de_proves/joc2/ratings.test.unknown.csv"));
        ConjuntValoracions unknownConjunt = new ConjuntValoracions();
        unknownConjunt.afegir(taulaValoracionsUn, items, usuaris);

        MetodeRecomanadorCollaborative recomanadorCollaborative = new MetodeRecomanadorCollaborative(usuaris, items, valoracions);
        MetodeRecomanadorContentBased recomanadorContentBased = new MetodeRecomanadorContentBased(usuaris, items, valoracions);

        double totalDCG = 0.;
        double totalIDCG = 0.;
        double totalNDCG = 0.;
        double totalDCG2 = 0.;
        double totalIDCG2 = 0.;
        double totalNDCG2 = 0.;

        int numRecomanacions = 5;
        for (Usuari us : usuaris.obtenirTotsElsElements().values()) {
            ConjuntItems itemsRecomanables = new ConjuntItems(items.obteTipusItem());
            ArrayList<Pair<Integer, Double>> valoracionsUnk = new ArrayList<>();

            for (Valoracio val : unknownConjunt.obtenitTotesLesValoracions().values()) {
                if (val.obtenirUsuari().equals(us)) {
                    itemsRecomanables.afegir(val.obtenirItem());
                    valoracionsUnk.add(new Pair<>(val.obtenirItem().obtenirId().obtenirValor(), val.obtenirValor()));
                }
            }

            ConjuntRecomanacions recomanacionsCollab = recomanadorCollaborative.obteRecomanacions(us, itemsRecomanables, numRecomanacions);
            ConjuntRecomanacions recomanacionsContent = recomanadorContentBased.obteRecomanacions(us, itemsRecomanables, numRecomanacions);
            totalDCG += recomanacionsCollab.calculaDiscountedCumulativeGain(valoracionsUnk);
            totalIDCG += recomanacionsCollab.calculaIdealDiscountedCumulativeGain(valoracionsUnk, numRecomanacions);
            totalNDCG += recomanacionsCollab.obtenirDiscountedCumulativeGain()/recomanacionsCollab.obtenirIdealDiscountedCumulativeGain();

            totalDCG2 += recomanacionsContent.calculaDiscountedCumulativeGain(valoracionsUnk);
            totalIDCG2 += recomanacionsContent.calculaIdealDiscountedCumulativeGain(valoracionsUnk, numRecomanacions);
            totalNDCG2 += recomanacionsContent.obtenirDiscountedCumulativeGain()/recomanacionsContent.obtenirIdealDiscountedCumulativeGain();
        }
        System.out.println("Recomanador colaboratiu:\nDGC mitja: " + totalDCG/usuaris.mida() + ", IDCG mitja: " + totalIDCG/usuaris.mida() +
                ", NDCG mitja: " + totalNDCG/usuaris.mida());
        System.out.println("Recomanador per contingut:\nDGC mitja: " + totalDCG2/usuaris.mida() + ", IDCG mitja: " + totalIDCG2/usuaris.mida() +
                ", NDCG mitja: " + totalNDCG2/usuaris.mida());
    }
}
