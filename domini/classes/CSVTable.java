package domini.classes;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Representa un tipus de dades en el que emmagatzarem les dades llegides del CSV.
 *
 * @author pablo.vega
 */
public class CSVTable {

    /** Variable on guardarem els atributs del objectes que rebem del csv.*/
    private ArrayList<ArrayList<String>> valoresDeItem;

    /** Variable que farem servir per trobar l'índex de l'atribut que volem consultar.*/
    private HashMap<String, Integer> atributosToIndex;

    /** Variable que farem per trobar l'atribut que relaciona el número de columna a consultar.*/
    private ArrayList<String> indexToAtributos;

    /** Variable que representa el número d'atributs de les dades introduïdes */
    private Integer numAtribs;

    /** Variable que representa la quantitat d'ítems que tenim */
    private Integer numItems;

    /**
     * Constructora buida de la classe <code>CSVTable</code>
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
     *
     * @param atribs es la llista de atributs que volem tenir.
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
     *
     * @param valors conjunt de valors.
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
     * Donat un atribut, retorna la llista de valors d'aquest atribut per aquest ítem.
     *
     * @param atribut the atribut
     * @return the valors atribut
     */
    public ArrayList<String> obtenirValorsAtribut(String atribut) {
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
     * Gets valors atribut.
     *
     * @param indexAtrib the index atrib
     * @return the valors atribut
     */
    public ArrayList<String> obtenirValorsAtribut(int indexAtrib) {
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
            Integer index = indexAtrib;
            for (int i = 0; i < numItems; ++i) {
                resultado.add(this.valoresDeItem.get(i).get(index));
            }

            return resultado;
        }
    }

    /**
     * Retorna els valors de l'item seleccionat.
     *
     * @param indexItem the index item
     * @return item
     */
    public ArrayList<String> obtenirItem(Integer indexItem) {
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
     * Retorna la llista d'adtributs.
     *
     * @return atrib list
     */
    public ArrayList<String> obtenirLlistaAtributs() {
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
     *
     * @return valores de todos los items
     */
    public ArrayList<ArrayList<String>> obtenirValorsDeTotsElsItems() {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
            return null;
        }

        else  {
            ArrayList<ArrayList<String>> resultado = new ArrayList<>(this.valoresDeItem);
            return resultado;
        }
    }

    /**
     * Retorna el contingut de la taula amb els atributs
     *
     * @return table
     */
    public ArrayList<ArrayList<String>> obtenirTaula() {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
            return null;
        }

        else {
            ArrayList<ArrayList<String>> resultado = new ArrayList<>();
            resultado.add(this.obtenirLlistaAtributs());
            for (ArrayList<String> elem : valoresDeItem) {
                resultado.add(elem);
            }
            return resultado;
        }
    }

    /**
     * Imprimeix el contingut de les dades.
     */
    public void imprimir() {
        if (numAtribs == -1) {
            System.out.println("Encara no s'han introduit els atributs");
        }

        else {
            for (String elem : this.obtenirLlistaAtributs()) {
                System.out.print(elem);
                System.out.print('|');
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
     * Gets valores de item con valor.
     *
     * @param id    the id
     * @param valor the valor
     * @return the valores de item con valor
     */
    public ArrayList<String> obtenirValorsDeItemSeleccionat(String id, String valor) {
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
     * Gets valores de item con valor.
     *
     * @param ind   the ind
     * @param valor the valor
     * @return the valores de item con valor
     */
    public ArrayList<String> obtenirValorsDeItemSeleccionat(Integer ind, String valor) {
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
                ArrayList<String> resultado = new ArrayList<>(valoresDeItem.get(i - 1));
                resultado.remove(index);
                return resultado;
            }
        }
    }

    /**
     * Is initialized boolean.
     *
     * @return the boolean
     */
    public boolean estaInicialitzat() {
        return numAtribs != -1;
    }

    /**
     * Print num elems.
     */
    public void imprimirNumeroElements() {
        System.out.println(numItems);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("Funciona Bien");
    }
}