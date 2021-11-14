package Domini;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
            row = row;
            String[] atributos = row.split(",");
            for (String elem : atributos) {
                atributs.add(elem);
            }

            tabla.introduirListaAtributs(atributs);
        }

        int indice = 2;
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

            if (valores.size() != 25) {
                System.out.println(indice);
                System.out.println((valores.size()));
                System.out.println(row);
                System.out.println(actual);
            }

            tabla.introduirLlistaDeValors(valores);
            valores.clear();
            ++indice;
        }

        atributs.clear();
        csvReader.close();
        return tabla;
    }

    public void writeCSV(String pathname) {

    }
}
