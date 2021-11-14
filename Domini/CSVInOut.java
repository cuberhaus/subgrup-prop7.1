package Domini;

import java.io.*;
import java.util.ArrayList;

/** Classe que s'encarrega de la gestió entrada i sortida de CSV. */
public class CSVInOut {
    public CSVInOut() {}
    public CSVTable readCSV(String pathname) throws IOException {
        CSVTable tabla = new CSVTable();
        BufferedReader csvReader = new BufferedReader(new FileReader(pathname));

        String row;

        ArrayList<String> atributs = new ArrayList<>();
        if ((row = csvReader.readLine()) != null) {
            String[] atributos = row.split(",");
            for (String elem : atributos) {
                atributs.add(elem);
            }
            tabla.introduirListaAtributs(atributs);
        }

        ArrayList<String> valores = new ArrayList<>();
        while ((row = csvReader.readLine()) != null) {
            row = row + ' ';
            char[] caracteres = row.toCharArray();
            boolean comillas = true;
            boolean entreComillas = false;

            for (int i = 1; i < caracteres.length; ++i) {
                if (entreComillas && comillas && caracteres[i - 1] == '\"' && caracteres[i] == ',') {
                    caracteres[i - 1] = ' ';
                    entreComillas = false;
                }

                else if (!entreComillas && caracteres[i - 1] == ',' && caracteres[i] == '\"') {
                    comillas = true;
                    caracteres[i] = ' ';
                    entreComillas = true;
                }

                if (entreComillas && caracteres[i - 1] == '\"') {
                    comillas = !comillas;
                    caracteres[i - 1] = '\'';
                }

                if (entreComillas && caracteres[i] == ',') {
                    caracteres[i] = ';';
                }
            }

            String actual = String.valueOf(caracteres);
            String[] temp = actual.split(",");

            for (String elem : temp) {
                valores.add(elem);
            }

            tabla.introduirLlistaDeValors(valores);
            valores.clear();
        }

        atributs.clear();
        csvReader.close();
        return tabla;
    }

    public void writeCSV(String pathname, CSVTable tabla) throws IOException {
        if (!tabla.isInitialized()) {
            System.out.println("La taula que intentes escriure, no està inicialitzada.");
        }

        else {
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathname));
            ArrayList<ArrayList<String>> tablita = tabla.getTable();
            for (ArrayList<String> elem1 : tablita) {
                boolean primero = true;
                for (String elem2 : elem1) {
                    if (!primero) bw.write(',');
                    bw.write(elem2);
                    primero = false;
                }
                bw.write('\n');
            }
            bw.close();
        }
    }
}
