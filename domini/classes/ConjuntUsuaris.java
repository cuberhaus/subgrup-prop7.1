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
    @Override
    public  Usuari esborrar(Id id) {
        Usuari u1 = this.obtenir(id);
        u1.setActiu(false);
        return elements.put(u1.obtenirId(),u1);
    }

    @Override
    public boolean esborrar(Usuari usuari) {
        usuari.setActiu(false);
        if (!elements.containsKey(usuari.obtenirId())) {
            return false;
        }
        elements.put(usuari.obtenirId(), usuari);
        return true;
    }

}
