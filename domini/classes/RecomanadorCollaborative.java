package domini.classes;

import domini.classes.filtre.Filtre;
import domini.classes.metode_recomanador.MetodeRecomanadorCollaborative;

public class RecomanadorCollaborative extends Recomanador {
    public RecomanadorCollaborative(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques, Filtre filtre) {
        this.usuaris = usuaris;
        this.items = items;
        this.valoracionsPubliques = valoracionsPubliques;
        this.filtre = filtre;
        this.metodeRecomanador = new MetodeRecomanadorCollaborative(usuaris, filtre.filtrar(items), valoracionsPubliques);
    }
}
