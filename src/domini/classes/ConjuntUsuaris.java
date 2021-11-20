package domini.classes;

import domini.classes.csv.TaulaCSV;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 * Representa un conjunt d'usuaris
 * @author pol.casacuberta
 */

public class ConjuntUsuaris extends ConjuntIdentificat<Usuari> {
    public ConjuntUsuaris() {
        elements = new TreeMap<>();
    }
    /**
     * Afegeix un conjunt de valoracions al Paràmetre implícit a partir de:
     * @param taula Taula amb usuaris
     */
    public void afegir(TaulaCSV taula) {
        taula.eliminarEspaisInnecessaris();
//        elements = new TreeMap<>();
        ArrayList<String> idsUsuaris = taula.obtenirValorsAtribut("userId");
        for (String idUsuari : idsUsuaris) {
            int id = Integer.parseInt(idUsuari);
            this.afegir(new Usuari(new Id(id, true)));
        }
    }
    /**
     * Esborra un usuari del conjunt d'usuaris.
     * @param id que correspon al usuari que volem esborrar
     */
    @Override
    public  Usuari esborrar(Id id) {
        Usuari u1 = this.obtenir(id);
        u1.setActiu(false);
        return elements.put(u1.obtenirId(),u1);
    }

    /**
     * Esborra un usuari del conjunt d'usuaris.
     * Retorna true si s'ha esborrat correctament, retorna false si no hi era
     * @param usuari L'usuari que es vol esborrar del conjunt
     */
    @Override
    public boolean esborrar(Usuari usuari) {
        usuari.setActiu(false);
        if (!elements.containsKey(usuari.obtenirId())) {
            return false;
        }
        elements.put(usuari.obtenirId(), usuari);
        return true;
    }

    /**
     * Consultora que retorna una llista d'usuaris
     * @return <code>ArrayList<String></code> llista d'usuaris
     */
    public ArrayList<Usuari> obtenirUsuaris() {
        ArrayList<Usuari> usuaris = new ArrayList<>();
        Set<Id> keys = elements.keySet();
        for (Id id : keys) {
            usuaris.add(elements.get(id));
        }
        return usuaris;
    }
}
