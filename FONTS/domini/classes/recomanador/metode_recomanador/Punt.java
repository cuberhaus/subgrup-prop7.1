package domini.classes.recomanador.metode_recomanador;

import java.util.ArrayList;

/**
 * Representa un punt de R^n i suporta les operacions habituals
 * @author edgar.moreno
 */
public class Punt extends ArrayList<Double> {

    /**
     * @return la dimensio del punt
     */
    public int obtenirDimensio() {
        return size();
    }

    /**
     * @param a punt a afegir
     * @throws IllegalArgumentException si les dimensions dels dos punts no concorden.
     */
    public void afegir(Punt a) throws IllegalArgumentException {
        if (a.obtenirDimensio() != obtenirDimensio()) {
            throw new IllegalArgumentException("Els punts no es poden afegir ja que no tenen la mateixa dimensió.");
        }
        for (int i = 0; i < obtenirDimensio(); ++i) {
            set(i, get(i) + a.get(i));
        }
    }

    /**
     * @param a punt a restar
     * @throws IllegalArgumentException si les dimensions dels dos punts no concorden.
     */
    public void resta(Punt a) throws IllegalArgumentException {
        if (a.obtenirDimensio() != obtenirDimensio()) {
            throw new IllegalArgumentException("Els punts no es poden restar ja que no tenen la mateixa dimensió.");
        }
        for (int i = 0; i < obtenirDimensio(); ++i) {
            set(i, get(i) - a.get(i));
        }
    }

    /**
     * @param a factor pel qual es multiplica el punt
     */
    public void mult(double a) {
        for (int i = 0; i < obtenirDimensio(); ++i) {
            set(i, get(i) * a);
        }
    }

    /**
     * @return la norma euclidiana del punt.
     */
    public double norma() {
        double llargada = 0.;
        for (int i = 0; i < obtenirDimensio(); ++i) {
            llargada += get(i)*get(i);
        }
        return Math.sqrt(llargada);
    }

    /**
     * @param a punt amb el qual es calcula la distància.
     * @return la distància euclidiana entre els punts
     * @throws IllegalArgumentException si els punts no tenen la mateixa dimensió.
     */
    public double distancia(Punt a) throws IllegalArgumentException {
        if (a.obtenirDimensio() != obtenirDimensio()) {
            throw new IllegalArgumentException("No es pot calcular la distància entre punts de diferent dimensió.");
        }
        Punt resta = (Punt) this.clone();
        resta.resta(a);
        return resta.norma();
    }
}
