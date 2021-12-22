package domini.classes;

import domini.classes.csv.TaulaCSV;
import excepcions.NoExisteixElementException;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 * Representa un conjunt d'usuaris
 *
 * @author pol.casacuberta
 */

public class ConjuntUsuaris extends ConjuntIdentificat<Usuari> {
    public ConjuntUsuaris() {
        elements = new TreeMap<>();
    }

    /**
     * Constructora de ConjuntUsuaris donada una llista d'usuaris
     * @param llistaUsuaris llista d'usuaris amb la que s'inicialitza el conjunt, per a cada fila l'ordre ha de ser:
     *                      IdUsuari, nomUsuari, contrasenyaUsuari, UsuariActiu
     */
    public ConjuntUsuaris(ArrayList<ArrayList<String>> llistaUsuaris) {
        elements = new TreeMap<>();
        if (llistaUsuaris.size() > 0) {
            llistaUsuaris.remove(0);
            for (ArrayList<String> usuari : llistaUsuaris) {
                try {
                    String id = usuari.get(1);
                    String nom = usuari.get(0);
                    String password = usuari.get(2);
                    String actiu = usuari.get(3);

                    boolean actiuUsuari = Boolean.parseBoolean(actiu.trim());
                    int idNum = Integer.parseInt(id);
                    Id idUsuari = new Id(idNum, actiuUsuari);
                    elements.put(idUsuari, new Usuari(idUsuari, nom, password));
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * Afegeix un conjunt de valoracions al Paràmetre implícit a partir de:
     *
     * @param taula Taula amb usuaris
     * @throws Exception no s'ha pogut eliminar els espais
     */
    public void afegir(TaulaCSV taula) throws Exception {
        taula.eliminarEspaisInnecessaris();
        ArrayList<String> idsUsuaris = taula.obtenirValorsAtribut("userId");
        for (String idUsuari : idsUsuaris) {
            int id = Integer.parseInt(idUsuari);
            this.afegir(new Usuari(new Id(id, true)));
        }
    }

    /**
     * Esborra un usuari del conjunt d'usuaris.
     *
     * @param id que correspon al usuari que volem esborrar
     */
    @Override
    public Usuari esborrar(Id id) throws NoExisteixElementException {
        Usuari u1 = this.obtenir(id);
        u1.setActiu(false);
        return elements.put(u1.obtenirId(), u1);
    }

    /**
     * Esborra un usuari del conjunt d'usuaris.
     * Retorna true si s'ha esborrat correctament, retorna false si no hi era
     *
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
     *
     * @return <code>ArrayList &lt;String&gt;</code> llista d'usuaris
     */
    public ArrayList<Usuari> obtenirUsuaris() {
        ArrayList<Usuari> usuaris = new ArrayList<>();
        Set<Id> keys = elements.keySet();
        for (Id id : keys) {
            usuaris.add(elements.get(id));
        }
        return usuaris;
    }

    /**
     * Obté els usuaris per al format CSV
     * @return <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> del contingut dels usuaris amb la capaçalera
     */
    public ArrayList<ArrayList<String>> obtenirUsuarisCSV() {
        ArrayList<ArrayList<String>> resultat = new ArrayList<>();
        ArrayList<String> usuaris = new ArrayList<>();

        ArrayList<String> atributs = new ArrayList<>();
        atributs.add("userId");
        atributs.add("nom");
        atributs.add("password");
        atributs.add("actiu");

        resultat.add(atributs);

        Set<Id> keys = elements.keySet();
        for (Id id : keys) {
            usuaris.add(elements.get(id).obtenirNom());
            usuaris.add(String.valueOf(elements.get(id).obtenirId().obtenirValor()));
            usuaris.add(elements.get(id).obteContrasenya());
            usuaris.add(String.valueOf(elements.get(id).isActiu()));
            resultat.add(new ArrayList<>(usuaris));
            usuaris.clear();
        }

        resultat.add(usuaris);
        return resultat;
    }

    /**
     * Retorna una llista amb tots els usuaris i els seus atributs
     * @return Llista amb tots els usuaris
     */
    public ArrayList<ArrayList<String>> obtenirLlistaUsuarisActius() {
        ArrayList<ArrayList<String>> resultat = new ArrayList<>();
        ArrayList<String> usuaris = new ArrayList<>();

        Set<Id> keys = elements.keySet();
        for (Id id : keys) {
            if (id.esActiu()) {
                usuaris.add(elements.get(id).obtenirNom());
                usuaris.add(String.valueOf(elements.get(id).obtenirId().obtenirValor()));
                usuaris.add(String.valueOf(elements.get(id).obtenirId().esActiu()));
                resultat.add(new ArrayList<>(usuaris));
                usuaris.clear();
            }
        }

        resultat.add(usuaris);
        return resultat;
    }

    /**
     * Modificadora que canvia l'estat de tots els usuaris a no actius
     */
    public void esborrarTotsUsuaris() {
        Set<Id> keys = elements.keySet();
        for (Id id : keys) {
            elements.get(id).setActiu(false);
        }
    }
}
