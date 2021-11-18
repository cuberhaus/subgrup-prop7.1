package domini.classes;

import domini.classes.filtre.Filtre;
import domini.classes.metode_recomanador.MetodeRecomanador;

public abstract class Recomanador {
    protected ConjuntUsuaris usuaris;
    protected ConjuntItems items;
    protected ConjuntValoracions valoracionsPubliques;
    protected Filtre filtre;
    protected MetodeRecomanador metodeRecomanador;
}
