package presentacio.controladors;

public class ControladorGestioUsuari {
    private ControladorPresentacio controladorPresentacio;

    public void iniciarSessio(int id, String contrasenya) {
        int idSessio = controladorPresentacio.obtenirSessio();
        if (idSessio == 0) {
            controladorPresentacio.iniciarSessio(id, contrasenya);
        }
        System.out.println("Has de tancar la sessió abans d'obrir-ne un altre");
    }
}
