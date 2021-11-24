package domini.tests;

import domini.classes.Id;
import domini.classes.Item;
import domini.classes.TipusItem;
import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.valors.ValorAtribut;
import domini.classes.recomanador.metode_recomanador.KNN;
import libs.consola;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Driver per la classe KNN
 * @author edgar.moreno
 */
public class DriverKNN {
    static ArrayList<domini.classes.Item> items;
    static domini.classes.TipusItem tipusItem;
    private static void afegirItem() {
        System.out.println("Afegeix un item al conjunt actual");
        try {
            String consulta = "Introdueix el valor desitjat pel nou ítem";
            double val = consola.llegirDouble(consulta, "", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            ValorAtribut<Double> atribut = new domini.classes.atributs.valors.ValorNumeric(val);
            TreeMap<String, ValorAtribut<?>> mapa = new TreeMap<>();
            mapa.put("escalar", atribut);
            items.add(new Item(new Id(items.size(), true), tipusItem, mapa, null));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void consultaVeins() {
        System.out.println("Troba els veins de un item");
        try {
            String consulta = "Introdueix el valor desitjat pel item:";
            double val = consola.llegirDouble(consulta, "", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            ValorAtribut<Double> atribut = new domini.classes.atributs.valors.ValorNumeric(val);
            TreeMap<String, ValorAtribut<?>> mapa = new TreeMap<>();
            mapa.put("escalar", atribut);
            Item buscat = new Item(new Id(items.size(), true), tipusItem, mapa, null);
            String consulta2 = "Introdueix el nombre de veins desitjat";
            String err = "El nombre de veins ha de ser entre 0 i el nombre de items existents";
            int k = consola.llegirInt(consulta2, err, 1, items.size());
            domini.classes.recomanador.metode_recomanador.KNN knn = new KNN(items.toArray(new Item[0]));
            ArrayList<Item> veins = knn.obtenirVeins(buscat, k);
            System.out.println("Els veins son, respecte l'ordre en que han sigut afegits els items:");
            for(Item vei : veins) {
                System.out.print(vei.obtenirId().obtenirValor() + " ");
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)  {
        tipusItem = new TipusItem("escalar");
        domini.classes.atributs.valors.ValorNumeric valorAtribut = new domini.classes.atributs.valors.ValorNumeric();
        tipusItem = new TipusItem("escalar", new TreeMap<>(Map.of(
                "escalar", new TipusAtribut(new domini.classes.atributs.valors.ValorNumeric(),
                                                new domini.classes.atributs.distancia.DistanciaEuclidiana()))));
        items = new ArrayList<>();
        System.out.println("Driver per la classe KNN");
        String consulta = "\n0 - Sortir\n" +
                "1 - Afegeix un item\n" +
                "2 - Troba items propers\n";
        String err = "Valor invàlid: introdueix un enter entre 0 i 3";
        while(true){
            try {
                int i = consola.llegirInt(consulta, err, 0, 3);
                switch (i) {
                    case 0:
                        return;
                    case 1:
                        afegirItem();
                        break;
                    case 2:
                        consultaVeins();
                        break;
                }
            }
            catch(Exception e) {
                System.out.println("Torna-ho a provar.");
            }
        }
    }
}