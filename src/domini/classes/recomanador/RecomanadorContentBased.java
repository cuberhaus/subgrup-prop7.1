package domini.classes.recomanador;

import domini.classes.ConjuntItems;
import domini.classes.ConjuntUsuaris;
import domini.classes.ConjuntValoracions;
import domini.classes.recomanador.filtre.Filtre;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanadorContentBased;

public class RecomanadorContentBased extends Recomanador {
    public RecomanadorContentBased(ConjuntUsuaris usuaris, ConjuntItems items, ConjuntValoracions valoracionsPubliques, Filtre filtre) {
        this.usuaris = usuaris;
        this.items = items;
        this.valoracionsPubliques = valoracionsPubliques;
        this.filtre = filtre;
        this.metodeRecomanador = new MetodeRecomanadorContentBased(usuaris, filtre.filtrar(items), valoracionsPubliques);
    }
}
