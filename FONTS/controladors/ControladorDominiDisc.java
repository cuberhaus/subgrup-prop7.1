package controladors;

import gestorDeDisc.classes.EscriptorDeCSV;
import gestorDeDisc.classes.LectorDeCSV;

import java.io.IOException;
import java.util.ArrayList;

public class ControladorDominiDisc {
    public ControladorDominiDisc() {}

    public ArrayList<ArrayList<String>> llegirCSV(String ubicacio) throws IOException {
        LectorDeCSV lector = new LectorDeCSV();
        return lector.llegirCSV(ubicacio);
    }

    public void escriptorCSV(String ubicacio, ArrayList<ArrayList<String>> taula) throws IOException {
        EscriptorDeCSV escriptor = new EscriptorDeCSV();
        escriptor.escriureCSV(ubicacio, taula);
    }
}
