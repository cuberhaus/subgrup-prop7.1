package domini.classes.recomanador;

import domini.classes.ConjuntItems;
import domini.classes.ConjuntUsuaris;
import domini.classes.ConjuntValoracions;
import domini.classes.recomanador.filtre.Filtre;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanador;

/**
 * @author maria.prat
 */
public abstract class Recomanador {
    protected ConjuntUsuaris usuaris;
    protected ConjuntItems items;
    protected ConjuntValoracions valoracionsPubliques;
    protected Filtre filtre;
    protected MetodeRecomanador metodeRecomanador;
}
