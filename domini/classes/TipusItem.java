package domini.classes;

import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.distancia.*;
import domini.classes.atributs.valors.*;
import domini.classes.csv.TaulaCSV;

import java.util.HashMap;
import java.util.Map;

/**
 * Representa un tipus d'ítem.
 * @author maria.prat
 */
public class TipusItem {
    private String nom;
    /** Relaciona el nom d'un atribut amb el seu tipus. */
    private Map<String, TipusAtribut> tipusAtributs;

    /**
     * Crea un <code>TipusItem</code> amb el nom i el <code>Map<String, TipusAtribut></code> donats.
     * @param nom <code>String</code> que conté el nom del <code>TipusItem</code>.
     * @param tipusAtributs <code>Map<String, TipusAtribut></code> que conté els tipus d'atributs del <code>TipusItem</code>.
     */
    public TipusItem(String nom, Map<String, TipusAtribut> tipusAtributs) {
        this.nom = nom;
        this.tipusAtributs = tipusAtributs;
    }

    public TipusItem(String nomTipusItem, TaulaCSV taulaCSV) throws IllegalArgumentException {
        this(nomTipusItem, taulaCSV, taulaCSV.obtenirNumItems());
    }

    /**
     * Crea un <code>TipusItem</code> donats el nom del <code>TipusItem</code> i un conjunt d'ítems.
     * Assigna el <code>TipusAtribut</code> per defecte en funció del tipus de cada valor.
     * @param nomTipusItem <code>String</code> que conté el nom del <code>TipusItem</code>.
     * @param taulaCSV <code>TaulaCSV<String></code> que conté els ítems.
     * @param numCandidats Es deduirà el <code>TipusItem</code> a partir dels primers <code>numCandidats</code> ítems.
     */
    public TipusItem(String nomTipusItem, TaulaCSV taulaCSV, int numCandidats) throws IllegalArgumentException {
        if (numCandidats > taulaCSV.obtenirNumItems()) {
            throw new IllegalArgumentException("No es poden considerar més candidats que ítems a la taula per deduir" +
                    " el TipusItem.");
        }
        this.nom = nomTipusItem;
        this.tipusAtributs = new HashMap<>();
        for (int i = 0; i < taulaCSV.obtenirNomsAtributs().size(); ++i) {
            for (int j = 0; j < numCandidats; ++j) {
                if (this.tipusAtributs.containsKey(taulaCSV.obtenirNomsAtributs().get(i))) {
                    TipusAtribut tipusAtributActual = dedueixTipusAtribut(taulaCSV.obtenirValorAtribut(j,
                            taulaCSV.obtenirNomsAtributs().get(i)));
                    TipusAtribut tipusAtributNou = dedueixTipusAtribut(taulaCSV.obtenirValorAtribut(j,
                            taulaCSV.obtenirNomsAtributs().get(i)));
                    this.tipusAtributs.put(taulaCSV.obtenirNomsAtributs().get(i), trobaTipusAtributMenysRestrictiu(
                            tipusAtributActual.obtenirValorAtribut(), tipusAtributNou.obtenirValorAtribut()));
                } else {
                    this.tipusAtributs.put(taulaCSV.obtenirNomsAtributs().get(i),
                            dedueixTipusAtribut(taulaCSV.obtenirValorAtribut(j,
                                    taulaCSV.obtenirNomsAtributs().get(i))));
                }
            }
        }
    }

    private TipusAtribut trobaTipusAtributMenysRestrictiu(ValorAtribut<?> valorAtribut1,
                                                             ValorAtribut<?> valorAtribut2) throws IllegalArgumentException {
        // Considerem cada cas particularment per a poder definir la distància que li correspon a cada TipusAtribut
        // i assegurar-nos que és compatible amb el ValorAtribut.
        if (valorAtribut1 instanceof ValorBoolea) {
            if (valorAtribut2 instanceof ValorBoolea) {
                return new TipusAtribut(new ValorBoolea(), new Discreta());
            } else if (valorAtribut2 instanceof ValorConjuntBoolea) {
                return new TipusAtribut(new ValorConjuntBoolea(), new DiferenciaDeConjunts());
            } else if (valorAtribut2 instanceof ValorConjunt<?>) {
                return new TipusAtribut(new ValorConjuntCategoric(), new DiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorCategoric(), new Levenshtein());
            }
        } else if (valorAtribut1 instanceof ValorCategoric) {
            if (valorAtribut2 instanceof ValorConjunt<?>) {
                return new TipusAtribut(new ValorConjuntCategoric(), new DiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorCategoric(), new Levenshtein());
            }
        } else if (valorAtribut1 instanceof ValorNumeric) {
            if (valorAtribut2 instanceof ValorNumeric) {
                return new TipusAtribut(new ValorNumeric(), new Euclidiana());
            } else if (valorAtribut2 instanceof ValorConjuntNumeric) {
                return new TipusAtribut(new ValorConjuntNumeric(), new Euclidiana());
            } else if (valorAtribut2 instanceof ValorConjunt<?>) {
                return new TipusAtribut(new ValorConjuntCategoric(), new DiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorCategoric(), new Levenshtein());
            }
        } else if (valorAtribut1 instanceof ValorTextual) {
            if (valorAtribut2 instanceof ValorConjunt<?>) {
                return new TipusAtribut(new ValorConjuntTextual(), new DiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorTextual(), new Levenshtein());
            }
        } else if (valorAtribut1 instanceof ValorConjuntBoolea) {
            if (valorAtribut2 instanceof ValorConjuntBoolea) {
                return new TipusAtribut(new ValorConjuntBoolea(), new DiferenciaDeConjunts());
            } else {
                return new TipusAtribut(new ValorConjuntCategoric(), new DiferenciaDeConjunts());
            }
        } else if (valorAtribut1 instanceof ValorConjuntCategoric) {
            return new TipusAtribut(new ValorConjuntCategoric(), new DiferenciaDeConjunts());
        } else if (valorAtribut1 instanceof ValorConjuntNumeric) {
            if (valorAtribut2 instanceof ValorConjuntNumeric) {
                return new TipusAtribut(new ValorConjuntNumeric(), new Euclidiana());
            } else {
                return new TipusAtribut(new ValorConjuntCategoric(), new DiferenciaDeConjunts());
            }
        } else if (valorAtribut1 instanceof ValorConjuntTextual) {
            return new TipusAtribut(new ValorConjuntTextual(), new DiferenciaDeConjunts());
        } else {
            throw new IllegalArgumentException("No hi ha una relació de restrictivitat definida per la parella de ValorsAtributs donada.");
        }
    }

    private TipusAtribut dedueixTipusAtribut(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e1) {
            if (ValorBoolea.esBoolea(s)) {
                return new TipusAtribut(new ValorBoolea(), new Discreta());
            }
            if (s.contains(";")) {
                String primerValor = s.split(";", 2)[0];
                try {
                    Double.parseDouble(primerValor);
                } catch (NumberFormatException e2) {
                    // TODO(maria): pensar si volem que aquest mètode pugui llegir conjunts de booleans (caldria iterar
                    // per tots els elements per assegurar que són tots booleans. L'alternativa és fer el cast més endavant
                    // si l'usuari ho demana.
                    return new TipusAtribut(new ValorConjuntCategoric(), new DiferenciaDeConjunts());
                }
                return new TipusAtribut(new ValorConjuntNumeric(), new Euclidiana());
            }
            return new TipusAtribut(new ValorCategoric(), new Levenshtein());
        }
        return new TipusAtribut(new ValorNumeric(), new Euclidiana());
    }

    /**
     * Assigna el nom del <code>TipusItem</code>.
     * @param nom <code>String</code> que conté el nom del <code>TipusItem</code>.
     */
    public void assignarNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return <code>String</code> que conté el nom del <code>TipusItem</code>.
     */
    public String obtenirNom() {
        return nom;
    }

    /**
     * Assigna els <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * @param tipusAtributs <code>Map<String, TipusAtribut></code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public void assignarTipusAtributs(Map<String, TipusAtribut> tipusAtributs) {
        this.tipusAtributs = tipusAtributs;
    }

    /**
     * @return <code>Map<String, TipusAtribut></code> que conté els <code>tipusAtributs</code> del <code>TipusItem</code>.
     */
    public Map<String, TipusAtribut> obtenirTipusAtributs() {
        Map<String, TipusAtribut> copiaTipusAtributs = new HashMap<>();
        for (Map.Entry<String, TipusAtribut> entrada : tipusAtributs.entrySet()) {
            copiaTipusAtributs.put(entrada.getKey(), entrada.getValue().copy());
        }
        return copiaTipusAtributs;
    }

    /**
     * Afegeix el <code>TipusAtribut</code> amb el nom donats als <code>tipusAtributs</code> d'un <code>TipusItem</code>.
     * Si <code>tipusAtributs</code> ja contenia un tipus d'atribut amb el nom donat, el sobreescriu.
     * @param nomTipusAtribut Nom del <code>TipusAtribut</code> que s'afegirà als <code>tipusAtributs</code> del
     *                        <code>TipusItem</code>.
     * @param tipusAtribut <code>TipusAtribut</code> que s'afegirà als <code>tipusAtributs</code> del
     *                     <code>TipusItem</code>.
     */
    public void afegirTipusAtribut(String nomTipusAtribut, TipusAtribut tipusAtribut) {
        this.tipusAtributs.put(nomTipusAtribut, tipusAtribut);
    }

    /**
     * Un conjunt d'atributs és compatible amb un tipus d'ítem si hi ha una bijecció entre el conjunt d'atributs i el
     * conjunt de tipus d'atributs del tipus d'ítem, de manera que cada atribut es relaciona amb un tipus d'atribut amb
     * el mateix nom i el mateix TipusAtribut.
     * @param atributs <code>Map<String, ValorAtribut></code> que relaciona el nom d'un atribut amb el seu <code>ValorAtribut</code>.
     * @return Retorna cert si els atributs donats són compatibles amb el tipus d'ítem actual i, altrament, retorna fals.
     */
    public boolean esCompatible(Map<String, ValorAtribut<?>> atributs) {
        if (tipusAtributs.size() != atributs.size()) {
            return false;
        }
        for (Map.Entry<String, ValorAtribut<?>> atribut : atributs.entrySet()) {
            if (!tipusAtributs.containsKey(atribut.getKey())) {
                return false;
            }
            if (tipusAtributs.get(atribut.getKey()).obtenirValorAtribut().getClass() != atribut.getValue().getClass()) {
                return false;
            }
        }
        return true;
    }
}
