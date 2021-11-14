package Domini;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Representa un tipus de dades en el que guardarem una sèrie d'objectes (cada fila del CSV) amb una sèrie d'atributs (cada columna del CSV).
 * @author pablo.vega
 */

public class CSVTable {

    /** Estructura on guardarem els atributs dels objectes, cada fila representa un objecte, i cada columna un atribut d'aquest objecte.*/
    private ArrayList<ArrayList<String>> valoresDeItem;

    /** Estructura on guardarem la relació entre l'atribut i el número de columna.*/
    private HashMap<String, Integer> atributosToIndex;

    /** Estructura on guardarem la relació entre el número de columna i l'atribut.*/
    private ArrayList<String> indexToAtributos;

    /** Variable que representa el número d'atributs de les dades introduïdes. */
    private Integer numAtribs;

    /** Variable que representa la quantitat d'ítems que tenim carregats. */
    private Integer numItems;

    /**
     * Constructora buida de la classe <code>CSVTable</code>.
     */
    public CSVTable() {
        valoresDeItem = new ArrayList<> ();
        atributosToIndex = new HashMap<>();
        indexToAtributos = new ArrayList<>();
        numAtribs = -1;
        numItems = 0;
    }

    /**
     * Donada una llista d'atributs, els introdueix en la estructura de dades.
     * @param atribs <code>ArrayList<String></code> és la llista d'atributs que té aquest objecte.
     */
    public void introduirListaAtributs(ArrayList<String> atribs) {
        if (numAtribs != -1) {
            System.out.println("No es pot introduir de nou una llista d'atributs, proba a crear un nou objecte");
        }

        else {

            this.numAtribs = atribs.size();
            ArrayList<String> copyAtribs = new ArrayList<>(atribs);

            for (int i = 0; i < atribs.size(); ++i) {
                this.indexToAtributos.add(copyAtribs.get(i));
                this.atributosToIndex.put(copyAtribs.get(i), i);
            }
        }
    }

    /**
     * Donada una llista de valors, afegeix els valors al contenidor de valors.
     * @param valors <code>ArrayList<String></code> que conté els valors d'un objecte.
     */
    public void introduirLlistaDeValors(ArrayList<String> valors) {
        if (numAtribs == -1) {
            System.out.println("Encara no has introduit la quantitat d'atributs de l'ítem a emmagatzemar, proba a cridar" +
                    "la funcio '.introduirLlistaAtributs' ");
        }

        else if (numAtribs != valors.size()) {
            System.out.println("El numero d'atributs de l'item a afegir es diferent al numero d'atributs del tipus d'item.");
        }

        else {
            ArrayList<String> valorsCopy = new ArrayList<>(valors);
            valoresDeItem.add(valorsCopy);
            ++numItems;
        }
    }

    /**
     * Donat un atribut, retorna la llista de valors d'aquest atribut de tots els objectes del conjunt.
     * @param atribut <code>String</code> que conté el nom de l'atribut del qual volem la llista de valors
     * @return <code>ArrayList<String></code> llista de valors de l'atribut seleccionat o null si l'atribut no existeix o no s'han inicialitzat els atributs.
     */
    public ArrayList<String> getValorsAtribut(String atribut) {
        if (numAtribs == -1) {
            System.out.println("No has introduit els atributs");
            return null;
        }
        else if (!this.atributosToIndex.containsKey(atribut)) {
            System.out.println("L'atribut dessitjat no es troba en aquest contenidor");
            return null;
        }

        else {
            ArrayList<String> resultado = new ArrayList<>();
            Integer index = this.atributosToIndex.get(atribut);
            for (int i = 0; i < numItems; ++i) {
                resultado.add(this.valoresDeItem.get(i).get(index));
            }

            return resultado;
        }
    }

    /**
     * Retorna la llista dels valors de l'index de l'atribut dessitjat.
     * @param indexAtrib <code>Integer</code> que conté l'índex de l'atribut dessitjat.
     * @return <code>ArrayList<String></code> llista de valors de l'atribut seleccionat o altrament null si no existeix l'índex o no s'han inicialitzat els atributs
     */
    public ArrayList<String> getValorsAtribut(Integer indexAtrib) {
        if (numAtribs == -1) {
            System.out.println("No has introduit els atributs");
            return null;
        }
        else if (indexAtrib < 0 || indexAtrib >= numAtribs) {
            System.out.println("L'index de l'atribut dessitjat no es troba en aquest contenidor");
            return null;
        }

        else {
            ArrayList<String> resultado = new ArrayList<>();
            int index = indexAtrib;
            for (int i = 0; i < numItems; ++i) {
                resultado.add(this.valoresDeItem.get(i).get(index));
            }

            return resultado;
        }
    }

    /**
     * Retorna els valors de l'item seleccionat.
     * @param indexItem <code>Integer</code> és l'índex de l'ítem dessitjat.
     * @return <code>ArrayList<String></code> llista que conté els valors de l'item seleccionat o altrament null si l'índex no existeix o si no s'han inicialitzat els atributs encara
     */
    public ArrayList<String> getItem(Integer indexItem) {
        if (numAtribs == -1) {
            System.out.println("Atributs no inicialitzats");
            return null;
        }

        else if (indexItem < 0 || numAtribs <= indexItem) {
            System.out.println("L'índex de l'item no es el valid");
            return null;
        }

        else {
            ArrayList<String> resultado = new ArrayList<>(valoresDeItem.get(indexItem));
            return resultado;
        }

    }

    /**
     * Retorna la llista d'atributs.
     * @return <code>ArrayList<String></code> la llista d'atributs o altrament null si no s'han inicialitzat els atributs.
     */
    public ArrayList<String> getAtribList() {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
            return null;
        }

        else {
            ArrayList<String> atributos = new ArrayList<>();
            for (int i = 0; i < numAtribs; ++i) {
                atributos.add(this.indexToAtributos.get(i));
            }
            return atributos;
        }
    }

    /**
     * Retorna la llista de tots els valors dels ítems
     * @return <code>ArrayList<ArrayList<String>></code> retorna tot el conjunt o altrament null si no s'han inicialitzat els atributs.
     */
    public ArrayList<ArrayList<String>> getValoresDeTodosLosItems() {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
            return null;
        }

        else  {
            ArrayList<ArrayList<String>> resultado = new ArrayList<ArrayList<String>>(this.valoresDeItem);
            return resultado;
        }
    }

    /**
     * Retorna el contingut de la taula amb els atributs
     * @return <code>ArrayList<ArrayList<String>></code> retorna el conjunt amb la primera fila els atributs.
     */
    public ArrayList<ArrayList<String>> getTable() {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
            return null;
        }

        else {
            ArrayList<ArrayList<String>> resultado = new ArrayList<>();
            resultado.add(this.getAtribList());
            for (ArrayList<String> elem : valoresDeItem) {
                resultado.add(elem);
            }
            return resultado;
        }
    }

    /**
     * Imprimeix el contingut de les dades.
     */
    public void print() {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
        }

        else {
            for (String elem : this.getAtribList()) {
                System.out.print(elem);
                System.out.print(' ');
            }

            System.out.println();

            for (ArrayList<String> fila : valoresDeItem) {
                for (String elem : fila) {
                    System.out.print(elem);
                    System.out.print(' ');
                }
                System.out.println();
            }
        }
    }

    /**
     * Retorna l'objecte amb els seus atributs que compleix la condició de tenir el valor dessitjat en l'atribut seleccionat
     * @param id <code>String</code> del nom de l'atribut
     * @param valor <code>String</code> del valor de l'atribut
     * @return <code>ArrayList<String></code> llista de valors de l'objecte o altrament null si no s'han inicialitzat els atributs, no existeix l'atribut o no existeix cap objecte amb aquest valor en aquell atribut.
     */
    public ArrayList<String> getValoresDeItemConValor(String id, String valor) {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
            return null;
        }

        else if (!this.atributosToIndex.containsKey(id)) {
            System.out.println("L'atribut seleccionat no existeix");
            return null;
        }

        else  {
            Integer index = this.atributosToIndex.get(id);

            Integer tamano = this.valoresDeItem.size();
            boolean trobat = false;
            Integer i = 0;

            while (!trobat && i < tamano) {
                ArrayList<String> fila = valoresDeItem.get(i);
                trobat = (fila.get(index).equals(valor));
                ++i;
            }

            if (!trobat) {
                System.out.println("No existeix un item amb el valor de l'tribut dessitjat");
                return null;
            }

            else {
                ArrayList<String> resultado = new ArrayList<>(valoresDeItem.get(i - 1));
                resultado.remove(index);
                return resultado;
            }
        }
    }

    /**
     * Retorna l'objecte amb els seus atributs que compleix la condició de tenir el valor dessitjat en l'atribut seleccionat
     * @param ind <code>Integer</code> index de l'atribut
     * @param valor <code>String</code> del valor de l'atribut
     * @return <code>ArrayList<String></code> llista de valors de l'objecte o altrament null si no s'han inicialitzat els atributs, no existeix l'index de l'atribut o no existeix cap objecte amb aquest valor en aquell atribut.
     */
    public ArrayList<String> getValoresDeItemConValor(Integer ind, String valor) {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
            return null;
        }

        else if (ind < 0 || numAtribs <= ind) {
            System.out.println("L'atribut seleccionat no existeix");
            return null;
        }

        else  {
            Integer index = ind;

            Integer tamano = this.valoresDeItem.size();
            boolean trobat = false;
            Integer i = 0;

            while (!trobat && i < tamano) {
                ArrayList<String> fila = valoresDeItem.get(i);
                trobat = (fila.get(index).equals(valor));
                ++i;
            }

            if (!trobat) {
                System.out.println("No existeix un item amb el valor de l'tribut dessitjat");
                return null;
            }

            else {
                ArrayList<String> resultado = new ArrayList<>(this.valoresDeItem.get(i - 1));
                resultado.remove(index);
                return resultado;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Funciona Good");
    }
}