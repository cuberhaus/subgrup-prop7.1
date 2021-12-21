package excepcions;

public class SessioNoIniciadaException extends Exception{
    public SessioNoIniciadaException(String errorMessage) {
        super(errorMessage);
    }
}
