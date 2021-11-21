import domini.classes.Id;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author edgar.moreno
 */
public class Main {
    public static void main(String[] args) throws IOException {


        System.out.println("Amb aquest programa es poden carregar conjunts de dades propis i obtenir recomanacions per usuaris.");
        domini.classes.csv.LectorDeCSV lector = new domini.classes.csv.LectorDeCSV();
        String ubicacio_fitxer_items = libs.consola.llegirString("Indica on es troba el fitxer csv amb els items desitjats.");
        domini.classes.csv.TaulaCSV taula_items = lector.llegirCSV(ubicacio_fitxer_items);
        domini.classes.ConjuntItems items = new domini.classes.ConjuntItems("Series", taula_items);

        domini.classes.ConjuntUsuaris usuaris = new domini.classes.ConjuntUsuaris();

        domini.classes.csv.LectorDeCSV lector2 = new domini.classes.csv.LectorDeCSV();
        String ubicacio_val_publiques = libs.consola.llegirString("Indica on es troba el fitxer csv amb les valoracions publiques.");
        domini.classes.csv.TaulaCSV taula_valoracions = lector2.llegirCSV(ubicacio_val_publiques);
        usuaris.afegir(taula_valoracions);
        domini.classes.ConjuntValoracions valoracions = new domini.classes.ConjuntValoracions();
        valoracions.afegir(taula_valoracions, items, usuaris);

        domini.classes.csv.LectorDeCSV lector3 = new domini.classes.csv.LectorDeCSV();
        String ubicacio_val_privades = libs.consola.llegirString("Indica on es troba el fitxer csv amb les valoracions privades.");
        domini.classes.csv.TaulaCSV taula_valoracions_un = lector3.llegirCSV(ubicacio_val_privades);
        domini.classes.ConjuntValoracions unknownConjunt = new domini.classes.ConjuntValoracions();
        unknownConjunt.afegir(taula_valoracions_un, items, usuaris);

        domini.classes.recomanador.metode_recomanador.MetodeRecomanadorCollaborative recomanadorCollaborative = new domini.classes.recomanador.metode_recomanador.MetodeRecomanadorCollaborative(usuaris, items, valoracions);
        domini.classes.recomanador.metode_recomanador.MetodeRecomanadorContentBased recomanadorContentBased = new domini.classes.recomanador.metode_recomanador.MetodeRecomanadorContentBased(usuaris, items, valoracions);

        while(true) {
            int ordre = libs.consola.llegirInt("0 - Surt\n1 - Nova recomanacio", "Ordre invalida", 0, 1);
            if (ordre == 0) return;
            int id = libs.consola.llegirInt("Introdueix el Id del usuari que rebra recomanacions", "", Integer.MIN_VALUE, Integer.MAX_VALUE);
            int numRecomanacions = libs.consola.llegirInt("Introdueix el nombre de recomanacions a fer", "", Integer.MIN_VALUE, Integer.MAX_VALUE);
            domini.classes.Id idUs = new Id(id, true);
            if (!usuaris.conte(idUs)) {
                System.out.println("Aquest usuari no existeix");
                continue;
            }
            domini.classes.Usuari us = usuaris.obtenir(idUs);
            int metode = libs.consola.llegirInt("0 - Metode collaborative\n1 - Metode content based", "Ordre invalida", 0, 1);

            domini.classes.ConjuntItems itemsRecomanables = new domini.classes.ConjuntItems(items.obteTipusItem());
            ArrayList<domini.classes.Pair<Integer, Double>> valoracionsUnk = new ArrayList<>();

            for (domini.classes.Valoracio val : unknownConjunt.obteTotesValoracions().values()) {
                if (val.obtenirUsuari().equals(us)) {
                    itemsRecomanables.afegir(val.obtenirItem());
                    valoracionsUnk.add(new domini.classes.Pair<>(val.obtenirItem().obtenirId().obtenirValor(), val.obtenirValor()));
                }
            }
            domini.classes.recomanador.ConjuntRecomanacions recomanacions;
            if (metode == 0) {
                recomanacions = recomanadorCollaborative.obteRecomanacions(us, itemsRecomanables, numRecomanacions);
            }
            else {
                recomanacions = recomanadorContentBased.obteRecomanacions(us, itemsRecomanables, numRecomanacions);
            }

            System.out.println("Es recomanen els següent items:");
            for (domini.classes.recomanador.Recomanacio rec : recomanacions.obtenirConjuntRecomanacions()) {
                System.out.print(rec.obtenirId().obtenirValor() + " ");
            }
            System.out.println();
            System.out.println("El DCG d'aquestes recomanacions es de " + recomanacions.calculaDiscountedCumulativeGain(valoracionsUnk) +
                    " i el NDCG es de " + recomanacions.calculaIdealDiscountedCumulativeGain(valoracionsUnk, numRecomanacions));
            System.out.println();
        }
    }
}
