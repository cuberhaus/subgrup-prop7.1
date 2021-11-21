import domini.classes.*;
import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.valors.ValorAtribut;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Tots.
 */
public class UtilitatsDEscriptura {
    public static void imprimirItem(Item item) {
        System.out.println("******* Item *******");
        System.out.println("* Id de l'Item:");
        imprimirId(item.obtenirId());
        System.out.println("* TipusItem de l'Item:");
        imprimirTipusItem(item.obtenirTipusItem());
        System.out.println("* Atributs de l'Item:");
        imprimirAtributs(item.obtenirAtributs());
        System.out.println("* Valoracions de l'Item:");
        imprimirValoracions(item.obtenirValoracions());
        System.out.println();
    }

    public static void imprimirValoracions(Map<Usuari, Valoracio> valoracioMap) {
        if (valoracioMap.isEmpty()) {
            System.out.println("No té valoracions");
        } else {
            for (Map.Entry<Usuari, Valoracio> valoracioEntrada : valoracioMap.entrySet()) {
                System.out.println("Valoració de l'ítem "
                        + valoracioEntrada.getValue().obtenirItem().obtenirId().obtenirValor()
                        + " feta per l'usuari " + valoracioEntrada.getValue().obtenirUsuari().obtenirId().obtenirValor()
                        + " amb valor " + valoracioEntrada.getValue().obtenirValor());
            }
        }
    }


    public static void imprimirValoracio(Valoracio valoracio) {
        System.out.println("Té una valoració de l'ítem "
                + valoracio.obtenirItem().obtenirId().obtenirValor()
                + " feta per l'usuari " + valoracio.obtenirUsuari().obtenirId().obtenirValor()
                + " amb valor " + valoracio.obtenirValor());
    }

    public static void imprimirValoracionsUsuari(Map<Item, Valoracio> valoracioMap) {
        if (valoracioMap.isEmpty()) {
            System.out.println("No té valoracions");
        } else {
            for (Map.Entry<Item, Valoracio> valoracioEntrada : valoracioMap.entrySet()) {
                System.out.println("Té una valoració de l'ítem "
                        + valoracioEntrada.getValue().obtenirItem().obtenirId().obtenirValor()
                        + " feta per l'usuari " + valoracioEntrada.getValue().obtenirUsuari().obtenirId().obtenirValor()
                        + " amb valor " + valoracioEntrada.getValue().obtenirValor());
            }
        }
    }
    public static void imprimirAtributs(Map<String, ValorAtribut<?>> atributMap) {
        if (atributMap.isEmpty()) {
            System.out.println("No té atributs");
        } else {
            for (Map.Entry<String, ValorAtribut<?>> atributEntrada : atributMap.entrySet()) {
                System.out.println("Té un atribut amb nom " + atributEntrada.getKey() + " i valor "
                        + atributEntrada.getValue().obtenirValor().toString());
            }
        }
    }

    public static void imprimirTipusItem(TipusItem tipusItem) {
        System.out.println("TipusItem amb nom " + tipusItem.obtenirNom());
        imprimirTipusAtributs(tipusItem);
    }

    public static void imprimirTipusAtribut(TipusAtribut tipusAtribut) {
        System.out.println(tipusAtribut.obtenirValorAtribut().getClass().getSimpleName() + " amb "
                + tipusAtribut.obtenirDistancia().getClass().getSimpleName());
    }

    public static void imprimirId(Id id) {
        String actiu = "actiu";
        if (!id.esActiu()) {
            actiu = "no actiu";
        }
        System.out.println("Id amb valor " + id.obtenirValor() + " " + actiu);
    }

    public static void imprimirUsuari(Usuari usuari) {
        Id id = usuari.obtenirId();
        String nom = usuari.obtenirNom();
        Map<Item,Valoracio> valoracions = usuari.obtenirValoracions();
        System.out.println("Usuari amb nom: " + nom);
        imprimirId(id);
        imprimirValoracionsUsuari(valoracions);
    }

    public static void  imprimirConjuntItems(ConjuntItems conjuntItems) {
        ArrayList<Item> items = (ArrayList<Item>) conjuntItems.obtenirTotsElsElements().values();
        UtilitatsDEscriptura.imprimirTipusItem(conjuntItems.obteTipusItem());
        for (Item item : items) {
            UtilitatsDEscriptura.imprimirItem(item);
        }
    }

    public static void  imprimirConjuntUsuaris(ConjuntUsuaris conjuntUsuaris) {
        ArrayList<Usuari> usuaris = conjuntUsuaris.obtenirUsuaris();
        for (Usuari usuari : usuaris) {
            UtilitatsDEscriptura.imprimirUsuari(usuari);
        }
    }

    public static void  imprimirConjuntValoracions(ConjuntValoracions conjuntValoracions) {
        TreeMap<Pair<Usuari,Item>,Valoracio> valoracions = conjuntValoracions.obteTotesValoracions();
        ArrayList<Valoracio> arrayValoracions = new ArrayList<>();
        Set<Pair<Usuari, Item>> keys = valoracions.keySet();
        for (Pair<Usuari, Item> id : keys) {
            arrayValoracions.add(valoracions.get(id));
        }

        for (Valoracio valoracio : arrayValoracions) {
            UtilitatsDEscriptura.imprimirValoracio(valoracio);
        }
    }

    public static void  imprimirTreeMapValoracions(TreeMap<Pair<Usuari,Item>,Valoracio> conjuntValoracions) {
        ArrayList<Valoracio> arrayValoracions = new ArrayList<>();
        Set<Pair<Usuari, Item>> keys = conjuntValoracions.keySet();
        for (Pair<Usuari, Item> id : keys) {
            arrayValoracions.add(conjuntValoracions.get(id));
        }

        for (Valoracio valoracio : arrayValoracions) {
            UtilitatsDEscriptura.imprimirValoracio(valoracio);
        }
    }

    public static void  imprimirArrayDUsuaris(ArrayList<Usuari> conjuntUsuaris) {
        for (Usuari usuari : conjuntUsuaris) {
            UtilitatsDEscriptura.imprimirUsuari(usuari);
        }
    }

    public static void imprimirTipusAtributs(TipusItem tipusItem) {
        for (Map.Entry<String, TipusAtribut> tipusAtributEntrada : tipusItem.obtenirTipusAtributs().entrySet()) {
            System.out.print("Té un atribut amb nom " + tipusAtributEntrada.getKey() + " i tipus ");
            imprimirTipusAtribut(tipusAtributEntrada.getValue());
        }
    }
}