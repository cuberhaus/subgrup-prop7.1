package domini.classes;

import java.util.ArrayList;

public class Punt extends ArrayList<Double> {
    public int getDimensio() {
        return size();
    }

    public Object clone() {
        return super.clone();
    }

    public void afegir(Punt a) throws IllegalArgumentException {
        if (a.getDimensio() != getDimensio()) {
            throw new IllegalArgumentException("Els punts no es poden afegir ja que no tenen la mateixa dimensió.");
        }
        for (int i = 0; i < getDimensio(); ++i) {
            set(i, get(i) + a.get(i));
        }
    }

    public void resta(Punt a) throws IllegalArgumentException {
        if (a.getDimensio() != getDimensio()) {
            throw new IllegalArgumentException("Els punts no es poden restar ja que no tenen la mateixa dimensió.");
        }
        for (int i = 0; i < getDimensio(); ++i) {
            set(i, get(i) - a.get(i));
        }
    }

    public void mult(double a) {
        for (int i = 0; i < getDimensio(); ++i) {
            set(i, get(i) * a);
        }
    }
    public double norma() {
        double llargada = 0.;
        for (int i = 0; i < getDimensio(); ++i) {
            llargada += get(i)*get(i);
        }
        return Math.sqrt(llargada);
    }

    public double distancia(Punt a) throws IllegalArgumentException {
        if (a.getDimensio() != getDimensio()) {
            throw new IllegalArgumentException("No es pot calcular la distància entre punts de diferent dimensió.");
        }
        Punt resta = (Punt) this.clone();
        resta.resta(a);
        return resta.norma();
    }
}
