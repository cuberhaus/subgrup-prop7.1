package presentacio;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa l'encarregat d'actualitzar les taules de les vistes del programa quan s'actualitza algun
 * element que es comu als observadors d'aquest encarregat. Implementa el patro de disseny Observador.
 * @author maria.prat
 */
public class EncarregatActualitzarVistes {
    private static final ArrayList<Observador> observadors = new ArrayList<>();

    /**
     * Constructor per defecte
     */
    private EncarregatActualitzarVistes() {
    }

    /**
     * Interficie que han d'implementar els observadors d'aquest encarregat
     */
    public interface Observador {
        /**
         * Actualitza els elements dels observadors que estan subscrits a aquest encarregat
         */
        void actualitzar();
    }

    /**
     * Notific cada un dels observadors de l'encarregar
     */
    public static void notificarObservadors() {
        observadors.forEach(Observador::actualitzar);
    }

    /**
     * @param observador Observador que s'afegeix a la llista de subscripts a l'encarregat
     */
    public static void afegirObservador(Observador observador) {
        observadors.add(observador);
    }
}