package domini.classes;

import domini.classes.csv.TaulaCSV;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Representa un conjunt d'usuaris
 * @author pol.casacuberta
 */

public class ConjuntUsuaris extends ConjuntIdentificat<Usuari> {
    public void afegir(TaulaCSV taula) throws InterruptedException {
        elements = new TreeMap<>();
        ArrayList<String> idsUsuaris = taula.obtenirValorsAtribut("userId");
        for (String idUsuari : idsUsuaris) {
            int id = Integer.parseInt(idUsuari);
            this.afegir(new Usuari(id, true));
        }
    }
}
