package excepcions;

public class ContrasenyaIncorrectaException extends Exception{
    public ContrasenyaIncorrectaException(String errorMessage) {
        super(errorMessage);
    }
}
