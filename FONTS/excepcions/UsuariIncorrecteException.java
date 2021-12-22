package excepcions;

public class UsuariIncorrecteException extends Exception{
    public UsuariIncorrecteException(String errorMessage) {
        super(errorMessage);
    }
}
