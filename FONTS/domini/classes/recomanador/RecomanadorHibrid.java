package domini.classes.recomanador;

import domini.classes.ConjuntItems;
import domini.classes.ConjuntUsuaris;
import domini.classes.ConjuntValoracions;
import domini.classes.recomanador.filtre.Filtre;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanadorHibrid;

/**
 * Representa un recomanador col·laboratiu.
 * @author maria.prat
 */
public class RecomanadorHibrid extends Recomanador {
    public RecomanadorHibrid(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques, Filtre filtre) {
        this.usuaris = usuaris;
        this.items = items.copiar();
        filtre.filtrar(this.items);
        this.valoracionsPubliques = valoracionsPubliques;
        this.filtre = filtre;
        this.metodeRecomanador = new MetodeRecomanadorHibrid(usuaris, this.items, valoracionsPubliques);
    }
}
