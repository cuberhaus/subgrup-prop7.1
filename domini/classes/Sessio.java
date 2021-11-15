package domini.classes;

public abstract class Sessio {
    abstract void tancarSessio(Programa programa);

    abstract void iniciarSessio(Programa programa, Usuari usuari);

    abstract boolean isSessioIniciada();
}
