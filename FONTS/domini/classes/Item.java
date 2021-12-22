package domini.classes;

import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.valors.*;
import excepcions.FormatIncorrecteException;
import excepcions.UsuariIncorrecteException;

import java.util.*;

/**
 * Representa un ítem.
 * @author maria.prat
 */
public class Item implements Comparable<Item>, ElementIdentificat {
    private final Id id;
    private final TipusItem tipusItem;
    /**
     * Conté els valors dels atributs de l'Item.
     * Relaciona el nom dels atributs amb el seu valor.
     */
    private Map<String, ValorAtribut<?>> atributs;
    /**
     * Conté les valoracions de l'Item.
     * Relaciona l'Usuari que ha fet la valoració amb la valoració.
     */
    private final Map<Usuari, Valoracio> valoracions;

    /**
     * Constructor d'un ítem amb conjunt de valoracions buit.
     * @param id <code>Id</code> que conté l'identificador de l'ítem.
     * @param tipusItem <code>TipusItem</code> que conté el tipus de l'ítem.
     * @param atributs <code>Map&lt;String, ValorAtribut&lt;?&gt; &gt; </code> que relaciona els noms dels atributs de l'Item amb el
     *                 seu valor.
     * @param valoracions <code>Map&lt;Usuari, Valoracio&gt;</code> que relacions els Usuaris que han fet les valoracions de
     *                    l'Item amb la valoració.
     */
    public Item(Id id, TipusItem tipusItem, Map<String, ValorAtribut<?>> atributs, Map<Usuari, Valoracio> valoracions) {
        this.id = id;
        this.tipusItem = tipusItem;
        this.atributs = atributs;
        this.valoracions = valoracions;
    }

    /**
     * Constructor d'un ítem amb conjunt de valoracions buit.
     * Actualitza els factors de normalització del TipusItem de l'ítem.
     * @param id <code>Id</code> que conté l'identificador de l'ítem.
     * @param tipusItem <code>TipusItem</code> que conté el tipus de l'ítem.
     * @param nomAtributs <code>ArrayList&lt;String&gt;</code> que conté els noms dels atributs els valors dels quals es
     *                    troben a <code>valors</code>.
     * @param valors <code>ArrayList&lt;String&gt;</code> que conté els valors de l'ítem en forma de String.
     * @throws IllegalArgumentException si els valors donats no són compatibles amb el TipusItem.
     * @throws FormatIncorrecteException El format és incorrecte
     */
    public Item(Id id, TipusItem tipusItem, ArrayList<String> nomAtributs, ArrayList<String> valors) throws IllegalArgumentException, FormatIncorrecteException {
        this.id = id;
        this.tipusItem = tipusItem;
        assignarAtributs(nomAtributs, valors);
        actualitzarFactorNormalitzacioAtributs();
        this.valoracions = new TreeMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        // Dos ítems són iguals si tenen identificadors iguals.
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return Còpia profunda de l'Item sense valoracions, ja que no es pot copiar l'estructura de valoracions.
     */
    public Item copiar() {
        return new Item(this.obtenirId(), this.obtenirTipusItem(), this.obtenirAtributs(), new TreeMap<>());
    }

    @Override
    public int compareTo(Item o) {
        return id.compareTo(o.id);
    }

    /**
     * @return Còpia de l'identificador de l'Item.
     */
    public Id obtenirId() { return id.copiar(); }

    /**
     * @return Còpia del TipusItem de l'Item.
     */
    public TipusItem obtenirTipusItem() {
        return tipusItem.copiar();
    }

    /**
     * @return Còpia profunda del <code>Map&lt;String, ValorAtribut&lt;?&gt;&gt;</code> que relaciona els noms dels atributs de
     * l'Item amb el seu valor.
     */
    public Map<String, ValorAtribut<?>> obtenirAtributs() {
        Map<String, ValorAtribut<?>> atributs = new TreeMap<>();
        for (Map.Entry<String, ValorAtribut<?>> valorAtributEntry : this.atributs.entrySet()) {
            atributs.put(valorAtributEntry.getKey(), valorAtributEntry.getValue().copiar());
        }
        return atributs;
    }

    /**
     * @return Còpia profunda del <code>Map&lt;Usuari, Valoracio&gt;</code> que relaciona els Usuaris que han fet les
     * valoracions de l'Item amb la valoració.
     * @throws UsuariIncorrecteException no trobem l'usuari
     */
    public Map<Usuari, Valoracio> obtenirValoracions() throws UsuariIncorrecteException {
        Map<Usuari, Valoracio> valoracions = new TreeMap<>();
        for (Map.Entry<Usuari, Valoracio> valoracioEntry : this.valoracions.entrySet()) {
            valoracions.put(valoracioEntry.getKey().copiar(), valoracioEntry.getValue().copiar());
        }
        return valoracions;
    }

    /**
     * La distància entre dos Items es defineix com la suma de les distàncies normalitzades entre tots els atributs dels
     * Items, que han de ser del mateix tipus. La normalització es fa per evitar que un atribut tingui més importància
     * que un altre.
     * @param item Item amb el qual es vol calcular la distància.
     * @return <code>double</code> que conté la distància entre l'ítem actual i l'ítem donat, si els ítem són del mateix
     * tipus.
     * @throws IllegalArgumentException si l'Item donat és nul o els Items no són del mateix TipusItem.
     */
    public double obtenirDistancia(Item item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("No es pot calcular la distància entre dos ítems quan un d'ells es nul");
        }
        if (!tipusItem.equals(item.tipusItem)) {
            throw new IllegalArgumentException("No es pot calcular la distància entre dos ítems de TipusItems diferents.");
        }
        double distancia = 0.0;
        for (Map.Entry<String, TipusAtribut> tipusAtribut : tipusItem.obtenirTipusAtributs().entrySet()) {
            if (atributs.get(tipusAtribut.getKey()) == null || item.atributs.get(tipusAtribut.getKey()) == null) continue;
            distancia += tipusAtribut.getValue().obtenirDistancia().obtenir(atributs.get(tipusAtribut.getKey()),
                    item.atributs.get(tipusAtribut.getKey())) /
                    (tipusAtribut.getValue().obtenirDistancia().obtenirFactorDeNormalitzacio());
        }
        return distancia;
    }

    /**
     * @param valoracio Valoració que s'afegeix a les valoracions de l'Item.
     * @throws IllegalArgumentException Si la valoració donada és nul·la o l'ha feta un Item diferent de l'actual.
     */
    public void afegirValoracio(Valoracio valoracio) throws IllegalArgumentException {
        if (valoracio == null) {
            throw new IllegalArgumentException("No es pot afegir una valoració nul·la.");
        }
        if (!this.equals(valoracio.obtenirItem())) {
            throw new IllegalArgumentException("No es pot afegir a un ítem una valoració d'un altre ítem.");
        }
        valoracions.put(valoracio.obtenirUsuari(), valoracio);
    }

    /**
     * Esborra els atributs amb els noms donats del TipusItem de l'ítem i dels atributs de l'ítem. Si un dels noms donats
     * no es correspon amb cap atribut, s'ignora.
     * @param nomAtributs Conjunt de Strings que conté noms d'atributs.
     */
    public void esborrarAtributs(TreeSet<String> nomAtributs) {
        tipusItem.esborrarAtributs(nomAtributs);
        for (String nomAtribut : nomAtributs) {
            atributs.remove(nomAtribut);
        }
    }

    /**
     * @param valorAtribut ValorAtribut del tipus que es vol crear
     * @param s <code>String</code> que conté el valor de l'atribut
     * @return <code>ValorAtribut</code> de la mateixa subclasse que 'valorAtribut' i amb el valor que conté 's'
     * @throws IllegalArgumentException Si no es pot llegir el valor de la classe donada de la String donada o si no es
     * @throws FormatIncorrecteException El format no és correcte
     * reconeix la subclasse de 'valorAtribut'.
     */
    private ValorAtribut<?> obtenirValorAtribut(ValorAtribut<?> valorAtribut, String s) throws IllegalArgumentException, FormatIncorrecteException {
        if (valorAtribut instanceof ValorBoolea) {
            return new ValorBoolea(s);
        } else if (valorAtribut instanceof ValorCategoric) {
            return new ValorCategoric(s);
        } else if (valorAtribut instanceof ValorNumeric) {
            return new ValorNumeric(Double.parseDouble(s));
        } else if (valorAtribut instanceof ValorTextual) {
            return new ValorTextual(s);
        } else if (valorAtribut instanceof ValorConjuntBoolea) {
            return new ValorConjuntBoolea(s);
        } else if (valorAtribut instanceof ValorConjuntCategoric) {
            return new ValorConjuntCategoric(s);
        } else if (valorAtribut instanceof ValorConjuntNumeric) {
            return new ValorConjuntNumeric(s);
        } else if (valorAtribut instanceof ValorConjuntTextual) {
            return new ValorConjuntTextual(s);
        } else {
            throw new IllegalArgumentException("El ValorAtribut donat no és una instància de cap subclasse reconeguda.");
        }
    }

    /**
     * Assigna els noms i valors d'atributs donats als atributs de l'Item.
     * @param nomAtributs Noms dels atributs
     * @param valors Valors dels atributs guardats com Strings
     * @throws IllegalArgumentException Si els noms i valors donats no són compatibles amb el TipusItem de l'Item.
     * @throws FormatIncorrecteException El format no és correcte
     */
    private void assignarAtributs(ArrayList<String> nomAtributs, ArrayList<String> valors) throws IllegalArgumentException, FormatIncorrecteException {
        atributs = new TreeMap<>();
        for (int i = 0; i < nomAtributs.size(); ++i) {
            if (nomAtributs.get(i).equals("id")) continue;
            if (!tipusItem.obtenirTipusAtributs().containsKey(nomAtributs.get(i))) {
                throw new IllegalArgumentException("El TipusItem no és compatible amb els noms dels atributs donats.");
            }
            atributs.put(nomAtributs.get(i),
                    obtenirValorAtribut(tipusItem.obtenirTipusAtributs().get(nomAtributs.get(i)).obtenirValorAtribut(),
                            valors.get(i)));
        }
    }

    /**
     * Actualitza els factors de normalització de tots els TipusAtributs del TipusItem de l'Item.
     */
    private void actualitzarFactorNormalitzacioAtributs() {
        for (Map.Entry<String, TipusAtribut> atribut : tipusItem.obtenirTipusAtributs().entrySet()) {
            atribut.getValue().obtenirDistancia().actualitzarFactorDeNormalitzacio(atributs.get(atribut.getKey()));
        }
    }

    /**
     * @return una <code>ArrayList</code> amb el valor de cada atribut en l'ordre per defecte del map.
     */
    public ArrayList<String> converteixAArray() {
        ArrayList<String> res = new ArrayList<>();
        res.add(Integer.toString(id.valor));
        for (Map.Entry<String, ValorAtribut<?>> x : atributs.entrySet()) {
           res.add(x.getValue().toString());
        }
        return res;
    }


    /**
     * Modifica un atribut a un valor nou, si no existeix no fa res.
     * @param nomAtribut nom d'atribut a modificar
     * @param valor nou valor
     */
    public void modificarAtribut(String nomAtribut, ValorAtribut<?> valor) {
        if (!atributs.containsKey(nomAtribut))
            return;
        atributs.put(nomAtribut, valor);
    }

    /**
     * Canvia el nom del tipus item.
     * @param nouNom nou nom del tipus d'item.
     */
    public void canviarNomTipusItem(String nouNom) {
        tipusItem.canviarNom(nouNom);
    }
}
