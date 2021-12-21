package excepcions;

public class SessioIniciadaException extends Exception{
    public SessioIniciadaException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
