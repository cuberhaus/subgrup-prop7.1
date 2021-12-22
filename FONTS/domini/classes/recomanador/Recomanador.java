package domini.classes.recomanador;

import domini.classes.ConjuntItems;
import domini.classes.ConjuntUsuaris;
import domini.classes.ConjuntValoracions;
import domini.classes.Usuari;
import domini.classes.recomanador.filtre.Filtre;
import domini.classes.recomanador.metode_recomanador.MetodeRecomanador;
import excepcions.NoExisteixElementException;

/**
 * Representa un recomanador.
 * @author maria.prat
 */
public abstract class Recomanador {
    protected ConjuntUsuaris usuaris;
    protected ConjuntItems items;
    protected ConjuntValoracions valoracionsPubliques;
    protected Filtre filtre;
    protected MetodeRecomanador metodeRecomanador;

    /**
     * @param usuari Usuari a qui recomanar
     * @param numRecomanacions nombre de recomanacions sol·licitades
     * @return El conjunt de recomanacions per l'usuari
     * @throws NoExisteixElementException Si hi ha algun error durant l'obtencio de les recomanacions
     */
    public ConjuntRecomanacions obteRecomanacions(Usuari usuari, int numRecomanacions) throws NoExisteixElementException {
        return metodeRecomanador.obteRecomanacions(usuari, numRecomanacions);
    }
}
