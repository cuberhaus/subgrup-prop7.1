package excepcions;

public class SessioNoIniciadaException extends Exception{
    public SessioNoIniciadaException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
