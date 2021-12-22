package domini.classes.csv;


import excepcions.AccesAEstatIncorrecteException;
import excepcions.NoExisteixElementException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Representa un tipus de dades en el que emmagatzemem les dades llegides del CSV.
 * @author pablo.vega
 */
public class TaulaCSV {

    /** Variable on guardarem els atributs dels objectes que rebem del domini.tests.csv.*/
    private final ArrayList<ArrayList<String>> valorsItem;

    /** Variable que farem servir per trobar l'índex de l'atribut que volem consultar.*/
    private final HashMap<String, Integer> atributsAIndex;

    /** Variable que farem per trobar l'atribut que relaciona el número de columna a consultar.*/
    private final ArrayList<String> indexAAtributs;

    /** Variable que representa el nombre d'atributs de les dades introduïdes */
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
     * @throws AccesAEstatIncorrecteException si la taula es buida.
     */
    public TaulaCSV(ArrayList<ArrayList<String>> taula) throws AccesAEstatIncorrecteException {
        valorsItem = new ArrayList<>();
        atributsAIndex = new HashMap<>();
        indexAAtributs = new ArrayList<>();
        numAtributs = -1;
        numItems = 0;

        if (taula.size() == 0) {
            numAtributs = 0;
            return;
        }
        this.afegirConjuntAtributs(taula.get(0));

        for (int i = 1; i < taula.size(); ++i) {
            this.afegirConjuntValors(taula.get(i));
        }
        eliminarEspaisInnecessaris();
    }

    /**
     * Donada una llista d'atributs, els introdueix en l'estructura de dades.
     *
     * @param atributs és la llista d'atributs que volem tenir.
     * @throws IllegalCallerException si la taula ja ha estat inicialitzada amb uns atributs.
     */
    public void afegirConjuntAtributs(ArrayList<String> atributs) throws IllegalCallerException {
        if (numAtributs != -1) {
            throw new IllegalCallerException("La taula ja s'havia inicialitzat prèviament.");
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
     * @param valors conjunt de valors.
     * @throws IllegalCallerException si la taula no té els atributs prèviament definits o la quantitat de valors de l'ítem
     * a afegir no és igual al d'atributs.
     */
    public void afegirConjuntValors(ArrayList<String> valors) throws IllegalCallerException {
        if (numAtributs == -1) {
            throw new IllegalCallerException("No s'han inicialitzat els atributs");
        }

        else if (numAtributs != valors.size()) {
            throw new IllegalCallerException("La quantitat de valors de l'item a afegir es diferent a la " +
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
     * @throws AccesAEstatIncorrecteException els atributs de la taula no han estat inicialitzats 
     * @throws NoExisteixElementException l'atribut desitjat no es troba a la taula.
     */
    public ArrayList<String> obtenirValorsAtribut(String atribut) throws AccesAEstatIncorrecteException, NoExisteixElementException {
        if (numAtributs == -1) {
            throw new AccesAEstatIncorrecteException("No s'han inicialitzat els atributs");
        }
        else if (!this.atributsAIndex.containsKey(atribut)) {
            throw new NoExisteixElementException("L'atribut " + atribut + " no es troba en aquest contenidor");
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
     * @param indexAtribut the index atrib
     * @return the valors atribut
     * @throws Exception no s'han inicialitzat els atributs o l'índex de l'atribut no existeix.
     */
    public ArrayList<String> obtenirValorsAtribut(int indexAtribut) throws Exception {
        if (numAtributs == -1) {
            throw new Exception("No s'han inicialitzat els atributs");
        }
        else if (indexAtribut < 0 || indexAtribut >= numAtributs) {
            throw new Exception("L'index d'atribut dessitjat no es troba en aquest contenidor");
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
     * Retorna els valors de l'ítem seleccionat.
     * @param indexItem the index item
     * @return item
     * @throws AccesAEstatIncorrecteException no s'han inicialitzat els atributs de la taula
     * @throws NoExisteixElementException l'índex de l'ítem no existeix.
     */
    public ArrayList<String> obtenirItem(Integer indexItem) throws AccesAEstatIncorrecteException, NoExisteixElementException {
        if (numAtributs == -1) {
            throw new AccesAEstatIncorrecteException("No s'han inicialitzat els atributs");
        }

        else if (indexItem < 0 || numItems <= indexItem) {
            throw new NoExisteixElementException("L'index de l'item no correspon amb cap");
        }

        else {
            return new ArrayList<>(valorsItem.get(indexItem));
        }

    }

    /**
     * Retorna la llista d'atributs.
     *
     * @return atrib list
     * @throws AccesAEstatIncorrecteException la taula no ha estat inicialitzada amb els atributs desitjats.
     */
    public ArrayList<String> obtenirNomsAtributs() throws AccesAEstatIncorrecteException {
        if (numAtributs == -1) {
            throw new AccesAEstatIncorrecteException("No s'han inicialitzat els atributs");
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
     * @return valores de todos los items
     * @throws Exception la taula no ha estat inicialitzada amb els atributs desitjats.
     */
    public ArrayList<ArrayList<String>> obtenirValorsDeTotsElsItems() throws Exception {
        if (numAtributs == -1) {
            throw new Exception("No s'han inicialitzat els atributs");
        }

        else  {
            return new ArrayList<>(this.valorsItem);
        }
    }

    /**
     * Retorna el contingut de la taula amb els atributs
     *
     * @return table
     * @throws Exception la taula no ha estat inicialitzada amb els atributs desitjats.
     */
    public ArrayList<ArrayList<String>> obtenirTaula() throws Exception {
        if (numAtributs == -1) {
            throw new Exception("No s'han inicialitzat els atributs");
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
     * @throws Exception la taula no ha estat inicialitzada amb els atributs desitjats.
     */
    public void imprimir() throws Exception {
        if (numAtributs == -1) {
            throw new Exception("No s'han inicialitzat els atributs");
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
     * @param indexItem <code>int</code> indexItem de l'ítem
     * @param atribut <code>String</code> nom de l'atribut
     * @return <code>String</code> del valor de l'atribut
     * @throws AccesAEstatIncorrecteException la taula no ha estat inicialitzada amb els atributs desitjats
     * @throws NoExisteixElementException l'atribut no existeix o l'índex
     * de l'ítem no existeix.
     */
    public String obtenirValorAtribut(int indexItem, String atribut) throws NoExisteixElementException, AccesAEstatIncorrecteException {
        if (numAtributs == -1) {
            throw new AccesAEstatIncorrecteException("No s'han inicialitzat els atributs");
        }

        else if (!this.atributsAIndex.containsKey(atribut)) {
            throw new NoExisteixElementException("L'atribut " + atribut + " no existeix");
        }

        else if (indexItem < 0 || numItems <= indexItem) {
            throw new NoExisteixElementException("No existeix l'objecte");
        }

        return valorsItem.get(indexItem).get(atributsAIndex.get(atribut));

    }

    /**
     * Elimina els espais del principi i del final de la taula;
     * @throws AccesAEstatIncorrecteException la taula no ha estat inicialitzada amb els atributs desitjats.
     */
    public void eliminarEspaisInnecessaris() throws AccesAEstatIncorrecteException {
        if (!estaInicialitzada()) {
            throw new AccesAEstatIncorrecteException("La taula no ha estat inicialitzada.");
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