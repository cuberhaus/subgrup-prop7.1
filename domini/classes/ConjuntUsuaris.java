package domini.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// utilitzar obtenirValorsAtribut
public class ConjuntUsuaris {
    /** Conté el conjunt d'usuaris */
    private Map<Usuari,Usuari> usuaris = new HashMap<Usuari,Usuari>();

    public void inicialitzarUsuaris(String ubicacio) throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        TaulaCSV taula = lector.llegirCSV(ubicacio);
        ArrayList<String> users = taula.obtenirValorsAtribut("userId");
        for (String user : users) {
            int id = Integer.parseInt(user);
            Usuari u1 = new Usuari(id,true);
            if (!usuaris.containsKey(u1)) {
                usuaris.put(u1,u1);
            }
        }
    }
    public void esborraUsuari(Usuari usuari) {
        usuari.setActiu(false);
        usuaris.replace(usuari,usuari,usuari);
    }
}
