import domini.classes.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        LectorDeFitxers lector = new LectorDeCSV();
        Taula tablita = (Taula) lector.lectorDeFitxers("C:/Users/pable/Desktop/items.csv");
        tablita.imprimir();
        ArrayList<String> atributs = tablita.obtenirLlistaAtributs();
        System.out.println(atributs);

        EscriptorDeFitxers escriptor = new EscriptorDeCSV();
        escriptor.escriptorFitxers("C:/Users/pable/Desktop/items1.csv", tablita);
    }
}
