package domini.classes.recomanador;

import domini.classes.ConjuntItems;
import domini.classes.ConjuntUsuaris;
import domini.classes.ConjuntValoracions;
import domini.classes.recomanador.filtre.Filtre;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanadorCollaborative;

/**
 * Representa un recomanador col·laboratiu.
 * Aquesta classe encara no està completament implementada, ja que no és una funcionalitat principal del projecte.
 * @author maria.prat
 */
public class RecomanadorCollaborative extends Recomanador {
    public RecomanadorCollaborative(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques, Filtre filtre) {
        this.usuaris = usuaris;
        this.items = items.copiar();
        filtre.filtrar(this.items);
        this.valoracionsPubliques = valoracionsPubliques;
        this.filtre = filtre;
        this.metodeRecomanador = new MetodeRecomanadorCollaborative(usuaris, this.items, valoracionsPubliques);
    }
}
