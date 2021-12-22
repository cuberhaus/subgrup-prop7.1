package domini.classes;

import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.distancia.*;
import domini.classes.atributs.valors.*;
import domini.classes.csv.TaulaCSV;
import excepcions.AccesAEstatIncorrecteException;
import excepcions.DistanciaNoCompatibleAmbValorException;
import excepcions.NoExisteixElementException;

import java.util.*;

/**
 * Representa un tipus d'ítem.
 * @author maria.prat
 */
public class TipusItem {
    /** Nom del tipus d'ítem. */
    private String nom;
    /** Relaciona els noms dels atributs amb el seu tipus. */
    private Map<String, TipusAtribut> tipusAtributs;

    /**
     * Constructor d'un TipusItem sense tipus d'atributs donat el seu nom.
     * @param nom Nom del TipusItem
     */
    public TipusItem(String nom) {
        this.nom = nom;
        this.tipusAtributs = new TreeMap<>();
    }

    /**
     * Constructor d'un TipusItem donat el nom i el <code>Map&lt;String, TipusAtribut&gt;</code> que conté els tipus d'atributs.
     * @param nom Nom del TipusItem
     * @param tipusAtributs <code>Map&lt;String, TipusAtribut&gt;</code> amb els tipus d'atributs
     */
    public TipusItem(String nom, Map<String, TipusAtribut> tipusAtributs) {
        this.nom = nom;
        this.tipusAtributs = tipusAtributs;
    }

    /**
     * Constructor d'un TipusItem donat el nom i un conjunt d'ítems.
     * Dedueix el <code>TipusAtribut</code> de cada atribut en funció dels valors dels primers 'numCandidats' ítems.
     * @param nomTipusItem <code>String</code> que conté el nom del TipusItem.
     * @param taulaCSV <code>TaulaCSV</code> que conté els ítems.
     * @param numCandidats Nombre d'ítems que es consideraran per deduir el tipus de cada atribut.
     * @throws AccesAEstatIncorrecteException si el 'numCandidats' no està entre 1 i el nombre total d'ítems.
     * @throws NoExisteixElementException si el format de la taula no es correcta
     * @throws DistanciaNoCompatibleAmbValorException si hi ha una parella de valor i distancia no compatible
     */
    public TipusItem(String nomTipusItem, TaulaCSV taulaCSV, int numCandidats) throws AccesAEstatIncorrecteException,
            NoExisteixElementException, DistanciaNoCompatibleAmbValorException {
        if (numCandidats < 0) {
            throw new IllegalArgumentException("Es necessita com a mínim un candidat per crear un TipusItem");
        }
        if (numCandidats > taulaCSV.obtenirNumItems()) {
            throw new IllegalArgumentException("No es poden considerar més candidats que ítems a la taula per deduir" +
                    " el TipusItem.");
        }
        this.nom = nomTipusItem;
        this.tipusAtributs = new TreeMap<>();
        for (int i = 0; i < taulaCSV.obtenirNomsAtributs().size(); ++i) {
            for (int j = 0; j < numCandidats; ++j) {
                if (taulaCSV.obtenirNomsAtributs().get(i).equals("id")) continue;
                if (this.tipusAtributs.containsKey(taulaCSV.obtenirNomsAtributs().get(i))) {
                    TipusAtribut tipusAtributActual = dedueixTipusAtribut(taulaCSV.obtenirValorAtribut(j,
                            taulaCSV.obtenirNomsAtributs().get(i)));
                    TipusAtribut tipusAtributNou = dedueixTipusAtribut(taulaCSV.obtenirValorAtribut(j,
                            taulaCSV.obtenirNomsAtributs().get(i)));
                    this.tipusAtributs.put(taulaCSV.obtenirNomsAtributs().get(i), trobaTipusAtribut(
                            tipusAtributActual.obtenirValorAtribut(), tipusAtributNou.obtenirValorAtribut()));
                } else {
                    this.tipusAtributs.put(taulaCSV.obtenirNomsAtributs().get(i),
                            dedueixTipusAtribut(taulaCSV.obtenirValorAtribut(j,
                                    taulaCSV.obtenirNomsAtributs().get(i))));
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipusItem tipusItem = (TipusItem) o;
        // Dos TipusItem són iguals si tenen el mateix nom i els mateixos tipus d'atributs.
        return nom.equals(tipusItem.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    /**
     * @return Còpia profunda del TipusItem.
     */
    public TipusItem copiar() {
        Map<String, TipusAtribut> tipusAtributs = new TreeMap<>();
        for (Map.Entry<String, TipusAtribut> tipusAtribut : this.tipusAtributs.entrySet()) {
            tipusAtributs.put(tipusAtribut.getKey(), tipusAtribut.getValue().copiar());
        }
        return new TipusItem(nom, tipusAtributs);
    }

    /**
     * Assigna el nom del TipusItem.
     * @param nom <code>String</code> que conté el nom del TipusItem.
     */
    public void assignarNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return <code>String</code> que conté el nom del TipusItem.
     */
    public String obtenirNom() {
        return nom;
    }

    /**
     * Assigna els tipusAtributs d'un TipusItem.
     * @param tipusAtributs <code>Map&lt;String, TipusAtribut&gt;</code> que conté els tipus d'atributs.
     */
    public void assignarTipusAtributs(Map<String, TipusAtribut> tipusAtributs) {
        this.tipusAtributs = tipusAtributs;
    }

    /**
     * @return <code>Map&lt;String, TipusAtribut&gt;</code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public Map<String, TipusAtribut> obtenirTipusAtributs() {
        Map<String, TipusAtribut> copiaTipusAtributs = new TreeMap<>();
        for (Map.Entry<String, TipusAtribut> entrada : tipusAtributs.entrySet()) {
            copiaTipusAtributs.put(entrada.getKey(), entrada.getValue().copiar());
        }
        return copiaTipusAtributs;
    }

    /**
     * Afegeix el TipusAtribut amb el nom donat als tipus d'atributs d'un TipusItem
     * Si 'tipusAtributs' ja contenia un tipus d'atribut amb el nom donat, el sobreescriu.
     * @param nomTipusAtribut Nom del TipusAtribut que s'afegirà als 'tipusAtributs' del TipusItem.
     * @param tipusAtribut TipusAtribut que s'afegirà als 'tipusAtributs' del TipusItem
     */
    public void afegirTipusAtribut(String nomTipusAtribut, TipusAtribut tipusAtribut) {
        this.tipusAtributs.put(nomTipusAtribut, tipusAtribut);
    }

    /**
     * Esborra els atributs amb els noms donats del TipusItem. Si hi ha un nom que no es correspon amb cap atribut,
     * s'ignora.
     * @param nomAtributs Noms dels atributs que s'esborraran del TipusItem
     */
    public void esborrarAtributs(TreeSet<String> nomAtributs) {
        for (String nomAtribut : nomAtributs) {
            tipusAtributs.remove(nomAtribut);
        }
    }

    /**
     * @return Una fila per cada Atribut, en l'ordre: nom, valor, distancia
     */
    public ArrayList<ArrayList<String>> convertirAArrayList() {
        ArrayList<ArrayList<String>> out = new ArrayList<>();
        for (var x : tipusAtributs.entrySet()) {
            ArrayList<String> fila = new ArrayList<>();
            fila.add(x.getKey());
            fila.add(x.getValue().obtenirValorAtribut().obteNomValor());
            fila.add(x.getValue().obtenirDistancia().obteNomDistancia());
            out.add(fila);
        }
        return out;
    }
    /**
     * @param valorAtribut1 ValorAtribut
     * @param valorAtribut2 ValorAtribut
     * @return Retorna el TipusAtribut més restrictiu que admet els dos ValorsAtributs donats. És a dir, el TipusAtribut
     * tal que els dos valors donats poden ser transformats en valors admesos per aquest.
     * @throws IllegalArgumentException si no hi ha una relació entre la parella de ValorsAtributs donats i no es pot
     * trobar un TipusAtribut que els reconegui als dos.
     * @throws DistanciaNoCompatibleAmbValorException no es pot calcular la distancia del valor atribut.
     */
    private static TipusAtribut trobaTipusAtribut(ValorAtribut<?> valorAtribut1,
                                                  ValorAtribut<?> valorAtribut2) throws IllegalArgumentException, DistanciaNoCompatibleAmbValorException {
        // Considerem cada cas particularment per a poder definir la distància que li correspon a cada TipusAtribut
        // i assegurar-nos que és compatible amb el ValorAtribut.
        if (valorAtribut1 instanceof ValorBoolea) {
            if (valorAtribut2 instanceof ValorBoolea) {
                return new TipusAtribut(new ValorBoolea(), new DistanciaDiscreta());
            } else if (valorAtribut2 instanceof ValorConjuntBoolea) {
                return new TipusAtribut(new ValorConjuntBoolea(), new DistanciaDiferenciaDeConjunts());
            } else if (valorAtribut2 instanceof ValorConjunt<?>) {
                return new TipusAtribut(new ValorConjuntCategoric(), new DistanciaDiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein());
            }
        } else if (valorAtribut1 instanceof ValorCategoric) {
            if (valorAtribut2 instanceof ValorConjunt<?>) {
                return new TipusAtribut(new ValorConjuntCategoric(), new DistanciaDiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein());
            }
        } else if (valorAtribut1 instanceof ValorNumeric) {
            if (valorAtribut2 instanceof ValorNumeric) {
                return new TipusAtribut(new ValorNumeric(), new DistanciaEuclidiana());
            } else if (valorAtribut2 instanceof ValorConjuntNumeric) {
                return new TipusAtribut(new ValorConjuntNumeric(), new DistanciaEuclidiana());
            } else if (valorAtribut2 instanceof ValorConjunt<?>) {
                return new TipusAtribut(new ValorConjuntCategoric(), new DistanciaDiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein());
            }
        } else if (valorAtribut1 instanceof ValorTextual) {
            if (valorAtribut2 instanceof ValorConjunt<?>) {
                return new TipusAtribut(new ValorConjuntTextual(), new DistanciaDiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorTextual(), new DistanciaLevenshtein());
            }
        } else if (valorAtribut1 instanceof ValorConjuntBoolea) {
            if (valorAtribut2 instanceof ValorConjuntBoolea) {
                return new TipusAtribut(new ValorConjuntBoolea(), new DistanciaDiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorConjuntCategoric(), new DistanciaDiferenciaDeConjunts());
            }
        } else if (valorAtribut1 instanceof ValorConjuntCategoric) {
            return new TipusAtribut(new ValorConjuntCategoric(), new DistanciaDiferenciaDeConjunts());
        } else if (valorAtribut1 instanceof ValorConjuntNumeric) {
            if (valorAtribut2 instanceof ValorConjuntNumeric) {
                return new TipusAtribut(new ValorConjuntNumeric(), new DistanciaEuclidiana());
            } else {
                return new TipusAtribut(new ValorConjuntCategoric(), new DistanciaDiferenciaDeConjunts());
            }
        } else if (valorAtribut1 instanceof ValorConjuntTextual) {
            return new TipusAtribut(new ValorConjuntTextual(), new DistanciaDiferenciaDeConjunts());
        } else {
            throw new IllegalArgumentException("No hi ha una relació definida per la parella de ValorsAtributs donada.");
        }
    }

    /**
     * @param s <code>String</code> que conté el valor d'un atribut.
     * @return TipusAtribut per defecte que admet el valor contingut en 's'.
     * @throws DistanciaNoCompatibleAmbValorException no es pot calcular la distancia
     */
    private TipusAtribut dedueixTipusAtribut(String s) throws DistanciaNoCompatibleAmbValorException {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e1) {
            if (ValorBoolea.esBoolea(s)) {
                return new TipusAtribut(new ValorBoolea(), new DistanciaDiscreta());
            }
            if (s.contains(";")) {
                String primerValor = s.split(";", 2)[0];
                try {
                    Double.parseDouble(primerValor);
                } catch (NumberFormatException e2) {
                    // Aquest mètode llegeix els conjunts de booleans com a ValorConjuntCategoric. Si és necessari que
                    // ho faci, es modificarà més endavant.
                    return new TipusAtribut(new ValorConjuntCategoric(), new DistanciaDiferenciaDeConjunts());
                }
                return new TipusAtribut(new ValorConjuntNumeric(), new DistanciaEuclidiana());
            }
            return new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein());
        }
        return new TipusAtribut(new ValorNumeric(), new DistanciaEuclidiana());
    }

    public String toString() {
        return obtenirNom();
    }


    /**
     * Canvia el nom del tipus item.
     * @param nouNom nou nom del tipus d'item.
     */
    public void canviaElNom(String nouNom) {
        nom = nouNom;
    }
}
