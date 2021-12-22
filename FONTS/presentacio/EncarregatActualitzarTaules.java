package presentacio;

import java.util.ArrayList;
import java.util.List;

public class EncarregatActualitzarTaules {
    private static final List<Observador> observadors = new ArrayList<>();

    private EncarregatActualitzarTaules() {
    }

    public interface Observador {
        void actualitzar();
    }

    public static void notificarObservadors() {
        observadors.forEach(Observador::actualitzar);
    }

    public static void afegirObservador(Observador observador) {
        observadors.add(observador);
    }
}