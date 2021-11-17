package domini.classes;

import java.util.ArrayList;

public class ConjuntValoracions {
    public void afegir(TaulaCSV taula) {
        ArrayList<String> idsUsuaris= taula.obtenirValorsAtribut("userId");
        ArrayList<String> idsItems= taula.obtenirValorsAtribut("itemId");
        ArrayList<String> valors= taula.obtenirValorsAtribut("rating");
//        for (String idUsuari : idsUsuaris) {
//            int id = Integer.parseInt(idUsuari);
//            this.afegir(new Valoracio());
//        }
        for (int i = 0; i < idsUsuaris.size(); ++i) {
            int idUsuari = Integer.parseInt(idsUsuaris.get(i));
            int idItem = Integer.parseInt(idsUsuaris.get(i));
            Usuari usuari = new Usuari(idUsuari,true);
            this.afegir(new Valoracio(valors.get(i),));
        }

    }
}
