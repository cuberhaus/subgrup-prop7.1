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
    public Contenidor lectorDeFitxers(String pathname) throws IOException {
        Contenidor temp = this.llegirCSV(pathname);
        return temp;
    }

    /**
     * Funció que donat un pathname, et retorna la taula amb el contingut del CSV
     * @param pathname <code>String</code> pathname amb la ubicació de l'arxiu.
     * @return <code>Taula</code> amb el contingut de lal CSV
     * @throws IOException
     */
    public TaulaCSV llegirCSV(String pathname) throws IOException {
        TaulaCSV tabla = new TaulaCSV();
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
}
