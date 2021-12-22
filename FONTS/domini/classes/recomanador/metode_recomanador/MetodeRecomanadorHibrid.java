package domini.classes.recomanador.metode_recomanador;

import domini.classes.*;
import domini.classes.recomanador.ConjuntRecomanacions;
import domini.classes.recomanador.Recomanacio;
import excepcions.NoExisteixElementException;
import utilitats.Pair;

import java.util.*;

public class MetodeRecomanadorHibrid extends MetodeRecomanador {

    private final MetodeRecomanadorCollaborative metodeCollab;
    private final MetodeRecomanadorContentBased metodeContent;
    /**
     * Crea un <code>MetodeRecomanador</code> donant un conjunt de dades per defecte.
     *
     * @param usuaris              pot ser buit
     * @param items                pot ser buit
     * @param valoracionsPubliques pot ser buit
     */
    public MetodeRecomanadorHibrid(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques) {
        super(usuaris, items, valoracionsPubliques);
        metodeCollab = new MetodeRecomanadorCollaborative(usuaris, items, valoracionsPubliques);
        metodeContent = new MetodeRecomanadorContentBased(usuaris, items, valoracionsPubliques);
    }

    // Una primera aproximacio es demanar a cada mètode el doble dels necessaris i ordenar-los per les valoracions normalitzades.
    @Override
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, ConjuntItems conjuntRecomanable, ConjuntValoracions valoracionsUsuari, int numRecomanacions) throws NoExisteixElementException {
        ArrayList<Recomanacio> recCollab = metodeCollab.obteRecomanacions(usuari, conjuntRecomanable, valoracionsUsuari, 2*numRecomanacions).obtenirConjuntRecomanacions();
        ArrayList<Recomanacio> recContent = metodeContent.obteRecomanacions(usuari, conjuntRecomanable, valoracionsUsuari, 2*numRecomanacions).obtenirConjuntRecomanacions();
        double factorCollab = 0.;
        double factorContent = 0.;

        TreeMap<Id, Double> itemSeguretatContent = new TreeMap<>();
        for (Recomanacio rec : recCollab) {
            factorCollab = Math.max(factorCollab, rec.obtenirSeguretat());
        }
        for (Recomanacio recomanacio : recContent) {
            itemSeguretatContent.put(recomanacio.obtenirId(), recomanacio.obtenirSeguretat());
            factorContent = Math.max(factorContent, recomanacio.obtenirSeguretat());
        }
        // Totes les puntuacions de 0 a 5
        factorCollab = 5./factorCollab;
        factorContent = 5./factorContent;
        PriorityQueue<Pair<Double, Id>> pq = new PriorityQueue<>();
        for (Recomanacio rec : recCollab) {
            double seguretat = rec.obtenirSeguretat()*factorCollab;
            if (itemSeguretatContent.containsKey(rec.obtenirId())) {
                seguretat += itemSeguretatContent.get(rec.obtenirId())*factorContent;
                itemSeguretatContent.remove(rec.obtenirId());
            }
            if (pq.size() < numRecomanacions) {
                pq.add(new Pair<>(seguretat, rec.obtenirId()));
            }
            else if (!pq.isEmpty() && pq.peek().x < seguretat) {
                pq.remove();
                pq.add(new Pair<>(seguretat, rec.obtenirId()));
            }
        }
        for(var rec : itemSeguretatContent.entrySet()) {
            double seguretat = rec.getValue()*factorContent;
            if (pq.size() < numRecomanacions) {
                pq.add(new Pair<>(seguretat, rec.getKey()));
            }
            else if (!pq.isEmpty() && pq.peek().x < seguretat) {
                pq.remove();
                pq.add(new Pair<>(seguretat, rec.getKey()));
            }
        }
        ConjuntRecomanacions res = new ConjuntRecomanacions();
        while(!pq.isEmpty()) {
            Pair<Double, Id> pair = pq.remove();
            res.afegirRecomanacio(new Recomanacio(pair.y, pair.x));
        }
        res.ordena();
        return res;
    }
}
