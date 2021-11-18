package domini.classes.csv;

import domini.classes.Contenidor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Representa un tipus de dades en el que emmagatzarem les dades llegides del CSV.
 *
 * @author pablo.vega
 */
public class TaulaCSV extends Contenidor {

    /** Variable on guardarem els atributs del objectes que rebem del csv.*/
    private ArrayList<ArrayList<String>> valorsItem;

    /** Variable que farem servir per trobar l'índex de l'atribut que volem consultar.*/
    private HashMap<String, Integer> atributsAIndex;

    /** Variable que farem per trobar l'atribut que relaciona el número de columna a consultar.*/
    private ArrayList<String> indexAAtributs;

    /** Variable que representa el número d'atributs de les dades introduïdes */
    private Integer numAtribs;

    /** Variable que representa la quantitat d'ítems que tenim */
    private Integer numItems;

    /**
     * Constructora buida de la classe <code>CSVTable</code>
     */
    public TaulaCSV() {
        valorsItem = new ArrayList<> ();
        atributsAIndex = new HashMap<>();
        indexAAtributs = new ArrayList<>();
        numAtribs = -1;
        numItems = 0;
    }

    /**
     * Donada una llista d'atributs, els introdueix en la estructura de dades.
     *
     * @param atribs es la llista de atributs que volem tenir.
     */
    public void introduirListaAtributs(ArrayList<String> atribs) throws InterruptedException {
        if (numAtribs != -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else {
            this.numAtribs = atribs.size();
            ArrayList<String> copiaAtributs = new ArrayList<>(atribs);

            for (int i = 0; i < atribs.size(); ++i) {
                this.indexAAtributs.add(copiaAtributs.get(i));
                this.atributsAIndex.put(copiaAtributs.get(i), i);
            }
        }
    }

    /**
     * Donada una llista de valors, afegeix els valors al contenidor de valors.
     *
     * @param valors conjunt de valors.
     */
    public void introduirLlistaDeValors(ArrayList<String> valors) throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else if (numAtribs != valors.size()) {
            throw new IllegalArgumentException("La quantitat de valors de l'item a afegir es diferent a la " +
                    "quantitat d'atributs");
        }

        else {
            ArrayList<String> copiaValors = new ArrayList<>(valors);
            valorsItem.add(copiaValors);
            ++numItems;
        }
    }

    /**
     * Donat un atribut, retorna la llista de valors d'aquest atribut per aquest ítem.
     *
     * @param atribut the atribut
     * @return the valors atribut
     */
    public ArrayList<String> obtenirValorsAtribut(String atribut) throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }
        else if (!this.atributsAIndex.containsKey(atribut)) {
            throw new IllegalArgumentException("L'atribut dessitjat no es troba en aquest contenidor");
        }

        else {
            ArrayList<String> resultat = new ArrayList<>();
            Integer index = this.atributsAIndex.get(atribut);
            for (int i = 0; i < numItems; ++i) {
                resultat.add(this.valorsItem.get(i).get(index));
            }

            return resultat;
        }
    }

    /**
     * Gets valors atribut.
     *
     * @param indexAtrib the index atrib
     * @return the valors atribut
     */
    public ArrayList<String> obtenirValorsAtribut(int indexAtrib) throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }
        else if (indexAtrib < 0 || indexAtrib >= numAtribs) {
            throw new IllegalArgumentException("L'index d'atribut dessitjat no es troba en aquest contenidor");
        }

        else {
            ArrayList<String> resultat = new ArrayList<>();
            Integer index = indexAtrib;
            for (int i = 0; i < numItems; ++i) {
                resultat.add(this.valorsItem.get(i).get(index));
            }

            return resultat;
        }
    }

    /**
     * Retorna els valors de l'item seleccionat.
     *
     * @param indexItem the index item
     * @return item
     */
    public ArrayList<String> obtenirItem(Integer indexItem) throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else if (indexItem < 0 || numItems <= indexItem) {
            throw new IllegalArgumentException("L'index de l'item no correspon amb cap");
        }

        else {
            ArrayList<String> resultat = new ArrayList<>(valorsItem.get(indexItem));
            return resultat;
        }

    }

    /**
     * Retorna la llista d'adtributs.
     *
     * @return atrib list
     */
    public ArrayList<String> obtenirLlistaAtributs() throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else {
            ArrayList<String> atributs = new ArrayList<>();
            for (int i = 0; i < numAtribs; ++i) {
                atributs.add(this.indexAAtributs.get(i));
            }
            return atributs;
        }
    }

    /**
     * Retorna la llista de tots els valors dels ítems
     *
     * @return valores de todos los items
     */
    public ArrayList<ArrayList<String>> obtenirValorsDeTotsElsItems() throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else  {
            ArrayList<ArrayList<String>> resultat = new ArrayList<>(this.valorsItem);
            return resultat;
        }
    }

    /**
     * Retorna el contingut de la taula amb els atributs
     *
     * @return table
     */
    public ArrayList<ArrayList<String>> obtenirTaula() throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else {
            ArrayList<ArrayList<String>> resultat = new ArrayList<>();
            resultat.add(this.obtenirLlistaAtributs());
            for (ArrayList<String> elem : valorsItem) {
                resultat.add(elem);
            }
            return resultat;
        }
    }

    /**
     * Imprimeix el contingut de les dades.
     */
    public void imprimir() throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else {
            for (String elem : this.obtenirLlistaAtributs()) {
                System.out.print(elem);
                System.out.print('|');
            }

            System.out.println();

            for (ArrayList<String> fila : valorsItem) {
                for (String elem : fila) {
                    System.out.print(elem);
                    System.out.print(" | ");
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
    public ArrayList<String> obtenirValorsDeItemSeleccionat(String id, String valor) throws InterruptedException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else if (!this.atributsAIndex.containsKey(id)) {
            throw new IllegalArgumentException("L'atribut seleccionat no existeix");
        }

        else  {
            Integer index = this.atributsAIndex.get(id);

            Integer tamany = this.valorsItem.size();
            boolean trobat = false;
            Integer i = 0;

            while (!trobat && i < tamany) {
                ArrayList<String> fila = valorsItem.get(i);
                trobat = (fila.get(index).equals(valor));
                ++i;
            }

            if (!trobat) {
                System.out.println("No existeix un item amb el valor de l'tribut dessitjat");
                return null;
            }

            else {
                ArrayList<String> resultat = new ArrayList<>(valorsItem.get(i - 1));
                resultat.remove(index);
                return resultat;
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
    public ArrayList<String> obtenirValorsDeItemSeleccionat(Integer ind, String valor) throws InterruptedException, IllegalArgumentException {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else if (ind < 0 || numAtribs <= ind) {
            throw new IllegalArgumentException("L'atribut seleccionat no existeix");
        }

        else  {
            Integer index = ind;

            Integer refactor = this.valorsItem.size();
            boolean trobat = false;
            Integer i = 0;

            while (!trobat && i < refactor) {
                ArrayList<String> fila = valorsItem.get(i);
                trobat = (fila.get(index).equals(valor));
                ++i;
            }

            if (!trobat) {
                System.out.println("No existeix un item amb el valor de l'tribut dessitjat");
                return null;
            }

            else {
                ArrayList<String> resultat = new ArrayList<>(valorsItem.get(i - 1));
                resultat.remove(index);
                return resultat;
            }
        }
    }

    /**
     * Donat un item i un atribut et retorna el valor
     * @param indexItem <code>int</code> indexItem de l'item
     * @param atribut <code>String</code> nom de l'atribut
     * @return <code>String</code> del valor de l'atribut
     * @throws InterruptedException
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    public String obtenirValorAtributItem(int indexItem, String atribut) throws InterruptedException, IllegalArgumentException,
            IllegalStateException
    {
        if (numAtribs == -1) {
            throw new InterruptedException("No s'han inicialitzat els atributs");
        }

        else if (!this.atributsAIndex.containsKey(atribut)) {
            throw new IllegalArgumentException("L'atribut seleccionat no existeix");
        }

        else if (indexItem < 0 || numItems <= indexItem) {
            throw new IllegalStateException("No existeix l'objecte");
        }

        return valorsItem.get(indexItem).get(atributsAIndex.get(atribut));

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

    public int obtenirNumeroElements() {
        return numItems;
    }
    public int obtenirNumeroAtrib() {
        return numAtribs;
    }
}