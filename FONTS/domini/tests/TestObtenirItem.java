package domini.tests;

import domini.classes.*;
import domini.controladors.ControladorDomini;
import java.util.*;

public class TestObtenirItem {
    public static void main(String[] args) {
        try {
            ControladorDomini domini = ControladorDomini.obtenirInstancia();
            ArrayList<String> tipus = domini.obtenirNomsTipusItemsCarregats();
            domini.seleccionarTipusItem(tipus.get(0));
            int userId = domini.afegirUsuari("testUser123", "password");
            if (userId == -1) userId = Integer.parseInt(domini.obtenirUsuaris().get(0).get(0));
            domini.iniciarSessio(userId, "password");
            
            ArrayList<String> nomsAtributs = domini.obtenirNomsAtributsTipusItemSeleccionat();
            System.out.println("nomsAtributs before: " + nomsAtributs);
            
            // Add a test valuation to prevent division by zero or errors if no ratings exist
            ArrayList<ArrayList<String>> items = domini.obtenirItems();
            String firstId = items.get(0).get(0);
            try { domini.afegirValoracio(String.valueOf(userId), firstId, "5.0"); } catch (Exception e) {}
            
            // RUN RECOMMENDER
            ArrayList<String> resultats = domini.obtenirRecomanacioCollaborative(nomsAtributs, true);
            System.out.println("resultats: " + resultats);
            
            if (!resultats.isEmpty()) {
                String recId = resultats.get(0);
                Map<String, String> itemMap = domini.obtenirItem(recId);
                System.out.println("Item map keys after recommender: " + itemMap.keySet());
                for (String attr : nomsAtributs) {
                    System.out.println("Value for " + attr + ": " + itemMap.getOrDefault(attr, "EMPTY"));
                }
            } else {
                System.out.println("No recommendations.");
                Map<String, String> itemMap = domini.obtenirItem(firstId);
                System.out.println("Item map keys after recommender: " + itemMap.keySet());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
