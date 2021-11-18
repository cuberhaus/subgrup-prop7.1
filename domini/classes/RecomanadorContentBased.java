package domini.classes;

import domini.classes.filtre.Filtre;
import domini.classes.metode_recomanador.MetodeRecomanadorContentBased;

public class RecomanadorContentBased extends Recomanador {
    public RecomanadorContentBased(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques, Filtre filtre) {
        this.usuaris = usuaris;
        this.items = items;
        this.valoracionsPubliques = valoracionsPubliques;
        this.filtre = filtre;
        this.metodeRecomanador = new MetodeRecomanadorContentBased(usuaris, filtre.filtrar(items), valoracionsPubliques);
    }
}
