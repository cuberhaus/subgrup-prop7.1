package presentacio.controladors;

/**
 * @author pol.casacuberta
 */

public class ControladorGestioUsuari {
    private ControladorPresentacio controladorPresentacio;
    private static ControladorGestioUsuari instanciaUnica;

    private ControladorGestioUsuari() {
    }

    public static ControladorGestioUsuari obtenirInstancia(){
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorGestioUsuari();
        }
        return instanciaUnica;
    }

    public void iniciarSessio(String id, String contrasenya) {
        int idSessio = controladorPresentacio.obtenirSessio();

        if (id == null) {
            System.out.println("Id text is empty");
        }
        else {
            if (idSessio == 0) {
                controladorPresentacio.iniciarSessio(Integer.parseInt(id), contrasenya);
            }
            else {
                System.out.println("Has de tancar la sessió abans d'obrir-ne un altre");
            }
        }
    }
}
