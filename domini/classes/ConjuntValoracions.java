package domini.classes;

import java.util.ArrayList;

/**
 * Representa un conjunt de valoracions
 * @author pol.casacuberta
 */

public class ConjuntValoracions {

    public void afegir(String nomTipusItem,TaulaCSV valoracions, ConjuntItems items, ConjuntUsuaris usuaris) {

        ArrayList<String> idsUsuaris= valoracions.obtenirValorsAtribut("userId");
        ArrayList<String> idsItems= valoracions.obtenirValorsAtribut("itemId");
        ArrayList<String> valors= valoracions.obtenirValorsAtribut("rating");

        for (int i = 0; i < idsUsuaris.size(); ++i) {
            int idUsuariInt = Integer.parseInt(idsUsuaris.get(i));
            int idItemInt = Integer.parseInt(idsUsuaris.get(i));
            Id idItem= new Id (idItemInt,true);
            Id idUsuari= new Id (idUsuariInt,true);
            Item item = items.obte(idItem);
            Usuari usuari = usuaris.obte(idUsuari);
            double doubleValoracio = Double.parseDouble(valors.get(i));
            Valoracio valoracio = new Valoracio(doubleValoracio, usuari,item);
//            this.afegir(valoracio);
        }

    }
}
