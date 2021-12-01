package domini.classes.csv;

import domini.classes.Contenidor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Representa un tipus de dades en el que emmagatzarem les dades llegides del CSV.
 * @author pablo.vega
 */
public class TaulaCSV extends Contenidor {

    /** Variable on guardarem els atributs del objectes que rebem del domini.tests.csv.*/
    private final ArrayList<ArrayList<String>> valorsItem;

    /** Variable que farem servir per trobar l'índex de l'atribut que volem consultar.*/
    private final HashMap<String, Integer> atributsAIndex;

    /** Variable que farem per trobar l'atribut que relaciona el número de columna a consultar.*/
    private final ArrayList<String> indexAAtributs;

    /** Variable que representa el número d'atributs de les dades introduïdes */
    private Integer numAtributs;

    /** Variable que representa la quantitat d'ítems que tenim */
    private Integer numItems;

    /**
     * Constructora buida de la classe <code>CSVTable</code>
     */
    public TaulaCSV() {
        valorsItem = new ArrayList<> ();
        atributsAIndex = new HashMap<>();
        indexAAtributs = new ArrayList<>();
        numAtributs = -1;
        numItems = 0;
    }

    /**
     * Constructora partint d'un contingut.
     * @param taula ArrayList que conté tot el contingut de la taula.
     */
    public TaulaCSV(ArrayList<ArrayList<String>> taula) {
        valorsItem = new ArrayList<>();
        atributsAIndex = new HashMap<>();
        indexAAtributs = new ArrayList<>();
        numAtributs = -1;
        numItems = 0;

        if (taula.size() == 0) {
            throw new IllegalArgumentException("La taula es buida");
        }
        this.afegirConjuntAtributs(taula.get(0));

        for (int i = 1; i < taula.size(); ++i) {
            this.afegirConjuntValors(taula.get(i));
        }
        eliminarEspaisInnecessaris();
    }

    /**
     * Donada una llista d'atributs, els introdueix en la estructura de dades.
     *
     * @param atributs es la llista de atributs que volem tenir.
     */
    public void afegirConjuntAtributs(ArrayList<String> atributs) throws IllegalStateException {
        if (numAtributs != -1) {
            throw new IllegalStateException("La taula ja s'havia inicialitzat prèviament.");
        }

        else {
            this.numAtributs = atributs.size();
            ArrayList<String> copiaAtributs = new ArrayList<>(atributs);

            for (int i = 0; i < atributs.size(); ++i) {
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
    public void afegirConjuntValors(ArrayList<String> valors) throws IllegalStateException {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
        }

        else if (numAtributs != valors.size()) {
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
    public ArrayList<String> obtenirValorsAtribut(String atribut) throws IllegalStateException {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
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
     * @param indexAtribut the index atrib
     * @return the valors atribut
     */
    public ArrayList<String> obtenirValorsAtribut(int indexAtribut) throws IllegalStateException {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
        }
        else if (indexAtribut < 0 || indexAtribut >= numAtributs) {
            throw new IllegalArgumentException("L'index d'atribut dessitjat no es troba en aquest contenidor");
        }

        else {
            ArrayList<String> resultat = new ArrayList<>();
            for (int i = 0; i < numItems; ++i) {
                resultat.add(this.valorsItem.get(i).get(indexAtribut));
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
    public ArrayList<String> obtenirItem(Integer indexItem) throws IllegalStateException {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
        }

        else if (indexItem < 0 || numItems <= indexItem) {
            throw new IllegalArgumentException("L'index de l'item no correspon amb cap");
        }

        else {
            return new ArrayList<>(valorsItem.get(indexItem));
        }

    }

    /**
     * Retorna la llista d'adtributs.
     *
     * @return atrib list
     */
    public ArrayList<String> obtenirNomsAtributs() throws IllegalStateException {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
        }

        else {
            ArrayList<String> atributs = new ArrayList<>();
            for (int i = 0; i < numAtributs; ++i) {
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
    public ArrayList<ArrayList<String>> obtenirValorsDeTotsElsItems() throws IllegalStateException {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
        }

        else  {
            return new ArrayList<>(this.valorsItem);
        }
    }

    /**
     * Retorna el contingut de la taula amb els atributs
     *
     * @return table
     */
    public ArrayList<ArrayList<String>> obtenirTaula() throws IllegalStateException {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
        }

        else {
            eliminarEspaisInnecessaris();
            ArrayList<ArrayList<String>> resultat = new ArrayList<>();
            resultat.add(this.obtenirNomsAtributs());
            resultat.addAll(valorsItem);
            return resultat;
        }
    }

    /**
     * Imprimeix el contingut de les dades.
     */
    public void imprimir() throws IllegalStateException {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
        }

        else {
            for (String elem : this.obtenirNomsAtributs()) {
                System.out.print(elem);
                System.out.print(" | ");
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
     * Donat un item i un atribut et retorna el valor
     * @param indexItem <code>int</code> indexItem de l'item
     * @param atribut <code>String</code> nom de l'atribut
     * @return <code>String</code> del valor de l'atribut
     * @throws IllegalArgumentException l'atribut seleccionat no existeix a la taula i/o l'index no existeix
     * @throws IllegalStateException la taula no s'ha inicialitzat
     */
    public String obtenirValorAtribut(int indexItem, String atribut) throws IllegalStateException, IllegalArgumentException
    {
        if (numAtributs == -1) {
            throw new IllegalStateException("No s'han inicialitzat els atributs");
        }

        else if (!this.atributsAIndex.containsKey(atribut)) {
            throw new IllegalArgumentException("L'atribut " + atribut + " no existeix");
        }

        else if (indexItem < 0 || numItems <= indexItem) {
            throw new IllegalArgumentException("No existeix l'objecte");
        }

        return valorsItem.get(indexItem).get(atributsAIndex.get(atribut));

    }

    /**
     * Elimina els espais del principi i del final de la taula;
     */
    public void eliminarEspaisInnecessaris() {
        if (!estaInicialitzada()) {
            throw new IllegalStateException("La taula no ha estat inicialitzada.");
        }
        for (int i = 0; i < numAtributs; ++i) {
            String nomRetallat = indexAAtributs.get(i).trim();
            if (!nomRetallat.equals(indexAAtributs.get(i))) {
                atributsAIndex.remove(indexAAtributs.get(i));
                atributsAIndex.put(indexAAtributs.get(i), i);
                indexAAtributs.set(i, nomRetallat);
            }
        }
        for (int i = 0; i < numItems; ++i) {
            for (int j = 0; j < numAtributs; ++j) {
                valorsItem.get(i).set(j, valorsItem.get(i).get(j).trim());
            }
        }
    }

    /**
     * Retorna si la taula és inicialitzada.
     * @return <code>boolean</code>.
     */
    public boolean estaInicialitzada() {
        return numAtributs != -1;
    }
    public int obtenirNumItems() {
        return numItems;
    }
    public int obtenirNumAtributs() {
        return numAtributs;
    }
}