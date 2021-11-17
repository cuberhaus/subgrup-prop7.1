package domini.classes;

import java.util.ArrayList;

public class ConjuntUsuaris extends ConjuntIdentificat<Usuari> {
    public void afegir(TaulaCSV taula) {
        ArrayList<String> idsUsuaris = taula.obtenirValorsAtribut("userId");
        for (String idUsuari : idsUsuaris) {
            int id = Integer.parseInt(idUsuari);
            this.afegir(new Usuari(id, true));
        }
    }
}
