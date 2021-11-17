package domini.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe que implementa el lector de CSV
 * @author pablo.vega
 */

public class LectorDeCSV extends LectorDeFitxers{
    public LectorDeCSV() {}

    @Override
    public Contenidor lectorDeFitxers(String ubicacio) throws IOException, InterruptedException {
        Contenidor temp = this.llegirCSV(ubicacio);
        return temp;
    }

    /**
     * Funció que donat un pathname, et retorna la taula amb el contingut del CSV
     * @param ubicacio <code>String</code> pathname amb la ubicació de l'arxiu.
     * @return <code>Taula</code> amb el contingut de lal CSV
     * @throws IOException
     */
    public TaulaCSV llegirCSV(String ubicacio) throws IOException, InterruptedException {
        TaulaCSV tabla = new TaulaCSV();
        BufferedReader csvReader = new BufferedReader(new FileReader(ubicacio));

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

            ArrayList<Integer> posicionComillas = new ArrayList<>();
            ArrayList<Integer> posicionComas = new ArrayList<>();

            for (int i = 1; i < caracteres.length; ++i) {
                if (entreComillas && comillas && caracteres[i - 1] == '\"' && caracteres[i] == ',') {
                    caracteres[i - 1] = ' ';
                    posicionComillas.add(i - 1);
                    entreComillas = false;
                }

                else if (!entreComillas && caracteres[i - 1] == ',' && caracteres[i] == '\"') {
                    comillas = true;
                    caracteres[i] = ' ';
                    posicionComillas.add(i);
                    entreComillas = true;
                }

                if (entreComillas && caracteres[i - 1] == '\"') {
                    comillas = !comillas;
                    posicionComillas.add(i - 1);
                    caracteres[i - 1] = '\'';
                }

                if (entreComillas && caracteres[i] == ',') {
                    posicionComas.add(i);
                    caracteres[i] = ';';
                }
            }

            String actual = String.valueOf(caracteres);
            String[] temp = actual.split(",");

            for (String elem : temp) {
                valores.add(elem);
            }

            ArrayList<String> temporal = new ArrayList<>();
            Integer indice = 0;
            for (String valor : valores) {
                char[] letras = valor.toCharArray();
                for (int i = 0; i < letras.length; ++i) {
                    if (posicionComas.contains(indice)) letras[i] = ',';
                    if (posicionComillas.contains(indice)) letras[i] = '\"';
                    ++indice;
                }
                temporal.add(String.valueOf(letras));
                ++indice;
            }

            tabla.introduirLlistaDeValors(temporal);
            valores.clear();
        }

        atributs.clear();
        csvReader.close();
        return tabla;
    }
}
